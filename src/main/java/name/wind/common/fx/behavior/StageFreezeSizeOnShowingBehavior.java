package name.wind.common.fx.behavior;

import javafx.stage.Stage;
import name.wind.common.util.Value;

import static javafx.stage.WindowEvent.WINDOW_SHOWING;

public class StageFreezeSizeOnShowingBehavior implements Behavior<Stage>  {

    @Override public void apply(Stage stage) {
        stage.addEventHandler(
            WINDOW_SHOWING, event -> Value.of(stage)
                .with(target -> target.setMinWidth(target.getWidth()))
                .with(target -> target.setMinHeight(target.getHeight())));
    }

}
