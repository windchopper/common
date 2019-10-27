package com.github.windchopper.common.fx.cdi.form;

import javafx.collections.ObservableMap;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;
import java.util.function.Supplier;

public class StageFormLoad extends FormLoad {

    private final Supplier<Stage> stageSupplier;

    public StageFormLoad(Resource resource, Supplier<Stage> stageSupplier) {
        super(resource);
        this.stageSupplier = stageSupplier;
    }

    public StageFormLoad(Resource resource, Map<String, ?> parameters, Supplier<Stage> stageSupplier) {
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
