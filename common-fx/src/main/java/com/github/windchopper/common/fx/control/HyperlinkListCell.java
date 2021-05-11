package com.github.windchopper.common.fx.control;

import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import java.util.Optional;
import java.util.function.BiConsumer;

public class HyperlinkListCell<T> extends ListCell<T> {

    private Hyperlink hyperlink = new Hyperlink();

    private StringConverter<T> converter;
    private BiConsumer<ActionEvent, T> actionHandler;

    public HyperlinkListCell(StringConverter<T> converter) {
        this(converter, null);
    }

    public HyperlinkListCell(StringConverter<T> converter, BiConsumer<ActionEvent, T> actionHandler) {
        this.converter = converter;
        this.actionHandler = actionHandler;

        getStyleClass().add("hyperlink-list-cell");
        setGraphic(null);
    }

    public Hyperlink getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(Hyperlink hyperlink) {
        this.hyperlink = hyperlink;
    }

    public StringConverter<T> getConverter() {
        return converter;
    }

    public void setConverter(StringConverter<T> converter) {
        this.converter = converter;
    }

    public BiConsumer<ActionEvent, T> getActionHandler() {
        return actionHandler;
    }

    public void setActionHandler(BiConsumer<ActionEvent, T> actionHandler) {
        this.actionHandler = actionHandler;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(hyperlink);
            hyperlink.setText(Optional.of(converter)
                .map(converter -> converter.toString(item))
                .orElse(""));

            hyperlink.setVisited(false);

            if (actionHandler != null) {
                hyperlink.setOnAction(event -> actionHandler.accept(event, item));
            }
        }
    }

}
