package dal;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kuba
 * @date 4/9/2021 11:46 AM
 */
class AbsenceDataTest {

    @org.junit.jupiter.api.Test
    void isStudentPresent() {

    }

    @org.junit.jupiter.api.Test
    void isDataStudentPresent() {
        AbsenceData absenceData = new AbsenceData();
        boolean result = absenceData.isStudentPresent(1);
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult, result);
    }
}