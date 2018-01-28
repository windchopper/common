package com.github.windchopper.common.preferences;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JndiPreferencesStorage extends AbstractPreferencesStorage {

    @FunctionalInterface public interface ContextMethod<R> {
        R invoke() throws NamingException;
    }

    private static final String DEFAULT_PREFIX__VALUE = "preferences:value:";
    private static final String DEFAULT_PREFIX__CHILD = "preferences:child:";

    private final ContextMethod<Context> contextBuilder;
    private final String valuePrefix;
    private final String childPrefix;
    private final String name;
    private final String path;

    private final Map<String, String> values = new HashMap<>();
    private final Map<String, PreferencesStorage> childs = new HashMap<>();

    public JndiPreferencesStorage(ContextMethod<Context> contextBuilder, String valuePrefix, String childPrefix) {
        this.contextBuilder = contextBuilder;
        this.valuePrefix = Objects.toString(valuePrefix, DEFAULT_PREFIX__VALUE);
        this.childPrefix = Objects.toString(childPrefix, DEFAULT_PREFIX__CHILD);
        this.name = "";
        this.path = name;
        load();
    }

    public JndiPreferencesStorage(JndiPreferencesStorage parent, String name) {
        this.contextBuilder = parent.contextBuilder;
        this.valuePrefix = parent.valuePrefix;
        this.childPrefix = parent.childPrefix;
        this.name = name;
        this.path = parent.path + PATH_NAME_SEPARATOR + name;
        load();
    }

    private Context traverse(Context context) throws NamingException {
        for (StringTokenizer tokenizer = new StringTokenizer(path, PATH_NAME_SEPARATOR); tokenizer.hasMoreTokens(); ) {
            context = context.createSubcontext(tokenizer.nextToken());
        }

        return context;
    }

    @FunctionalInterface private interface ContextAction<R> {
        R execute(Context context) throws NamingException;
    }

    private <R> R performWithContext(ContextAction<R> action) throws NamingException {
        Context context = contextBuilder.invoke();

        try {
            return action.execute(context);
        } finally {
            context.close();
        }
    }

    private void load() {
        invoke(
            () -> performWithContext(context -> {
                continueLoad(context);
                return null;
            }),
            this::logError,
            () -> null);
    }

    private <B extends NameClassPair> Stream<B> bindingsAsStream(ContextMethod<NamingEnumeration<B>> listMethod) throws NamingException {
        NamingEnumeration<B> bindingEnumeration = listMethod.invoke();
        List<B> bindingList;

        try {
            bindingList = Collections.list(bindingEnumeration);
        } finally {
            bindingEnumeration.close();
        }

        return bindingList.stream();
    }

    private void continueLoad(Context context) throws NamingException {
        Map<String, Set<NameClassPair>> separatedBindings = bindingsAsStream(() -> context.list(""))
            .collect(Collectors.groupingBy(binding -> binding.getName().startsWith(valuePrefix) ? "values" : binding.getName().startsWith(childPrefix) ? "childs" : "others", Collectors.toSet()));

        Optional.ofNullable(separatedBindings.get("values")).ifPresent(bindings -> values.putAll(
            bindings.stream().collect(
                Collectors.toMap(
                    binding -> binding.getName().replace(valuePrefix, ""),
                    binding -> invoke(() -> (String) context.lookup(binding.getName()), this::logError, () -> null)))));

        Optional.ofNullable(separatedBindings.get("childs")).ifPresent(bindings -> childs.putAll(
            bindings.stream().collect(
                Collectors.toMap(
                    binding -> binding.getName().replace(childPrefix, ""),
                    binding -> new JndiPreferencesStorage(this, binding.getName().replace(childPrefix, ""))))));
    }

    @Override public String value(String name, String defaultValue) {
        return values.getOrDefault(name, defaultValue);
    }

    @Override public void putValue(String name, String value) {
        invoke(
            () -> performWithContext(context -> {
                traverse(context).rebind(name, value);
                values.put(name, value);
                return null;
            }),
            this::logError,
            () -> null);
    }

    @Override public void removeValue(String name) {
        invoke(
            () -> performWithContext(context -> {
                traverse(context).unbind(name);
                values.remove(name);
                return null;
            }),
            this::logError,
            () -> null);
    }

    @Override public Set<String> valueNames() {
        return Collections.unmodifiableSet(values.keySet());
    }

    @Override public Set<String> childNames() {
        return Collections.unmodifiableSet(childs.keySet());
    }

    @Override public PreferencesStorage child(String name) {
        return childs.computeIfAbsent(name, key -> new JndiPreferencesStorage(this, key));
    }

    @Override public void removeChild(String name) {
        invoke(
            () -> performWithContext(context -> {
                traverse(context).destroySubcontext(name);
                childs.remove(name);
                return null;
            }),
            this::logError,
            () -> null);
    }

}
