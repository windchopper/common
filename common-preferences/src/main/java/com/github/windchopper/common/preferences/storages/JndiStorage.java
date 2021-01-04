package com.github.windchopper.common.preferences.storages;

import com.github.windchopper.common.preferences.PreferencesStorage;
import com.github.windchopper.common.util.stream.FailableConsumer;
import com.github.windchopper.common.util.stream.FailableSupplier;

import javax.naming.*;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

public class JndiStorage implements PreferencesStorage {

    private final FailableSupplier<Context, NamingException> contextBuilder;
    private final String name;
    private final String path;

    private final Map<String, String> values = new HashMap<>();
    private final Map<String, PreferencesStorage> childs = new HashMap<>();

    public JndiStorage(FailableSupplier<Context, NamingException> contextBuilder) throws NamingException {
        this.contextBuilder = contextBuilder;
        name = "";
        path = "";
        performWithContext(this::continueLoad);
    }

    public JndiStorage(JndiStorage parent, String name) throws NamingException {
        this.contextBuilder = parent.contextBuilder;
        this.name = name;
        this.path = String.join(SEPARATOR, parent.path, name);
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

    private void continueLoad(Context context) throws NamingException {
        Map<String, List<Binding>> separatedBindings;

        var bindings = context.listBindings("");

        try {
            separatedBindings = Collections.list(bindings).stream()
                .collect(groupingBy(binding -> binding.getObject() instanceof Context ? "childs" : "values"));
        } finally {
            bindings.close();
        }

        for (var binding : separatedBindings.getOrDefault("childs", emptyList())) {
            childs.put(binding.getName(), new JndiStorage(this, binding.getName()));
        }

        for (var binding : separatedBindings.getOrDefault("values", emptyList())) {
            values.put(binding.getName(), Objects.toString(binding.getObject(), null));
        }
    }

    @Override public String value(String name) {
        return values.get(name);
    }

    @Override public void saveValue(String name, String text) throws NamingException {
        performWithContext(context -> {
            traverse(context).rebind(name, text);
            values.put(name, text);
        });
    }

    @Override public void dropValue(String name) throws NamingException {
        performWithContext(context -> {
            traverse(context).unbind(name);
            values.remove(name);
        });
    }

    @Override public PreferencesStorage child(String name) throws NamingException {
        var child = childs.get(name);

        if (child == null) {
            childs.put(name, child = new JndiStorage(this, name));
        }

        return child;
    }

    @Override public void dropChild(String name) throws NamingException {
        performWithContext(context -> {
            traverse(context).destroySubcontext(name);
            childs.remove(name);
        });
    }

    @Override public Set<String> valueNames() {
        return Collections.unmodifiableSet(values.keySet());
    }

    @Override public Set<String> childNames() {
        return Collections.unmodifiableSet(childs.keySet());
    }

}
