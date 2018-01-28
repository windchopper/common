package com.github.windchopper.common.fx.dialog;

import com.github.windchopper.common.util.Pipeliner;

import java.util.ResourceBundle;

public class OptionDialogModel {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.fx.i18n.messages");

    private static final String BUNDLE_KEY__OK = "com.github.windchopper.common.fx.dialog.OptionDialogModel.ok";
    private static final String BUNDLE_KEY__CANCEL = "com.github.windchopper.common.fx.dialog.OptionDialogModel.cancel";
    private static final String BUNDLE_KEY__YES = "com.github.windchopper.common.fx.dialog.OptionDialogModel.yes";
    private static final String BUNDLE_KEY__NO = "com.github.windchopper.common.fx.dialog.OptionDialogModel.no";

    public enum Option {

        OK(bundle.getString(BUNDLE_KEY__OK), DialogAction.ThreatThreshold.ACCEPT.minDegree() + 1),
        CANCEL(bundle.getString(BUNDLE_KEY__CANCEL), DialogAction.ThreatThreshold.REJECT.maxDegree() - 1),
        YES(bundle.getString(BUNDLE_KEY__YES), DialogAction.ThreatThreshold.ACCEPT.minDegree() + 2),
        NO(bundle.getString(BUNDLE_KEY__NO), DialogAction.ThreatThreshold.REJECT.maxDegree() - 2);

        private final String label;
        private final int threat;

        Option(String label, int threat) {
            this.label = label;
            this.threat = threat;
        }

        DialogAction newAction(Dialog<?, ?> dialog) {
            return Pipeliner.of(() -> new DialogAction(dialog, threat))
                .accept(action -> action.textProperty().set(label))
                .get();
        }

    }

    /*
     *
     */

    private Option option;

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

}
