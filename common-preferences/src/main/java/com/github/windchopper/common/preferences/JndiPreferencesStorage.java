package com.github.windchopper.common.preferences;

import com.github.windchopper.common.util.stream.FailableConsumer;
import com.github.windchopper.common.util.stream.FailableSupplier;

import javax.naming.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;

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

    public JndiPreferencesStorage(FailableSupplier<Context, NamingException> contextBuilder, String valuePrefix, String childPrefix) throws NamingException {
        this.contextBuilder = contextBuilder;
        this.valuePrefix = Objects.toString(valuePrefix, DEFAULT_PREFIX__VALUE);
        this.childPrefix = Objects.toString(childPrefix, DEFAULT_PREFIX__CHILD);
        this.name = "";
        this.path = name;
        performWithContext(this::continueLoad);
    }

    public JndiPreferencesStorage(JndiPreferencesStorage parent, String name) throws NamingException {
        this.contextBuilder = parent.contextBuilder;
        this.valuePrefix = parent.valuePrefix;
        this.childPrefix = parent.childPrefix;
        this.name = name;
        this.path = parent.path + "/" + name;
        performWithContext(this::continueLoad);
    }

    private Context traverse(Context context) throws NamingException {
        for (var tokenizer = new StringTokenizer(path, "/"); tokenizer.hasMoreTokens(); ) {
            context = context.createSubcontext(tokenizer.nextToken());
        }

        return context;
    }

    private void performWithContext(FailableConsumer<Context, NamingException> action) throws NamingException {
        var context = contextBuilder.get();

        try {
            action.accept(context);
        } finally {
            context.close();
        }
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
        var separatedBindings = bindingsAsStream(() -> context.list(""))
            .collect(Collectors.groupingBy(binding -> binding.getName().startsWith(valuePrefix) ? "values" : binding.getName().startsWith(childPrefix) ? "childs" : "others", Collectors.toSet()));

        for (var binding : separatedBindings.getOrDefault("values", emptySet())) {
            values.put(binding.getName().replace(valuePrefix, ""), Objects.toString(context.lookup(binding.getName()), null));
        }

        for (var binding : separatedBindings.getOrDefault("childs", emptySet())) {
            childs.put(binding.getName().replace(childPrefix, ""), new JndiPreferencesStorage(this, binding.getName().replace(childPrefix, "")));
        }
    }

    @Override public Optional<PreferencesEntryText> valueImpl(String name) {
        return Optional.ofNullable(values.get(name))
            .map(encoded -> new PreferencesEntryText().decodeFromString(encoded));
    }

    @Override public void saveValueImpl(String name, String text) throws NamingException {
        performWithContext(context -> {
            var encodedValue = new PreferencesEntryText(LocalDateTime.now(), text).encodeToString();
            traverse(context).rebind(name, encodedValue);
            values.put(name, encodedValue);
        });
    }

    @Override public void removeValueImpl(String name) throws NamingException {
        performWithContext(context -> {
            traverse(context).unbind(name);
            values.remove(name);
        });
    }

    @Override public PreferencesStorage childImpl(String name) throws NamingException {
        var child = childs.get(name);

        if (child == null) {
            childs.put(name, child = new JndiPreferencesStorage(this, name));
        }

        return child;
    }

    @Override public void removeChildImpl(String name) throws NamingException {
        performWithContext(context -> {
            traverse(context).destroySubcontext(name);
            childs.remove(name);
        });
    }

    @Override public Set<String> valueNamesImpl() {
        return Collections.unmodifiableSet(values.keySet());
    }

    @Override public Set<String> childNamesImpl() {
        return Collections.unmodifiableSet(childs.keySet());
    }

}
