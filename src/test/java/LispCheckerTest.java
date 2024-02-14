import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LispCheckerTest {

    @Test
    void isValid_ValidInput_ReturnsTrue() {
        LispChecker lispChecker = new LispChecker();
        assertTrue(lispChecker.isValid("()"));
        assertTrue(lispChecker.isValid("()[]{}"));
        assertTrue(lispChecker.isValid("{([])}"));
        assertTrue(lispChecker.isValid("(((())))"));
    }

    @Test
    void isValid_InvalidInput_ReturnsFalse() {
        LispChecker lispChecker = new LispChecker();
        assertFalse(lispChecker.isValid("("));
        assertFalse(lispChecker.isValid(")"));
        assertFalse(lispChecker.isValid("(]"));
        assertFalse(lispChecker.isValid("([)]"));
        assertFalse(lispChecker.isValid("({)}"));
    }

    @Test
    void isValid_EmptyInput_ReturnsFalse() {
        LispChecker lispChecker = new LispChecker();
        assertFalse(lispChecker.isValid(""));
    }

    @Test
    void isValid_NullInput_ReturnsFalse() {
        LispChecker lispChecker = new LispChecker();
        assertFalse(lispChecker.isValid(null));
    }
}