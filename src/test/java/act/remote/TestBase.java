package act.remote;

import act.app.App;
import act.conf.AppConfig;
import junit.framework.Assert;
import org.osgl.$;
import org.osgl.util.S;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Base class for test cases
 */
class TestBase extends Assert {

    AppConfig mockAppConfig;
    App mockApp;

    protected final void same(Object a, Object b) {
        assertSame(a, b);
    }

    protected final void eq(Object[] a1, Object[] a2) {
        assertArrayEquals(a1, a2);
    }

    protected final void eq(Object o1, Object o2) {
        assertEquals(o1, o2);
    }

    protected final void ne(Object expected, Object actual) {
        no($.eq(expected, actual));
    }

    protected final void ceq(Object o1, Object o2) {
        assertEquals(S.string(o1), S.string(o2));
    }

    protected final void yes(Boolean expr, String msg, Object... args) {
        assertTrue(S.fmt(msg, args), expr);
    }

    protected final void yes(Boolean expr) {
        assertTrue(expr);
    }

    protected final void no(Boolean expr, String msg, Object... args) {
        assertFalse(S.fmt(msg, args), expr);
    }

    protected final void no(Boolean expr) {
        assertFalse(expr);
    }

    protected final void fail(String msg, Object... args) {
        assertFalse(S.fmt(msg, args), true);
    }

    protected final void initMocks() {
        mockApp = mock(App.class);
        mockAppConfig = mock(AppConfig.class);
        when(mockApp.config()).thenReturn(mockAppConfig);
    }

}
