package name.wind.common.fx;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Tooltip;

public abstract class Action implements EventHandler<ActionEvent> {

    private final StringProperty textProperty = new SimpleStringProperty(this, "text");
    private final StringProperty longTextProperty = new SimpleStringProperty(this, "longText");
    private final ObjectProperty<Node> graphicProperty = new SimpleObjectProperty<>(this, "graphic");
    private final BooleanProperty disableProperty = new SimpleBooleanProperty(this, "disable");

    public StringProperty textProperty() {
        return textProperty;
    }

    public StringProperty longTextProperty() {
        return longTextProperty;
    }

    public ObjectProperty<Node> graphicProperty() {
        return graphicProperty;
    }

    public BooleanProperty disableProperty() {
        return disableProperty;
    }

    public void bind(ButtonBase button) {
        button.textProperty().bind(textProperty);
        button.graphicProperty().bind(graphicProperty);
        button.disableProperty().bind(disableProperty);

        button.addEventHandler(ActionEvent.ACTION, this);

        longTextProperty.isNotEmpty().addListener(
            (property, oldNotEmptyState, newNotEmptyState) -> {
                Tooltip tooltip = button.getTooltip();

                if (newNotEmptyState) {
                    tooltip = new Tooltip();
                    tooltip.textProperty().bind(longTextProperty);
                } else {
                    tooltip.textProperty().unbind();
                    tooltip = null;
                }

                button.setTooltip(tooltip);
            }
        );
    }

}
