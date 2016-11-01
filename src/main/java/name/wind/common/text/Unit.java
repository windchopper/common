package name.wind.common.text;

import java.math.BigInteger;
import java.util.EnumSet;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Unit {

    private enum CapturingGroup {

        DIVIDER,
        VALUE,
        MASCULINE,
        FORM,
        FEMININE;

        public <T> T extract(Matcher matcher, Function<String, T> converter) {
            return converter.apply(
                matcher.group(
                    name()));
        }

    }

    private enum Expression {

        DIVIDER(CapturingGroup.DIVIDER, "\\d+", false),
        VALUE(CapturingGroup.VALUE, "\\d+", false),
        MASCULINE(CapturingGroup.MASCULINE, ".+?", true),
        FORM(CapturingGroup.FORM, "\\w+", false),
        FEMININE(CapturingGroup.FEMININE, ".+?", true);

        private final CapturingGroup capturingGroup;
        private final String bodyExpression;
        private final boolean quoted;

        Expression(CapturingGroup capturingGroup, String bodyExpression, boolean quoted) {
            this.capturingGroup = capturingGroup;
            this.bodyExpression = bodyExpression;
            this.quoted = quoted;
        }

        public String expression() {
            StringBuilder expressionBuilder = new StringBuilder();

            if (quoted) {
                expressionBuilder.append("\"");
            }

            expressionBuilder
                .append("(?<")
                .append(capturingGroup)
                .append(">")
                .append(bodyExpression)
                .append(")");

            if (quoted) {
                expressionBuilder.append("\"");
            }

            return expressionBuilder.toString();
        }

    }

    private static final Pattern linePattern = Pattern.compile(
        EnumSet.allOf(Expression.class).stream().map(Expression::expression).collect(Collectors.joining("[,\\s]+", "^", "$")));

    private final BigInteger divider;
    private final BigInteger value;
    private final String masculine;
    private final WordForm form;
    private final String feminine;

    public Unit(String line) {
        Matcher lineMatcher = linePattern.matcher(line);

        if (lineMatcher.matches()) {
            divider = CapturingGroup.DIVIDER.extract(lineMatcher, BigInteger::new);
            value = CapturingGroup.VALUE.extract(lineMatcher, BigInteger::new);
            masculine = CapturingGroup.MASCULINE.extract(lineMatcher, String::new);
            form = CapturingGroup.FORM.extract(lineMatcher, WordForm::valueOf);
            feminine = CapturingGroup.FEMININE.extract(lineMatcher, String::new);
        } else
            throw new IllegalArgumentException(line);
    }

    public BigInteger divider() {
        return divider;
    }

    public BigInteger value() {
        return value;
    }

    public String masculine() {
        return masculine;
    }

    public WordForm form() {
        return form;
    }

    public String feminine() {
        return feminine;
    }

}
