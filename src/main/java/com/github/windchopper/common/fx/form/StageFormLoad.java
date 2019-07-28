package com.github.windchopper.common.fx.form;

import javafx.collections.ObservableMap;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;
import java.util.function.Supplier;

public class StageFormLoad extends FormLoad {

    private final Supplier<Stage> stageSupplier;

    public StageFormLoad(Supplier<Stage> stageSupplier, String resource) {
        super(resource);
        this.stageSupplier = stageSupplier;
    }

    public StageFormLoad(Supplier<Stage> stageSupplier, String resource, Map<String, ?> parameters) {
        super(resource, parameters);
        this.stageSupplier = stageSupplier;
    }

    public Stage stage() {
        return stageSupplier.get();
    }

    @Override public void afterLoad(Parent form, Object controller, Map<String, ?> parameters, ObservableMap<String, ?> formNamespace) {
        Scene scene = new Scene(form);

        Stage stage = stageSupplier.get();
        stage.setScene(scene);

        super.afterLoad(form, controller, parameters, formNamespace);

        stage.show();
    }

}
