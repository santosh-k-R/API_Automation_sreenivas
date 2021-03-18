package com.cisco.services.api_automation.utils.customassert;

import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import java.util.Iterator;
import java.util.Map;

public class SoftAssert extends Assertion{
    private final Map<AssertionError, IAssert<?>> m_errors = Maps.newLinkedHashMap();
    private static final String DEFAULT_SOFT_ASSERT_MESSAGE = "The following asserts failed:";

    public SoftAssert() {
    }

    protected void doAssert(IAssert<?> a) {
        this.onBeforeAssert(a);

        try {
            a.doAssert();
            this.onAssertSuccess(a);
        } catch (AssertionError var6) {
            this.onAssertFailure(a, var6);
            this.m_errors.put(var6, a);
        } finally {
            this.onAfterAssert(a);
        }

    }

    public void assertAll() {
        this.assertAll((String)null);
    }

    public void assertAll(String message) {
        System.out.println(message);
        if (!this.m_errors.isEmpty()) {
            StringBuilder sb = new StringBuilder(null == message ? "The following asserts failed:" : message);
            boolean first = true;
            Iterator var4 = this.m_errors.keySet().iterator();

            while(var4.hasNext()) {
                AssertionError error = (AssertionError)var4.next();
                if (first) {
                    first = false;
                } else {
                    sb.append(",");
                }

                sb.append("\n\t");
                sb.append(this.getErrorDetails(error));
            }

            throw new AssertionError(sb.toString());
        }
    }
}
