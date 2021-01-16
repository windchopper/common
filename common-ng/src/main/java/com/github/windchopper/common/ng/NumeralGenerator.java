package com.github.windchopper.common.ng;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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
        var lines = new ArrayList<String>();

        try (
            var resourceStream = NumeralGenerator.class.getResourceAsStream("/com/github/windchopper/common/ng/i18n/" + resource + "_" + locale.getLanguage() + ".csv");
            var inputStreamReader = new InputStreamReader(resourceStream, StandardCharsets.UTF_8);
            var bufferedReader = new BufferedReader(inputStreamReader)) {

            for (var line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
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
            for (var entry : units.entrySet()) {
                var limit = entry.getKey();

                if (value.compareTo(limit.abs()) < 0) {
                    var remainder = value.remainder(limit.signum() < 0 ? BigInteger.ONE : limit.divide(RADIX));
                    generateWords(remainder, unitCases, scaleUnitGenders, words);
                    var unit = entry.getValue().get(value.subtract(remainder));
                    unitCases.push(unit.form());

                    try {
                        words.addFirst(scaleUnitGenders.pop().numeral(unit));
                    } catch (EmptyStackException ignored) {
                        words.addFirst(unit.masculine());
                    }

                    break outer;
                }
            }

            for (var scaleUnit : scaleUnits) {
                var divider = RADIX.pow(scaleUnit.power().intValue());

                if (value.compareTo(divider.multiply(SCALE_STEP)) < 0) {
                    var remainder = value.remainder(divider);
                    generateWords(remainder, unitCases, scaleUnitGenders, words);
                    var beforeSize = words.size();
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
        return String.join(delimiter, generateWords(
            value.abs(), new Stack<>(), new Stack<>(), new LinkedList<>()));
    }

}
