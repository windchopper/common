package com.github.windchopper.common.preferences;

import com.github.windchopper.common.util.stream.FailableConsumer;
import com.github.windchopper.common.util.stream.FailableFunction;
import com.github.windchopper.common.util.stream.FailableRunnable;
import com.github.windchopper.common.util.stream.FailableSupplier;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class JndiPreferencesStorage extends AbstractPreferencesStorage {

    private static final String DEFAULT_PREFIX__VALUE = "preferences:value:";
    private static final String DEFAULT_PREFIX__CHILD = "preferences:child:";

    private final FailableSupplier<Context, NamingException> contextBuilder;
    private final String valuePrefix;
    private final String childPrefix;
    private final String name;
    private final String path;

    private final Map<String, String> values = new HashMap<>();
    private final Map<String, PreferencesStorage> childs = new HashMap<>();

    public JndiPreferencesStorage(FailableSupplier<Context, NamingException> contextBuilder, String valuePrefix, String childPrefix) {
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

    private void performWithContext(FailableConsumer<Context, NamingException> action) throws NamingException {
        Context context = contextBuilder.get();

        try {
            action.accept(context);
        } finally {
            context.close();
        }
    }

    private void load() {
        FailableRunnable
            .failsafeRun(() -> performWithContext(this::continueLoad))
            .onFailure(this::logError);
    }

    private <B extends NameClassPair> Stream<B> bindingsAsStream(FailableSupplier<NamingEnumeration<B>, NamingException> listMethod) throws NamingException {
        NamingEnumeration<B> bindingEnumeration = listMethod.get();
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
            bindings.stream()
                .collect(toMap(
                    binding -> binding.getName().replace(valuePrefix, ""),
                    FailableFunction.wrap((NameClassPair binding) -> context.lookup(binding.getName()))
                        .andThen(result -> result.recover((binding, exception) -> null))
                        .andThen(result -> Objects.toString(result, null))))));

        Optional.ofNullable(separatedBindings.get("childs")).ifPresent(bindings -> childs.putAll(
            bindings.stream().collect(
                toMap(
                    binding -> binding.getName().replace(childPrefix, ""),
                    binding -> new JndiPreferencesStorage(this, binding.getName().replace(childPrefix, ""))))));
    }

    @Override public String value(String name, String defaultValue) {
        return values.getOrDefault(name, defaultValue);
    }

    @Override public void putValue(String name, String value) {
        FailableRunnable
            .failsafeRun(() -> performWithContext(context -> {
                traverse(context).rebind(name, value);
                values.put(name, value);
            }))
            .onFailure(this::logError);
    }

    @Override public void removeValue(String name) {
        FailableRunnable
            .failsafeRun(() -> performWithContext(context -> {
                traverse(context).unbind(name);
                values.remove(name);
            }))
            .onFailure(this::logError);
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
        FailableRunnable
            .failsafeRun(() -> performWithContext(context -> {
                traverse(context).destroySubcontext(name);
                childs.remove(name);
            }))
            .onFailure(this::logError);
    }

}
