package name.wind.common.text;

import java.util.function.Function;

public enum WordForm {

    NOMINATIVE(ScaleUnit::nominative),
    GENITIVE(ScaleUnit::genitive),
    PLURAL(ScaleUnit::plural);

    private final Function<ScaleUnit, String> extractor;

    WordForm(Function<ScaleUnit, String> extractor) {
        this.extractor = extractor;
    }

    public String scaleUnitName(ScaleUnit scaleUnit) {
        return extractor.apply(scaleUnit);
    }

}
