package com.github.windchopper.common.fx.application.annotation;

import javax.enterprise.util.AnnotationLiteral;

public class ActionLiteral extends AnnotationLiteral<Action> implements Action {

    private final String value;

    public ActionLiteral(String value) {
        this.value = value;
    }

    @Override public String value() {
        return null;
    }

}
