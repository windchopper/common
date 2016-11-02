package name.wind.common.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class NumeralGenerator {

    private static final BigInteger RADIX = BigInteger.valueOf(10);
    private static final BigInteger SCALE_STEP = BigInteger.valueOf(1000);

    private final Map<Integer, ScaleUnit> scaleUnits;
    private final Map<BigInteger, Unit> units;

    public NumeralGenerator(Locale locale) throws IOException {
        scaleUnits = readResource("scaleUnits", locale).stream().filter(line -> line.length() > 0).map(ScaleUnit::new)
            .collect(Collectors.toMap(ScaleUnit::width, scaleUnit -> scaleUnit));
        units = readResource("units", locale).stream().filter(line -> line.length() > 0).map(Unit::new)
            .collect(Collectors.toMap(Unit::value, unit -> unit, (key, value) -> { throw new IllegalArgumentException(key.toString()); }, () -> new TreeMap<>((o1, o2) -> o2.compareTo(o1))));
    }

    private static List<String> readResource(String resource, Locale locale) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(
                NumeralGenerator.class.getResourceAsStream(
                    "/name/wind/common/i18n/text/" + resource + "_" + locale.getLanguage() + ".csv"), StandardCharsets.UTF_8))) {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                lines.add(line);
            }
        }

        return lines;
    }

    public String generate(BigInteger value, Gender gender) {
        LinkedList<String> words = new LinkedList<>();

        Integer width = 0;
        value = value.abs();

        while (value.compareTo(BigInteger.ZERO) > 0) {
            ScaleUnit currentScaleUnit = scaleUnits.get(width);

            if (currentScaleUnit != null) {
                BigInteger scaleUnitValue = value.divide(SCALE_STEP);

                if (scaleUnitValue.compareTo(SCALE_STEP) > 0) {
                    scaleUnitValue = scaleUnitValue.remainder(RADIX);
                }

                if (scaleUnitValue.compareTo(BigInteger.ZERO) > 0) {
                    words.addFirst(units.get(scaleUnitValue).form().scaleUnitName(currentScaleUnit));
                }

                value = value.divide(SCALE_STEP);
                gender = currentScaleUnit.gender();
            }

            for (Unit numeral : units.values()) {
                BigInteger remainder = value.remainder(numeral.divider());
                if (remainder.compareTo(BigInteger.ZERO) > 0) {
                    Unit selectedUnit = units.get(remainder);
                    if (selectedUnit != null) {
                        if (gender != null) {
                            words.addFirst(gender.numeral(selectedUnit));
                            gender = null;
                        } else {
                            words.addFirst(selectedUnit.masculine());
                        }
                        value = value.subtract(selectedUnit.value());
                        width = width + 1;
                        break;
                    }
                }
            }
        }

        return String.join(" ", words);
    }

}
