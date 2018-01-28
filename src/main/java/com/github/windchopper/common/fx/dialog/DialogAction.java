package com.github.windchopper.common.fx.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import com.github.windchopper.common.fx.Action;

public class DialogAction extends Action {

    public enum ThreatThreshold {

        REJECT(Integer.MIN_VALUE, -1),
        INDEFINITE(0, 0),
        ACCEPT(+1, Integer.MAX_VALUE);

        private final int minDegree;
        private final int maxDegree;

        ThreatThreshold(int minDegree, int maxDegree) {
            this.minDegree = minDegree;
            this.maxDegree = maxDegree;
        }

        public int minDegree() {
            return minDegree;
        }

        public int maxDegree() {
            return maxDegree;
        }

        public boolean belongs(DialogAction action) {
            return maxDegree >= action.threat && minDegree <= action.threat;
        }

    }

    /*
     *
     */

    private final Dialog<?, ?> dialog;
    private final int threat;

    public DialogAction(Dialog<?, ?> dialog, int threat) {
        this.dialog = dialog;
        this.threat = threat;
    }

    @Override
    public void setHandler(EventHandler<ActionEvent> handler) {
        if (ThreatThreshold.ACCEPT.belongs(this) || ThreatThreshold.REJECT.belongs(this)) {
            EventHandler<ActionEvent> originalHandler = handler;
            handler = event -> {
                originalHandler.handle(event);
                dialog.hide();
            };
        }

        super.setHandler(handler);
    }

}
