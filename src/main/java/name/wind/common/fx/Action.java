package name.wind.common.fx;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Tooltip;

import java.util.concurrent.Executor;

import static java.util.concurrent.ForkJoinPool.commonPool;

public class Action implements EventHandler<ActionEvent> {

    private final StringProperty textProperty = new SimpleStringProperty(this, "text");
    private final StringProperty longTextProperty = new SimpleStringProperty(this, "longText");
    private final ObjectProperty<Node> graphicProperty = new SimpleObjectProperty<>(this, "graphic");
    private final BooleanProperty disableProperty = new SimpleBooleanProperty(this, "disable");

    private final boolean lockWhileExecute;
    private final Executor executor;
    private final EventHandler<ActionEvent> handler;

    /*
     *
     */

    public Action(EventHandler<ActionEvent> handler) {
        this(commonPool(), true, handler);
    }

    public Action(Executor executor, boolean lockWhileExecute, EventHandler<ActionEvent> handler) {
        this.executor = executor;
        this.lockWhileExecute = lockWhileExecute;
        this.handler = handler;
    }

    /*
     *
     */

    public void bind(ButtonBase button) {
        button.textProperty().bind(textProperty);
        button.graphicProperty().bind(graphicProperty);
        button.disableProperty().bind(disableProperty);

        button.addEventHandler(ActionEvent.ACTION, this);

        longTextProperty.isNotEmpty().addListener((property, oldNotEmptyState, newNotEmptyState) -> {
            Tooltip tooltip = button.getTooltip();

            if (newNotEmptyState) {
                tooltip = new Tooltip();
                tooltip.textProperty().bind(longTextProperty);
            } else {
                tooltip.textProperty().unbind();
                tooltip = null;
            }

            button.setTooltip(tooltip);
        });
    }

    /*
     *
     */

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

    /*
     * EventHandler implementation
     */

    @Override public void handle(ActionEvent event) {
        if (lockWhileExecute) {
            disableProperty().set(true);
        }

        executor.execute(() -> {
            try {
                handler.handle(event);
            } finally {
                if (lockWhileExecute) {
                    disableProperty().set(false);
                }
            }
        });
    }

}
