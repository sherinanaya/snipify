package com.snipify.util;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UrlValidatorTest {
 @Test void acceptsHttps() { assertTrue(UrlValidator.isValid("https://example.com/path")); }
 @Test void rejectsUnsafeSchemesAndCredentials() { assertFalse(UrlValidator.isValid("javascript:alert(1)")); assertFalse(UrlValidator.isValid("https://user:pass@example.com")); }
}