package name.wind.common.fx.builder;

import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.function.Supplier;

public abstract class AbstractStageBuilder<T extends Stage, B extends AbstractStageBuilder<T, B>> extends AbstractWindowBuilder<T, B> {

    public AbstractStageBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

    @SuppressWarnings("unchecked") public B title(String title) {
        target.setTitle(title);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B bindTitleProperty(ObservableValue<String> observable) {
        target.titleProperty().bind(observable);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B icons(Image... images) {
        target.getIcons().addAll(images);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B owner(Window owner) {
        target.initOwner(owner);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B style(StageStyle style) {
        target.initStyle(style);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B modality(Modality modality) {
        target.initModality(modality);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B resizable(boolean resizable) {
        target.setResizable(resizable);
        return (B) this;
    }

}
