package name.wind.common.search;

import name.wind.common.util.regex.Patterns;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternsTest {

    @Test public void testWildcardMultiwordPattern() {
        String searchPhrase = "abc1 de* \"ghi\"* j??4 \"3 j\"";

        Pattern pattern = Patterns.wildcardMultiwordPattern(searchPhrase);
        Matcher matcher = pattern.matcher(
            "abc1\n" +
            "def2\n" +
            "ghi3\n" +
            "jkl4\n");

        Assert.assertTrue(matcher.find());
        Assert.assertEquals("abc1", matcher.group());
        Assert.assertTrue(matcher.find());
        Assert.assertEquals("def2", matcher.group());
        Assert.assertTrue(matcher.find());
        Assert.assertEquals("ghi3", matcher.group());
        Assert.assertTrue(matcher.find());
        Assert.assertEquals("jkl4", matcher.group());
    }

}
