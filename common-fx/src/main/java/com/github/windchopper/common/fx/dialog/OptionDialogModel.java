package com.github.windchopper.common.fx.dialog;

import java.util.Collection;

public class OptionDialogModel {

    private OptionDialog.Type type;
    private String message;
    private Collection<OptionDialog.Option> availableOptions;
    private OptionDialog.Option selectedOption;

    public OptionDialog.Type getType() {
        return type;
    }

    public void setType(OptionDialog.Type type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<OptionDialog.Option> getAvailableOptions() {
        return availableOptions;
    }

    public void setAvailableOptions(Collection<OptionDialog.Option> availableOptions) {
        this.availableOptions = availableOptions;
    }

    public OptionDialog.Option getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(OptionDialog.Option selectedOption) {
        this.selectedOption = selectedOption;
    }

}
