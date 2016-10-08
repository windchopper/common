package name.wind.common.util.regex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Patterns {

    private static final Pattern quotedPhrasePattern = Pattern.compile("(?<all>[\"](?<text>.+?)[\"])");
    private static final Pattern simplePhrasePattern = Pattern.compile("(?<text>[^\\p{Z}\"]+)");

    private Patterns() {
        // prevent instantiation
    }

    /*
     *
     */

    public static Pattern wildcardMultiwordPattern(String searchPhrase) {
        Map<String, String> quotedPhrases = new HashMap<>();

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

        Set<String> phrases = new HashSet<>();
        Set<String> expressions = new HashSet<>();

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

        return Pattern.compile(
            String.join("|", expressions), Pattern.CASE_INSENSITIVE);
    }

}
