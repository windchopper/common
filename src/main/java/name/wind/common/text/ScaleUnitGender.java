package name.wind.common.text;

import java.util.function.Function;

public enum ScaleUnitGender {

    MASCULINE(Unit::masculine),
    FEMININE(Unit::feminine);

    private final Function<Unit, String> extractor;

    ScaleUnitGender(Function<Unit, String> extractor) {
        this.extractor = extractor;
    }

    public String numeral(Unit unit) {
        return extractor.apply(unit);
    }

}
