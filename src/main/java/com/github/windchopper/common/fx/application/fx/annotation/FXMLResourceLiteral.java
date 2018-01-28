package com.github.windchopper.common.fx.application.fx.annotation;

import javax.enterprise.util.AnnotationLiteral;

public class FXMLResourceLiteral extends AnnotationLiteral<FXMLResource> implements FXMLResource {

    private final String value;

    public FXMLResourceLiteral(String value) {
        this.value = value;
    }

    @Override public String value() {
        return value;
    }

}
