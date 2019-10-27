package com.github.windchopper.common.fx.cdi.form;

import com.github.windchopper.common.util.Pipeliner;
import com.github.windchopper.common.util.Resource;
import javafx.collections.ObservableMap;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;
import java.util.function.Supplier;

public class StageFormLoad extends FormLoad {

    private final Supplier<Stage> stageSupplier;

    public StageFormLoad(Resource resource, Supplier<Stage> stageSupplier) {
        this(resource, Map.of(), stageSupplier);
    }

    public StageFormLoad(Resource resource, Map<String, ?> parameters, Supplier<Stage> stageSupplier) {
        super(resource, parameters);
        this.stageSupplier = stageSupplier;
    }

    @Override public void afterLoad(Parent form, Object controller, Map<String, ?> parameters, ObservableMap<String, ?> formNamespace) {
        Stage stage = Pipeliner.of(stageSupplier)
            .set(suppliedStage -> suppliedStage::setScene, Pipeliner.of(form)
                .map(Scene::new)
                .get())
            .get();

        super.afterLoad(form, controller, parameters, formNamespace);

        stage.show();
    }

}
