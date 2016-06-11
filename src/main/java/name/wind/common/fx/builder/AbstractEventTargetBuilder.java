package name.wind.common.fx.builder;

import javafx.event.EventTarget;

import java.util.function.Supplier;

public abstract class AbstractEventTargetBuilder<T extends EventTarget, B extends AbstractEventTargetBuilder<T, B>> extends AbstractBuilder<T, B> {

    public AbstractEventTargetBuilder(Supplier<T> supplier) {
        super(supplier);
    }

}
