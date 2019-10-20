package com.github.windchopper.common.ng;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumeralGeneratorTest {

    void testValue(NumeralGenerator generator, BigInteger value, String expectedPhrase) {
        String phrase = generator.generate(" ", value).toLowerCase();
        System.out.println(value + ": " + phrase);
        assertEquals(expectedPhrase.toLowerCase(), phrase.toLowerCase());
    }

    @Test public void testRussian() throws IOException {
        var generator = new NumeralGenerator(Locale.forLanguageTag("ru"));

        testValue(generator, new BigInteger("1"), "один");
        testValue(generator, new BigInteger("11"), "одиннадцать");
        testValue(generator, new BigInteger("99"), "девяносто девять");
        testValue(generator, new BigInteger("123"), "сто двадцать три");
        testValue(generator, new BigInteger("2815"), "две тысячи восемьсот пятнадцать");
        testValue(generator, new BigInteger("7865"), "семь тысяч восемьсот шестьдесят пять");
        testValue(generator, new BigInteger("468354"), "четыреста шестьдесят восемь тысяч триста пятьдесят четыре");
        testValue(generator, new BigInteger("9072435"), "девять миллионов семьдесят две тысяч четыреста тридцать пять");
        testValue(generator, new BigInteger("16435789"), "шестнадцать миллионов четыреста тридцать пять тысяч семьсот восемьдесят девять");
        testValue(generator, new BigInteger("817435789"), "восемьсот семнадцать миллионов четыреста тридцать пять тысяч семьсот восемьдесят девять");
        testValue(generator, new BigInteger("2468435780"), "два миллиарда четыреста шестьдесят восемь миллионов четыреста тридцать пять тысяч семьсот восемьдесят");
    }

    @Test public void testEnglish() throws IOException {
        var generator = new NumeralGenerator(Locale.forLanguageTag("en"));

        testValue(generator, new BigInteger("1"), "one");
        testValue(generator, new BigInteger("11"), "eleven");
        testValue(generator, new BigInteger("99"), "ninety nine");
        testValue(generator, new BigInteger("123"), "one hundred twenty three");
        testValue(generator, new BigInteger("2815"), "two thousand eight hundred fifteen");
        testValue(generator, new BigInteger("7865"), "seven thousand eight hundred sixty five");
        testValue(generator, new BigInteger("468354"), "four hundred sixty eight thousand three hundred fifty four");
        testValue(generator, new BigInteger("9072435"), "nine million seventy two thousand four hundred thirty five");
        testValue(generator, new BigInteger("16435789"), "sixteen million four hundred thirty five thousand seven hundred eighty nine");
        testValue(generator, new BigInteger("817435789"), "eight hundred seventeen million four hundred thirty five thousand seven hundred eighty nine");
        testValue(generator, new BigInteger("2468435780"), "two billion four hundred sixty eight million four hundred thirty five thousand seven hundred eighty");
    }

}
