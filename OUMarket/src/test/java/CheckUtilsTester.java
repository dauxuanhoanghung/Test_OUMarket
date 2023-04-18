/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.tester.utils.CheckUtils;
import java.time.LocalDate;
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
    
    @ParameterizedTest
    @CsvFileSource(resources = {"/data/Hung/passwordTest.csv"}, numLinesToSkip = 1)
    public void isValidPassword(int expectedOutput, String password) {
        Assertions.assertEquals(expectedOutput, CheckUtils.isValidPassword(password));
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = {"/data/Hung/age18Test.csv"}, numLinesToSkip = 1)
    public void isEnough18Test(int expectedOutput, String birthday) {
        Assertions.assertEquals(expectedOutput, CheckUtils.isAgeEnough18(birthday));
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = {"/data/Hung/pastDateTest.csv"}, numLinesToSkip = 1)
    public void isNotPastDateTest(int expectedOutput, String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        Assertions.assertEquals(expectedOutput, CheckUtils.isNotPastDate(date));
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = {"/data/Hung/priceTest.csv"}, numLinesToSkip = 1)
    public void isValidPriceTest(int expectedOutput, String price) {
        Assertions.assertEquals(expectedOutput, CheckUtils.isValidPrice(price));
    }
}
