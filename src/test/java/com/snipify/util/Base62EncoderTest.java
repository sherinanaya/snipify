package com.snipify.util;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class Base62EncoderTest {
 @Test void encodesKnownValues() { assertEquals("0", Base62Encoder.encode(0)); assertEquals("10", Base62Encoder.encode(62)); }
 @Test void generatesSevenCharacters() { assertEquals(7, ShortCodeGenerator.generate().length()); }
}