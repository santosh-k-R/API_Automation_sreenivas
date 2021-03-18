package com.cisco.services.api_automation.utils.customassert;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.asserts.IAssert;
import org.testng.asserts.IAssertLifecycle;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Assertion implements IAssertLifecycle {
    public Assertion() {
    }

    protected void doAssert(IAssert<?> assertCommand) {
        this.onBeforeAssert(assertCommand);

        try {
            this.executeAssert(assertCommand);
            this.onAssertSuccess(assertCommand);
        } catch (AssertionError var6) {
            this.onAssertFailure(assertCommand, var6);
            throw var6;
        } finally {
            this.onAfterAssert(assertCommand);
        }

    }

    public void executeAssert(IAssert<?> assertCommand) {
        assertCommand.doAssert();
    }

    public void onAssertSuccess(IAssert<?> assertCommand) {
    }

    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
    }

    public void onBeforeAssert(IAssert<?> assertCommand) {
    }

    public void onAfterAssert(IAssert<?> assertCommand) {
    }
    @Step("{1} : {0}")
    public void assertTrue(final boolean condition, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Boolean>(condition, Boolean.TRUE, message) {
            public void doAssert() {
                Assert.assertTrue(condition, message);
            }
        });
    }

    public void assertTrue(final boolean condition) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Boolean>(condition, Boolean.TRUE) {
            public void doAssert() {
                Assert.assertTrue(condition);
            }
        });
    }
    @Step("{1} : {0}")
    public void assertFalse(final boolean condition, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Boolean>(condition, Boolean.FALSE, message) {
            public void doAssert() {
                Assert.assertFalse(condition, message);
            }
        });
    }

    public void assertFalse(final boolean condition) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Boolean>(condition, Boolean.FALSE) {
            public void doAssert() {
                Assert.assertFalse(condition);
            }
        });
    }

    public void fail(final String message, final Throwable realCause) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(message) {
            public void doAssert() {
                Assert.fail(message, realCause);
            }
        });
    }

    public void fail(final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(message) {
            public void doAssert() {
                Assert.fail(message);
            }
        });
    }

    public void fail() {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>((String)null) {
            public void doAssert() {
                Assert.fail();
            }
        });
    }

    @Step("{2} : {0}")
    public <T> void assertEquals(final T actual, final T expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<T>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }

    public <T> void assertEquals(final T actual, final T expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<T>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }

    @Step("{2} : {0}")
    public void assertEquals(final String actual, final String expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<String>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }

    public void assertEquals(final String actual, final String expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<String>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }

    @Step("{3} : {0}")
    public void assertEquals(final double actual, final double expected, final double delta, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Double>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, delta, message);
            }
        });
    }

    public void assertEquals(final double actual, final double expected, final double delta) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Double>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, delta);
            }
        });
    }

    @Step("{3} : {0}")
    public void assertEquals(final float actual, final float expected, final float delta, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Float>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, delta, message);
            }
        });
    }

    public void assertEquals(final float actual, final float expected, final float delta) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Float>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, delta);
            }
        });
    }

    @Step("{2} : {0}")
    public void assertEquals(final long actual, final long expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Long>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }

    public void assertEquals(final long actual, final long expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Long>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertEquals(final boolean actual, final boolean expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Boolean>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }

    public void assertEquals(final boolean actual, final boolean expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Boolean>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertEquals(final byte actual, final byte expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Byte>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }

    public void assertEquals(final byte actual, final byte expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Byte>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertEquals(final char actual, final char expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Character>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }

    public void assertEquals(final char actual, final char expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Character>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertEquals(final short actual, final short expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Short>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }

    public void assertEquals(final short actual, final short expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Short>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertEquals(final int actual, final int expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Integer>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }

    public void assertEquals(final int actual, final int expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Integer>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }

    public void assertNotNull(final Object object) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(object, (Object)null) {
            public void doAssert() {
                Assert.assertNotNull(object);
            }
        });
    }

    public void assertNotNull(final Object object, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(object, (Object)null, message) {
            public void doAssert() {
                Assert.assertNotNull(object, message);
            }
        });
    }

    public void assertNull(final Object object) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(object, (Object)null) {
            public void doAssert() {
                Assert.assertNull(object);
            }
        });
    }

    public void assertNull(final Object object, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(object, (Object)null, message) {
            public void doAssert() {
                Assert.assertNull(object, message);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertSame(final Object actual, final Object expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(actual, expected, message) {
            public void doAssert() {
                Assert.assertSame(actual, expected, message);
            }
        });
    }

    public void assertSame(final Object actual, final Object expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(actual, expected) {
            public void doAssert() {
                Assert.assertSame(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertNotSame(final Object actual, final Object expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(actual, expected, message) {
            public void doAssert() {
                Assert.assertNotSame(actual, expected, message);
            }
        });
    }

    public void assertNotSame(final Object actual, final Object expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(actual, expected) {
            public void doAssert() {
                Assert.assertNotSame(actual, expected);
            }
        });
    }

    public void assertEquals(final Collection<?> actual, final Collection<?> expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Collection<?>>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertEquals(final Collection<?> actual, final Collection<?> expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Collection<?>>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertEquals(final Object[] actual, final Object[] expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object[]>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertEqualsNoOrder(final Object[] actual, final Object[] expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object[]>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEqualsNoOrder(actual, expected, message);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertEquals(final Object[] actual, final Object[] expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object[]>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }

    public void assertEqualsNoOrder(final Object[] actual, final Object[] expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object[]>(actual, expected) {
            public void doAssert() {
                Assert.assertEqualsNoOrder(actual, expected);
            }
        });
    }

    public void assertEquals(final byte[] actual, final byte[] expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<byte[]>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertEquals(final byte[] actual, final byte[] expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<byte[]>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }

    public void assertEquals(final Set<?> actual, final Set<?> expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Set<?>>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertEquals(final Set<?> actual, final Set<?> expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Set<?>>(actual, expected, message) {
            public void doAssert() {
                Assert.assertEquals(actual, expected, message);
            }
        });
    }

    public void assertEquals(final Map<?, ?> actual, final Map<?, ?> expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Map<?, ?>>(actual, expected) {
            public void doAssert() {
                Assert.assertEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertNotEquals(final Object actual, final Object expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(actual, expected, message) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, message);
            }
        });
    }

    public void assertNotEquals(final Object actual, final Object expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Object>(actual, expected) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertNotEquals(final String actual, final String expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<String>(actual, expected, message) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, message);
            }
        });
    }

    public void assertNotEquals(final String actual, final String expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<String>(actual, expected) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertNotEquals(final long actual, final long expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Long>(actual, expected, message) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, message);
            }
        });
    }

    public void assertNotEquals(final long actual, final long expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Long>(actual, expected) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertNotEquals(final boolean actual, final boolean expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Boolean>(actual, expected, message) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, message);
            }
        });
    }

    public void assertNotEquals(final boolean actual, final boolean expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Boolean>(actual, expected) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertNotEquals(final byte actual, final byte expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Byte>(actual, expected, message) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, message);
            }
        });
    }

    public void assertNotEquals(final byte actual, final byte expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Byte>(actual, expected) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected);
            }
        });
    }
    @Step("{2} : {0}")
    public void assertNotEquals(final char actual, final char expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Character>(actual, expected, message) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, message);
            }
        });
    }

    public void assertNotEquals(final char actual, final char expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Character>(actual, expected) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected);
            }
        });
    }

    @Step("{2} : {0}")
    public void assertNotEquals(final short actual, final short expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Short>(actual, expected, message) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, message);
            }
        });
    }

    public void assertNotEquals(final short actual, final short expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Short>(actual, expected) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected);
            }
        });
    }

    @Step("{2} : {0}")
    public void assertNotEquals(final int actual, final int expected, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Integer>(actual, expected, message) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, message);
            }
        });
    }

    public void assertNotEquals(final int actual, final int expected) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Integer>(actual, expected) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected);
            }
        });
    }

    @Step("{3} : {0}")
    public void assertNotEquals(final float actual, final float expected, final float delta, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Float>(actual, expected, message) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, delta, message);
            }
        });
    }

    public void assertNotEquals(final float actual, final float expected, final float delta) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Float>(actual, expected) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, delta);
            }
        });
    }

    public void assertNotEquals(final double actual, final double expected, final double delta, final String message) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Double>(actual, expected, message) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, delta, message);
            }
        });
    }

    public void assertNotEquals(final double actual, final double expected, final double delta) {
        this.doAssert(new com.cisco.services.api_automation.utils.customassert.Assertion.SimpleAssert<Double>(actual, expected) {
            public void doAssert() {
                Assert.assertNotEquals(actual, expected, delta);
            }
        });
    }

    protected String getErrorDetails(Throwable error) {
        StringBuilder sb = new StringBuilder();
        sb.append(error.getMessage());

        for(Throwable cause = error.getCause(); cause != null; cause = cause.getCause()) {
            sb.append(" ").append(cause.getMessage());
        }

        return sb.toString();
    }

    private abstract static class SimpleAssert<T> implements IAssert<T> {
        private final T actual;
        private final T expected;
        private final String m_message;

        public SimpleAssert(String message) {
            this((Object)null, (Object)null, message);
        }

        public SimpleAssert(T actual, T expected) {
            this(actual, expected, (String)null);
        }

        public SimpleAssert(Object actual, Object expected, String message) {
            this.actual = (T) actual;
            this.expected = (T) expected;
            this.m_message = message;
        }

        public String getMessage() {
            return this.m_message;
        }

        public T getActual() {
            return this.actual;
        }

        public T getExpected() {
            return this.expected;
        }

        public abstract void doAssert();
    }
}

