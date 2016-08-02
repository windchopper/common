package name.wind.common.search;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Predicate;

public class WildcardMultiphraseMattcherTest {

    @Test public void testSearch() {
        String searchPhrase = "abc1 de* \"ghi\"* j??4 \"3 j\"";
        WildcardMultiphraseMatcher matcher = new WildcardMultiphraseMatcher(searchPhrase);
        Predicate<Object> predicate = matcher.toPredicate(Object::toString);

        Assert.assertTrue(predicate.test("abc1"));
        Assert.assertTrue(predicate.test("def2"));
        Assert.assertTrue(predicate.test("ghi3"));
        Assert.assertTrue(predicate.test("jkl4"));
        Assert.assertTrue(predicate.test("abc1 def2 ghi3 jkl4"));
    }

}
