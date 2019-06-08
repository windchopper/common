package com.github.windchopper.common.fx.application;

import com.github.windchopper.common.cdi.BeanReference;
import com.github.windchopper.common.fx.annotation.FXMLResourceLiteral;
import com.github.windchopper.common.fx.event.FXMLResourceOpen;
import com.github.windchopper.common.fx.event.ResourceBundleLoading;
import com.github.windchopper.common.util.Pipeliner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

@ApplicationScoped public class FormLoader {

    private ResourceBundle resources;

    protected void registerResourceBundle(@Observes ResourceBundleLoading resourceBundleLoading) {
        resources = resourceBundleLoading.resources();
    }

    protected void formOpen(@Observes FXMLResourceOpen fxmlResourceOpen) throws IOException {
        var fxmlLoader = new FXMLLoader();

        if (resources != null) {
            fxmlLoader.setResources(resources);
        }

        var fxmlResourceLiteral = new FXMLResourceLiteral(fxmlResourceOpen.resource());

        fxmlLoader.setControllerFactory(controllerType -> new BeanReference()
            .withType(controllerType)
            .withQualifiers(fxmlResourceLiteral)
            .resolve());

        try (InputStream inputStream = fxmlResourceOpen.resourceAsStream()) {
            Stage stage = Pipeliner.of(fxmlResourceOpen.stage())
                .set(bean -> bean::setScene, Pipeliner.of(fxmlLoader.<Parent>load(inputStream))
                    .map(Scene::new)
                    .get())
                .get();

            var controller = fxmlLoader.getController();

            if (controller instanceof StageController) {
                ((StageController) controller).start(stage,  fxmlResourceOpen.resource(), fxmlResourceOpen.parameters());
            }

            stage.show();
        }
    }

}
