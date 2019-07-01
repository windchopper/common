package com.github.windchopper.common.fx.application;

import com.github.windchopper.common.cdi.BeanReference;
import com.github.windchopper.common.fx.annotation.FXMLResourceLiteral;
import com.github.windchopper.common.fx.event.FXMLResourceOpen;
import com.github.windchopper.common.fx.event.ResourceBundleLoading;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

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

    protected void runFxThreadSensitiveAction(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
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
            var loaded = fxmlLoader.<Parent>load(inputStream);

            runFxThreadSensitiveAction(() -> {
                var openingStage = fxmlResourceOpen.stage();
                openingStage.setScene(new Scene(loaded));

                if (fxmlLoader.getController() instanceof StageController) {
                    ((StageController) fxmlLoader.getController()).start(openingStage,  fxmlResourceOpen.resource(), fxmlResourceOpen.parameters(), fxmlLoader.getNamespace());
                }

                openingStage.show();
            });
        }
    }

}
