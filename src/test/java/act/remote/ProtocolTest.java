package act.remote;

import org.junit.Test;

import static act.remote.Protocol.*;

/**
 * Test cases for {@link act.remote.Protocol}
 */
public class ProtocolTest extends TestBase {
    @Test
    public void parseValidProtocolNameInCapitalOrLowerCaseLetters() {
        eq(FTP, lookup("ftp"));
        eq(HTTP, lookup("HttP"));
        eq(HTTPS, lookup("HTTPS"));
    }

    @Test
    public void parseValidProtocolNameWithEndingSpaces() {
        eq(WS, lookup(" ws "));
        eq(WSS, lookup(" wss"));
    }

    @Test(expected = UnknownProtocolException.class)
    public void parseUnknownProtocolName() {
        lookup("gopher");
    }
}
