package com.github.windchopper.common.fx.dialog;

import com.github.windchopper.common.fx.Alignment;
import com.github.windchopper.common.fx.Fill;
import com.github.windchopper.common.util.Pipeliner;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class CaptionedDialogSkeleton extends DialogSkeleton {

    private final StringProperty textProperty = new SimpleStringProperty(this, "text");
    private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>(this, "image");

    public CaptionedDialogSkeleton() {
        add(PartType.HEADING, Pipeliner.of(GridPane::new)
            .set(grid -> grid::setPadding, new Insets(10.0))
            .set(grid -> grid::setBackground, new Background(
                new BackgroundFill(
                    new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.DARKGRAY), new Stop(1, Color.TRANSPARENT)),
                    CornerRadii.EMPTY, Insets.EMPTY)))
            .add(grid -> grid::getChildren, List.of(
                Pipeliner.of(Label::new)
                    .accept(label -> label.textProperty().bind(textProperty))
                    .set(label -> label::setWrapText, true)
                    .accept(label -> label.setFont(
                        Font.font(
                            label.getFont().getFamily(), FontWeight.BOLD, label.getFont().getSize() * 1.1)))
                    .accept(label -> {
                        GridPane.setConstraints(label, 0, 0);
                        GridPane.setMargin(label, new Insets(0.0, 10.0, 0.0, 0.0));
                        Alignment.LEFT_TOP.apply(label);
                        Fill.HORIZONTAL.apply(label);
                    })
                    .get(),
                Pipeliner.of(ImageView::new)
                    .accept(imageView -> imageView.imageProperty().bind(imageProperty))
                    .accept(imageView -> {
                        GridPane.setConstraints(imageView, 1, 0);
                        Alignment.RIGHT_TOP.apply(imageView);
                        Fill.NONE.apply(imageView);
                    })
                    .get()))
            .get());
    }

    public StringProperty textProperty() {
        return textProperty;
    }

    public ObjectProperty<Image> imageProperty() {
        return imageProperty;
    }

}
