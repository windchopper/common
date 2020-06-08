package com.github.windchopper.common.fx;

import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.Property;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.control.*;

public class ActionControlAdapter<T extends EventTarget> {

    @FunctionalInterface public interface AddEventHandlerInvoker<T extends EventTarget> {
        <E extends Event> void addEventHandler(T control, EventType<E> eventType, EventHandler<E> eventHandler);
    }

    @FunctionalInterface public interface PropertyAccessor<T extends EventTarget, P> {
        Property<P> property(T control);
    }

    public static class ForButtonBase extends ActionControlAdapter<ButtonBase> {

        public ForButtonBase(ButtonBase buttonBase) {
            super(buttonBase,
                ButtonBase::addEventHandler,
                ButtonBase::textProperty,
                ButtonBase::graphicProperty,
                ButtonBase::disableProperty,
                ButtonBase::tooltipProperty);
        }

    }

    public static class ForMenuItem extends ActionControlAdapter<MenuItem> {

        public ForMenuItem(MenuItem menuItem) {
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
    private final PropertyAccessor<T, String> textPropertyAccessor;
    private final PropertyAccessor<T, Node> graphicPropertyAccessor;
    private final PropertyAccessor<T, Boolean> disablePropertyAccessor;
    private final PropertyAccessor<T, Tooltip> tooltipPropertyAccessor;

    public ActionControlAdapter(T control,
                                AddEventHandlerInvoker<T> addEventHandlerInvoker,
                                PropertyAccessor<T, String> textPropertyAccessor,
                                PropertyAccessor<T, Node> graphicPropertyAccessor,
                                PropertyAccessor<T, Boolean> disablePropertyAccessor,
                                PropertyAccessor<T, Tooltip> tooltipPropertyAccessor) {
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
        return textPropertyAccessor.property(control);
    }

    public Property<Node> graphicProperty() {
        return graphicPropertyAccessor.property(control);
    }

    public Property<Boolean> disableProperty() {
        return disablePropertyAccessor.property(control);
    }

    public Property<Tooltip> toolipProperty() {
        return tooltipPropertyAccessor.property(control);
    }

}
