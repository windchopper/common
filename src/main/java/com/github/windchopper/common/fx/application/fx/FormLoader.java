package com.github.windchopper.common.fx.application.fx;

import com.github.windchopper.common.cdi.BeanReference;
import com.github.windchopper.common.fx.application.event.ResourceBundleLoading;
import com.github.windchopper.common.fx.application.fx.annotation.FXMLResourceLiteral;
import com.github.windchopper.common.fx.application.fx.event.FXMLResourceOpen;
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
        FXMLLoader fxmlLoader = new FXMLLoader();

        if (resources != null) {
            fxmlLoader.setResources(resources);
        }

        try (InputStream inputStream = fxmlResourceOpen.resourceAsStream()) {
            FXMLResourceLiteral fxmlResourceLiteral = new FXMLResourceLiteral(fxmlResourceOpen.resource());
            BeanReference controllerReference = new BeanReference().withType(StageController.class).withQualifiers(fxmlResourceLiteral);
            StageController controller = (StageController) controllerReference.resolve();
            fxmlLoader.setController(controller);
            Parent sceneRoot = fxmlLoader.load(inputStream);
            Scene scene = new Scene(sceneRoot);
            Stage stage = fxmlResourceOpen.stage();
            stage.setScene(scene);
            controller.start(stage, fxmlResourceOpen.resource(), fxmlResourceOpen.parameters());
            stage.show();
        }
    }

}
