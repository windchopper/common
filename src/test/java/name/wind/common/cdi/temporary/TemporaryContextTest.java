package name.wind.common.cdi.temporary;

import name.wind.common.cdi.method.WithTemporaryScope;
import name.wind.common.test.WeldTestRunner;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@RunWith(WeldTestRunner.class) @FixMethodOrder(MethodSorters.NAME_ASCENDING) @ApplicationScoped public class TemporaryContextTest {

    @Inject TemporaryBean temporary;

    @Test @WithTemporaryScope public void test1st() {
        temporary.test();
    }

    @Test public void test2nd() {
        Assert.assertTrue(TemporaryBean.postConstructInvoked);
        Assert.assertTrue(TemporaryBean.preDestroyInvoked);
    }

}
