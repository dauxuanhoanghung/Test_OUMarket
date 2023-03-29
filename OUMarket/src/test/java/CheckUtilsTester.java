/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.tester.utils.CheckUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 *
 * @author LENOVO
 */
public class CheckUtilsTester {

    @ParameterizedTest
    @CsvFileSource(resources = {"/data/Hung/phoneTest.csv"}, numLinesToSkip = 1)
    public void isValidPhoneNumberTest(int expectedOutput, String phoneNumber) {
        Assertions.assertEquals(expectedOutput, CheckUtils.isValidPhoneNumber(phoneNumber));
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/data/Hung/nameTest.csv"}, numLinesToSkip = 1)
    public void isValidNameTest(int expectedOutput, String name) {
        Assertions.assertEquals(expectedOutput, CheckUtils.isValidName(name));
    }
}
