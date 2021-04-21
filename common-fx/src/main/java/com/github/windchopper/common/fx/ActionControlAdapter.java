package com.github.windchopper.common.fx;

import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.Property;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.util.function.Function;

public class ActionControlAdapter<T extends EventTarget> {

    @FunctionalInterface public interface AddEventHandlerInvoker<T extends EventTarget> {
        <E extends Event> void addEventHandler(T control, EventType<E> eventType, EventHandler<E> eventHandler);
    }

    public static class ActionButtonAdapter extends ActionControlAdapter<ButtonBase> {

        public ActionButtonAdapter(ButtonBase buttonBase) {
            super(buttonBase,
                ButtonBase::addEventHandler,
                ButtonBase::textProperty,
                ButtonBase::graphicProperty,
                ButtonBase::disableProperty,
                ButtonBase::tooltipProperty);
        }

    }

    public static class ActionMenuItemAdapter extends ActionControlAdapter<MenuItem> {

        public ActionMenuItemAdapter(MenuItem menuItem) {
            super(menuItem,
                MenuItem::addEventHandler,
                MenuItem::textProperty,
                MenuItem::graphicProperty,
                MenuItem::disableProperty,
                control -> new ObjectPropertyBase<>() {

                    private Tooltip installedTooltip;

                    @Override protected void invalidated() {
                        var tooltip = get();

                        if (tooltip != installedTooltip) {
                            if (installedTooltip != null) {
                                Tooltip.uninstall(control.getGraphic(), installedTooltip);
                            }

                            if (tooltip != null) {
                                Tooltip.install(control.getGraphic(), tooltip);
                            }

                            installedTooltip = tooltip;
                        }
                    }

                    @Override public Object getBean() {
                        return control;
                    }

                    @Override public String getName() {
                        return "tooltip";
                    }

                });
        }

    }

    private final T control;
    private final AddEventHandlerInvoker<T> addEventHandlerInvoker;
    private final Function<T, Property<String>> textPropertyAccessor;
    private final Function<T, Property<Node>> graphicPropertyAccessor;
    private final Function<T, Property<Boolean>> disablePropertyAccessor;
    private final Function<T, Property<Tooltip>> tooltipPropertyAccessor;

    public ActionControlAdapter(T control,
                                AddEventHandlerInvoker<T> addEventHandlerInvoker,
                                Function<T, Property<String>> textPropertyAccessor,
                                Function<T, Property<Node>> graphicPropertyAccessor,
                                Function<T, Property<Boolean>> disablePropertyAccessor,
                                Function<T, Property<Tooltip>> tooltipPropertyAccessor) {
        this.control = control;
        this.addEventHandlerInvoker = addEventHandlerInvoker;
        this.textPropertyAccessor = textPropertyAccessor;
        this.graphicPropertyAccessor = graphicPropertyAccessor;
        this.disablePropertyAccessor = disablePropertyAccessor;
        this.tooltipPropertyAccessor = tooltipPropertyAccessor;
    }

    public <E extends Event> void addEventHandler(EventType<E> eventType, EventHandler<E> eventHandler) {
        addEventHandlerInvoker.addEventHandler(control, eventType, eventHandler);
    }

    public Property<String> textProperty() {
        return textPropertyAccessor.apply(control);
    }

    public Property<Node> graphicProperty() {
        return graphicPropertyAccessor.apply(control);
    }

    public Property<Boolean> disableProperty() {
        return disablePropertyAccessor.apply(control);
    }

    public Property<Tooltip> tooltipProperty() {
        return tooltipPropertyAccessor.apply(control);
    }

}
