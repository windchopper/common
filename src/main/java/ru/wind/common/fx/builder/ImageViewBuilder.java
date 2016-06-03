package ru.wind.common.fx.builder;

import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.function.Supplier;

public class ImageViewBuilder extends AbstractNodeBuilder<ImageView, ImageViewBuilder> {

    public ImageViewBuilder(Supplier<ImageView> targetSupplier) {
        super(targetSupplier);
    }

    public ImageViewBuilder image(Image image) {
        target.setImage(image);
        return this;
    }

    public ImageViewBuilder image(Supplier<Image> imageReference) {
        target.setImage(imageReference.get());
        return this;
    }

    public ImageViewBuilder imageUrl(String url) {
        target.setImage(
            new Image(url)
        );

        return this;
    }

    public ImageViewBuilder bindImageProperty(ObservableValue<? extends Image> observable) {
        target.imageProperty().bind(observable);
        return this;
    }

}
