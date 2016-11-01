package name.wind.common.text;

import java.util.EnumSet;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScaleUnit {

    private enum CapturingGroup {

        WIDTH,
        NOMINATIVE,
        GENITIVE,
        PLURAL,
        GENDER;

        public <T> T extract(Matcher matcher, Function<String, T> converter) {
            return converter.apply(
                matcher.group(
                    name()));
        }

    }

    private enum Expression {

        WIDTH(CapturingGroup.WIDTH, "\\d+", false),
        NOMINATIVE(CapturingGroup.NOMINATIVE, ".+?", true),
        GENITIVE(CapturingGroup.GENITIVE, ".+?", true),
        PLURAL(CapturingGroup.PLURAL, ".+?", true),
        GENDER(CapturingGroup.GENDER, "\\w+", false);

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

    private final Integer width;
    private final String nominative;
    private final String genitive;
    private final String plural;
    private final Gender gender;

    public ScaleUnit(String line) throws IllegalArgumentException {
        Matcher lineMatcher = linePattern.matcher(line);

        if (lineMatcher.matches()) {
            width = CapturingGroup.WIDTH.extract(lineMatcher, Integer::new);
            nominative = CapturingGroup.NOMINATIVE.extract(lineMatcher, String::new);
            genitive = CapturingGroup.GENDER.extract(lineMatcher, String::new);
            plural = CapturingGroup.PLURAL.extract(lineMatcher, String::new);
            gender = CapturingGroup.GENDER.extract(lineMatcher, Gender::valueOf);
        } else
            throw new IllegalArgumentException(line);
    }

    public Integer width() {
        return width;
    }

    public String nominative() {
        return nominative;
    }

    public String genitive() {
        return genitive;
    }

    public String plural() {
        return plural;
    }

    public Gender gender() {
        return gender;
    }

}
