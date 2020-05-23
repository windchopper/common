package com.github.windchopper.common.fx.dialog;

import com.github.windchopper.common.fx.Action;

public class DialogAction extends Action {

    public enum ThreatThreshold {

        REJECT(Integer.MIN_VALUE, -1),
        INDEFINITE(0, 1000),
        ACCEPT(1001, Integer.MAX_VALUE);

        private final int min;
        private final int max;

        ThreatThreshold(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int min() {
            return min;
        }

        public int max() {
            return max;
        }

        public boolean belongs(DialogAction action) {
            return max >= action.threat && min <= action.threat;
        }

    }

    private final Dialog<?, ?, ?> dialog;
    private final int threat;

    public DialogAction(Dialog<?, ?, ?> dialog, int threat) {
        this.dialog = dialog;
        this.threat = threat;
    }

    @Override public void addHandler(EventHandler handler) {
        if (ThreatThreshold.ACCEPT.belongs(this) || ThreatThreshold.REJECT.belongs(this)) {
            var originalHandler = handler;
            handler = (actionEvent, action) -> {
                originalHandler.handle(actionEvent, action);
                dialog.hide();
            };
        }

        super.addHandler(handler);
    }

}
