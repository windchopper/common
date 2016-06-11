package name.wind.common.fx.builder;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.function.Supplier;

public class SceneBuilder extends AbstractEventTargetBuilder<Scene, SceneBuilder> {

    public SceneBuilder(Supplier<Scene> targetSupplier) {
        super(targetSupplier);
    }

    public SceneBuilder root(Parent root) {
        target.setRoot(root);
        return this;
    }

    public SceneBuilder keyTypedHandler(EventHandler<KeyEvent> handler) {
        target.setOnKeyTyped(handler);
        return this;
    }

}
