package name.wind.common.fx.behavior;

import javafx.scene.Node;
import javafx.scene.effect.Effect;

import java.util.HashMap;
import java.util.Map;

public class StickOutBehavior extends AbstractPointerMovementReactionBehavior<Node> {

    private final Map<Node, Effect> savedEffects = new HashMap<>(0);

    private final Effect effect;
    private final double scaleX;
    private final double scaleY;

    public StickOutBehavior(Effect effect, double scaleX, double scaleY) {
        this.effect = effect;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override protected void pointerHoveredOver(Node target) {
        savedEffects.put(target, target.getEffect());
        target.setEffect(effect);
        target.setScaleX(scaleX);
        target.setScaleY(scaleY);
    }

    @Override protected void pointerMovedAway(Node target) {
        target.setEffect(savedEffects.remove(target));
        target.setScaleX(1.0);
        target.setScaleY(1.0);
    }

}
