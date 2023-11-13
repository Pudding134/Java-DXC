import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EncoderDecoderTest {

    EncoderDecoder encoderDecoder = new EncoderDecoder();

    @Test
    void testEncodeStandardCharacters() {
        assertEquals("BUFTUJOH", encoderDecoder.encode("TESTING", 'B'));
    }

    @Test
    void testEncodeSpecialCharacters() {
        assertEquals("B!@#$%", encoderDecoder.encode("!@#$%", 'B'));
    }

    @Test
    void testDecode() {
        assertEquals("TESTING", encoderDecoder.decode("BUFTUJOH"));
    }

    @Test
    void testCaseSensitivity() {
        assertEquals("BUftuJOH", encoderDecoder.encode("TestING", 'B'));
        assertEquals("TestING", encoderDecoder.decode("BUftuJOH"));
    }

    @Test
    void testDifferentOffsets() {
        assertEquals("CVguvKPI", encoderDecoder.encode("TestING", 'C'));
    }

    @Test
    void testEmptyString() {
        assertEquals("B", encoderDecoder.encode("", 'B'));
        assertEquals("", encoderDecoder.decode("B"));
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> encoderDecoder.encode(null, 'B'));
        assertThrows(IllegalArgumentException.class, () -> encoderDecoder.decode(null));
    }

    @Test
    void testInvalidOffset() {
        assertThrows(IllegalArgumentException.class, () -> encoderDecoder.encode("TESTING", '|'));
    }
}
