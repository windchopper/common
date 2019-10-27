package com.github.windchopper.common.fx.cdi.form;

import com.github.windchopper.common.cdi.BeanReference;
import com.github.windchopper.common.fx.cdi.ResourceBundleLoad;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

@ApplicationScoped public class FormLoader {

    private ResourceBundle resources;

    protected void registerResourceBundle(@Observes ResourceBundleLoad resourceBundleLoad) {
        resources = resourceBundleLoad.resources();
    }

    protected void runFxThreadSensitiveAction(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
    }

    protected void formOpen(@Observes FormLoad event) throws IOException {
        var fxmlLoader = new FXMLLoader();

        if (resources != null) {
            fxmlLoader.setResources(resources);
        }

        var formLiteral = new FormLiteral(event.resource());

        fxmlLoader.setControllerFactory(controllerType -> new BeanReference<>(controllerType, formLiteral)
            .resolve());

        try (InputStream inputStream = event.resourceAsStream()) {
            var form = fxmlLoader.<Parent>load(inputStream);
            runFxThreadSensitiveAction(() -> event.afterLoad(form, fxmlLoader.getController(), event.parameters(), fxmlLoader.getNamespace()));
        }
    }

}