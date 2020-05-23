package com.github.windchopper.common.fx.dialog;

import com.github.windchopper.common.util.Pipeliner;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;

public class ExceptionDialogSkeleton extends CaptionedDialogSkeleton {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.fx.i18n.messages");

    private static final String BUNDLE_KEY__STACK_TRACE = "com.github.windchopper.common.fx.dialog.ExceptionDialog.stackTrace";

    private final ObjectProperty<Throwable> exceptionProperty = new SimpleObjectProperty<>(this, "exception");

    public ExceptionDialogSkeleton() {
        add(PartType.CONTENT, Pipeliner.of(TitledPane::new)
            .set(bean -> bean::setExpanded, false)
            .set(bean -> bean::setAnimated, false)
            .set(bean -> bean::setPadding, new Insets(10.0))
            .set(bean -> bean::setText, bundle.getString(BUNDLE_KEY__STACK_TRACE))
            .set(bean -> bean::setContent, Pipeliner.of(TextArea::new)
                .set(bean -> bean::setPrefColumnCount, 40)
                .set(bean -> bean::setPrefRowCount, 20)
                .accept(bean -> {
                    exceptionProperty.addListener((exceptionProperty, oldValue, newValue) -> {
                        try (StringWriter stringWriter = new StringWriter(); PrintWriter printWriter = new PrintWriter(stringWriter, true)) {
                            newValue.printStackTrace(printWriter);
                            bean.setText(stringWriter.toString());
                        } catch (IOException ignored) {
                        }
                    });
                })
                .get())
            .accept(bean -> bean.expandedProperty().addListener((expandedProperty, oldValue, newValue) ->
                Platform.runLater(() -> bean.getScene().getWindow().sizeToScene())))
            .get());
    }

    public ObjectProperty<Throwable> exceptionProperty() {
        return exceptionProperty;
    }

}
