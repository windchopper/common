package name.wind.common.text;

import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class NumeralGeneratorTest {

    void testValue(NumeralGenerator generator, BigInteger value, Gender gender, String expectedPhrase) {
        String phrase = generator.generate(value, gender).toLowerCase();
        System.out.println(value + ": " + phrase);
        assertEquals(expectedPhrase.toLowerCase(), phrase.toLowerCase());
    }

    @Test public void testRussian() throws IOException {
        NumeralGenerator generator = new NumeralGenerator(Locale.forLanguageTag("ru"));

        testValue(generator, new BigInteger("123"), Gender.MASCULINE, "сто двадцать три");
        testValue(generator, new BigInteger("1"), Gender.FEMININE, "одна");
        testValue(generator, new BigInteger("11"), Gender.MASCULINE, "одиннадцать");
        testValue(generator, new BigInteger("7865"), Gender.MASCULINE, "семь тысяч восемьсот шестьдесят пять");
        testValue(generator, new BigInteger("90872435"), Gender.MASCULINE, "девяносто миллионов восемьсот семьдесят две тысячи четыреста тридцать пять");
    }

    @Test public void testEnglish() throws IOException {
        NumeralGenerator generator = new NumeralGenerator(Locale.forLanguageTag("en"));
    }

}
