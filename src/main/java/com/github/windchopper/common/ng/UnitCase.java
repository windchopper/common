package com.github.windchopper.common.ng;

import java.util.function.Function;

public enum UnitCase {

    NOMINATIVE(ScaleUnit::nominative),
    GENITIVE(ScaleUnit::genitive),
    PLURAL(ScaleUnit::plural);

    private final Function<ScaleUnit, String> extractor;

    UnitCase(Function<ScaleUnit, String> extractor) {
        this.extractor = extractor;
    }

    public String scaleUnitName(ScaleUnit scaleUnit) {
        return extractor.apply(scaleUnit);
    }

}
