package com.github.windchopper.common.fx.form;

import javax.enterprise.util.AnnotationLiteral;

public class FormLiteral extends AnnotationLiteral<Form> implements Form {

    private final String value;

    public FormLiteral(String value) {
        this.value = value;
    }

    @Override public String value() {
        return value;
    }

}
