package util;

import models.basic.Shop;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GeneralUtilTest {

    private static final String VALID_TEST_ID = "#13#";
    private static final String INVALID_TEST_ID_1 = "##";
    private static final String INVALID_TEST_ID_2 = "13";
    private static final int ID = 13;
    private static final int INVALID_RESULT = -1;


    private static final String VALID_DATE_1 = "11.12.2023";
    private static final String VALID_DATE_2 = "01.01.1970";
    private static final String INVALID_DATE_1 = "0.0.1969";
    private static final String INVALID_DATE_2 = "11.13.2024";
    private static final String INVALID_DATE_3 = "11-12-2024";


    @Test
    public void testReturnId() {
        assertEquals(ID, GeneralUtil.returnId(VALID_TEST_ID));
    }

    @Test
    public void testReturnIdShouldReturnNegativeOnInvalidId1() {
        assertEquals(INVALID_RESULT, GeneralUtil.returnId(INVALID_TEST_ID_1));
    }

    @Test
    public void testReturnIdShouldReturnNegativeOnInvalidId2() {
        assertEquals(INVALID_RESULT, GeneralUtil.returnId(INVALID_TEST_ID_2));
    }

    @Test
    public void testReturnIdShouldReturnNegativeOnNull() {
        assertEquals(INVALID_RESULT, GeneralUtil.returnId(null));
    }

    @Test
    public void testReturnIdShouldReturnNegativeOnEmpty() {
        assertEquals(INVALID_RESULT, GeneralUtil.returnId(""));
    }

    @Test
    public void testValidateDate1() {
        assertTrue(GeneralUtil.validateDate(VALID_DATE_1));
    }

    @Test
    public void testValidateDate2() {
        assertTrue(GeneralUtil.validateDate(VALID_DATE_2));
    }

    @Test
    public void testValidateDateShouldReturnFalseOnInvalid1() {
        assertFalse(GeneralUtil.validateDate(INVALID_DATE_1));
    }

    @Test
    public void testValidateDateShouldReturnFalseOnInvalid2() {
        assertFalse(GeneralUtil.validateDate(INVALID_DATE_2));
    }

    @Test
    public void testValidateDateShouldReturnFalseOnInvalid3() {
        assertFalse(GeneralUtil.validateDate(INVALID_DATE_3));
    }

    @Test
    public void testValidateDateShouldReturnFalseOnNull() {
        assertFalse(GeneralUtil.validateDate(null));
    }

    @Test
    public void testValidateDateShouldReturnFalseOnEmpty() {
        assertFalse(GeneralUtil.validateDate(""));
    }
}
