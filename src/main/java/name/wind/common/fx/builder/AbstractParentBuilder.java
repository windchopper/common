package name.wind.common.fx.builder;

import javafx.scene.Parent;

import java.util.function.Supplier;

public abstract class AbstractParentBuilder<T extends Parent, B extends AbstractParentBuilder<T, B>> extends AbstractNodeBuilder<T, B> {

    public AbstractParentBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

}
