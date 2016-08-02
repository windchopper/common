package name.wind.common.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WildcardMultiphraseMatcher {

    private final Pattern quotedPhrasePattern = Pattern.compile("(?<all>[\"](?<text>.+?)[\"])");
    private final Pattern simplePhrasePattern = Pattern.compile("(?<text>[^\\p{Z}\"]+)");

    private final Pattern combinedPattern;

    public WildcardMultiphraseMatcher(String searchPhrase) {
        Map<String, String> quotedPhrases = new HashMap<>();
        Set<String> phrases = new HashSet<>(), expressions = new HashSet<>();

        while (true) {
            Matcher matcher = quotedPhrasePattern.matcher(searchPhrase);

            if (matcher.find()) {
                String replacement = Long.toHexString(System.nanoTime());
                quotedPhrases.put(replacement, "\\Q" + matcher.group("text") + "\\E");
                searchPhrase = matcher.replaceFirst(replacement);
            } else {
                break;
            }
        }

        Matcher matcher = simplePhrasePattern.matcher(searchPhrase);

        for (int index = 0; matcher.find(index); index = matcher.end()) {
            phrases.add(matcher.group("text"));
        }

        for (String phrase : phrases) {
            for (Map.Entry<String, String> quotedPhraseEntry : quotedPhrases.entrySet()) {
                phrase = phrase.replace(
                    quotedPhraseEntry.getKey(),
                    quotedPhraseEntry.getValue());
            }

            expressions.add(
                "(" + phrase.replace("*", ".*").replace("?", ".") + ")");
        }

        combinedPattern = Pattern.compile(
            String.join("|", expressions), Pattern.CASE_INSENSITIVE);
    }

    public <T> Predicate<T> toPredicate(Function<T, String> stringifier) {
        return object -> combinedPattern.matcher(
            stringifier.apply(object)).find();
    }

}
