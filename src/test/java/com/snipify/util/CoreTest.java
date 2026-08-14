package com.snipify.util;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
class CoreTest{@Test void base62(){assertEquals("10",Base62Encoder.encode(62));assertEquals(7,ShortCodeGenerator.generate().length());}@Test void urls(){assertTrue(UrlValidator.isValid("https://example.com/a"));assertFalse(UrlValidator.isValid("javascript:alert(1)"));}}
