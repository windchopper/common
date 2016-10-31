import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NumInWords {

    private static FormatData formatData;

    public static String convert(String valueString) throws IllegalArgumentException, IOException {
        Deque<String> words = new LinkedList<>();

        if (formatData == null) {
            formatData = new FormatData();
        }

        BigInteger value = new BigInteger(valueString), groupCount = BigInteger.ZERO;
        BigInteger radix = BigInteger.valueOf(10), groupPlaceCount = BigInteger.valueOf(1000);

        boolean negative = value.compareTo(BigInteger.ZERO) < 0;

        if (negative) {
            value = value.abs();
        }

        BigInteger maximumDecimalPlaces = formatData.groupIndex.keySet().stream()
            .max(BigInteger::compareTo)
            .orElseThrow(() -> new IllegalStateException("что-то не так с файликом групп разрядов"));

        if (value.toString().length() - 3 > maximumDecimalPlaces.intValue()) {
            throw new IllegalArgumentException("многовато");
        }

        Group.Gender gender = null;

        while (value.compareTo(BigInteger.ZERO) > 0) {
            Group group = formatData.groupIndex.get(groupCount);

            if (group != null) {
                BigInteger quotient = value.divide(groupPlaceCount);
                words.addFirst(formatData.numeralIndex.get(quotient.remainder(radix)).decl.formExtractor.apply(group));
                value = value.divide(groupPlaceCount);
                gender = group.gender;
            }

            for (Numeral numeral : formatData.numerals) {
                BigInteger remainder = value.remainder(numeral.divider);
                if (remainder.compareTo(BigInteger.ZERO) > 0) {
                    Numeral valueNumeral = formatData.numeralIndex.get(remainder);
                    if (valueNumeral != null) {
                        if (gender != null) {
                            words.addFirst(gender.formExtractor.apply(valueNumeral));
                            gender = null;
                        } else {
                            words.addFirst(valueNumeral.word);
                        }
                        value = value.subtract(valueNumeral.value);
                        groupCount = groupCount.add(BigInteger.ONE);
                        break;
                    }
                }
            }
        }

        if (negative) {
            words.addFirst("минус");
        }

        return String.join(" ", words);
    }

    public static void main(String... args) throws IOException {
        System.out.println(convert("-23542436375672435"));
    }

}

class Numeral {

    enum Decl {

        N(group -> group.nominative),
        G(group -> group.genitive),
        P(group -> group.plural);

        final Function<Group, String> formExtractor;

        Decl(Function<Group, String> formExtractor) {
            this.formExtractor = formExtractor;
        }

    }

    private static final Pattern pattern = Pattern.compile(
        "^(?<divider>\\d+)[,\\s]+(?<value>\\d+)[,\\s]+\"(?<word>.*?)\"[,\\s]+(?<decl>.*?)([,\\s]+\"(?<femaleWord>.*)\")?$");

    final BigInteger divider;
    final BigInteger value;
    final String word;
    final Decl decl;
    final String femaleWord;

    Numeral(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            divider = new BigInteger(matcher.group("divider"));
            value = new BigInteger(matcher.group("value"));
            word = matcher.group("word");
            decl = Decl.valueOf(matcher.group("decl"));
            femaleWord = matcher.group("femaleWord");
        } else {
            throw new IllegalArgumentException(line);
        }
    }

}

class Group {

    private static final Pattern pattern = Pattern.compile(
        "^(?<count>\\d+)[,\\s]+\"(?<nominative>.*?)\"[,\\s]+\"(?<genitive>.*?)\"[,\\s]+\"(?<plural>.*?)\"[,\\s]+(?<gender>.*)$");

    final BigInteger count;
    final String nominative;
    final String genitive;
    final String plural;
    final Gender gender;

    enum Gender {

        M(numeral -> numeral.word),
        F(numeral -> numeral.femaleWord);

        final Function<Numeral, String> formExtractor;

        Gender(Function<Numeral, String> formExtractor) {
            this.formExtractor = formExtractor;
        }

    }

    Group(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            count = new BigInteger(matcher.group("count"));
            nominative = matcher.group("nominative");
            genitive = matcher.group("genitive");
            plural = matcher.group("plural");
            gender = Gender.valueOf(matcher.group("gender"));
        } else {
            throw new IllegalArgumentException();
        }
    }

}

class FormatData {

    final Set<Numeral> numerals;
    final Map<BigInteger, Group> groupIndex;
    final Map<BigInteger, Numeral> numeralIndex;

    FormatData() throws IOException {
        numerals = Files.readAllLines(Paths.get("numerals.csv")).stream().filter(line -> line.length() > 0)
            .map(Numeral::new).collect(Collectors.toSet());
        groupIndex = Files.readAllLines(Paths.get("groups.csv")).stream().filter(line -> line.length() > 0)
            .map(Group::new).collect(Collectors.toMap(group -> group.count, group -> group));
        numeralIndex = numerals.stream()
            .collect(Collectors.toMap(numeral -> numeral.value, numeral -> numeral));
    }

}

