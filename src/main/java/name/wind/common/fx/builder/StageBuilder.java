package name.wind.common.fx.builder;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.function.Supplier;

public class StageBuilder extends AbstractWindowBuilder<Stage, StageBuilder> {

    public StageBuilder(Supplier<Stage> targetSupplier) {
        super(targetSupplier);
    }

    public StageBuilder title(String title) {
        target.setTitle(title);
        return this;
    }

    public StageBuilder fixMinSize() {
        double width = target.getWidth();
        target.setMinWidth(width);

        double height = target.getHeight();
        target.setMinHeight(height);

        return this;
    }

    public StageBuilder scene(Parent sceneRoot) {
        Scene scene = new Scene(sceneRoot);
        target.setScene(scene);

        return this;
    }

    public StageBuilder modality(Modality modality) {
        target.initModality(modality);
        return this;
    }

    public StageBuilder owner(Window owner) {
        target.initOwner(owner);
        return this;
    }

}
