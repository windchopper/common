package com.github.windchopper.common.ng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

public class NumeralGenerator {

    private static final BigInteger RADIX = BigInteger.valueOf(10);
    private static final BigInteger SCALE_STEP = BigInteger.valueOf(1000);

    private final Map<BigInteger, Map<BigInteger, Unit>> units;
    private final Set<ScaleUnit> scaleUnits;

    /*
     *
     */

    public NumeralGenerator(Locale locale) throws IOException {
        units = readResource("units", locale).stream()
            .filter(line -> line.length() > 0).map(Unit::new).collect(
                groupingBy(Unit::limit, TreeMap::new, Collectors.toMap(Unit::value, unit -> unit, (unit1st, unit2nd) -> unit1st, TreeMap::new)));
        scaleUnits = readResource("scaleUnits", locale)
            .stream().filter(line -> line.length() > 0).map(ScaleUnit::new).collect(
                toCollection(TreeSet::new));
    }

    /*
     *
     */

    private static List<String> readResource(String resource, Locale locale) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(
                NumeralGenerator.class.getResourceAsStream(
                    "/com/github/windchopper/common/ng/i18n/" + resource + "_" + locale.getLanguage() + ".csv"), StandardCharsets.UTF_8))) {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                lines.add(line);
            }
        }

        return lines;
    }

    /*
     *
     */

    private LinkedList<String> generateWords(BigInteger value, Stack<UnitCase> unitCases, Stack<ScaleUnitGender> scaleUnitGenders, LinkedList<String> words) {
        outer: for ( ; value.compareTo(BigInteger.ZERO) > 0; ) {
            for (Map.Entry<BigInteger, Map<BigInteger, Unit>> entry : units.entrySet()) {
                BigInteger limit = entry.getKey();

                if (value.compareTo(limit.abs()) < 0) {
                    BigInteger remainder = value.remainder(limit.signum() < 0 ? BigInteger.ONE : limit.divide(RADIX));
                    generateWords(remainder, unitCases, scaleUnitGenders, words);
                    Unit unit = entry.getValue().get(value.subtract(remainder));
                    unitCases.push(unit.form());

                    try {
                        words.addFirst(scaleUnitGenders.pop().numeral(unit));
                    } catch (EmptyStackException ignored) {
                        words.addFirst(unit.masculine());
                    }

                    break outer;
                }
            }

            for (ScaleUnit scaleUnit : scaleUnits) {
                BigInteger divider = RADIX.pow(scaleUnit.power().intValue());

                if (value.compareTo(divider.multiply(SCALE_STEP)) < 0) {
                    BigInteger remainder = value.remainder(divider);
                    generateWords(remainder, unitCases, scaleUnitGenders, words);
                    int beforeSize = words.size();
                    scaleUnitGenders.push(scaleUnit.gender());
                    generateWords(value.subtract(remainder).divide(divider), unitCases, scaleUnitGenders, words);
                    words.add(words.size() - beforeSize, unitCases.pop().scaleUnitName(scaleUnit));

                    break outer;
                }
            }
        }

        return words;
    }

    /*
     *
     */

    public String generate(String delimiter, BigInteger value) {
        return String.join(requireNonNull(delimiter), generateWords(
            requireNonNull(value).abs(), new Stack<>(), new Stack<>(), new LinkedList<>()));
    }

}
