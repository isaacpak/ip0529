import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CheckoutTest {

    private List<Tool> tools;

    @Before
    public void setUp() {
        tools = Arrays.asList(
                new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false),
                new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true),
                new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, true, false),
                new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, true, false));

    }

    // Test 1
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDiscount() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 3);

        Checkout.createRentalAgreement("JAKR", 5, 101, calendar, tools);
    }

    /*
     * Ideally I would have had one calendar instance in the setup and then
     * use @ParameterizedTest along with
     * 
     * @CsvSource to input the tool code, rental days, and the date so it'd be
     * easier to add to the test for different scenarios. I ran into issues
     * upgrading to
     * JUnit 5.
     * 
     * For example:
     * 
     * @ParameterizedTest
     * 
     * @CsvSource({
     * "JAKR, 9, 0, 2015, 7, 2",
     * "JAKR, 4, 50, 2020, 7, 2",
     * })
     * public void testValidCheckoutScenarios(String toolCode, int rentaldays, int
     * discountPercent, int year, int month, int day){
     * calendar.set(Calendar.YEAR, year);
     * calendar.set(Calendar.MONTH, month);
     * calendar.set(Calendar.DAY_OF_MONTH, day);
     * 
     * Perform assertions here
     * 
     * }
     */

    // Test 2
    @Test
    public void testShortValidFourthOfJulyDiscount() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 2);

        RentalAgreement rentalAgreement = Checkout.createRentalAgreement("LADW", 3, 10, calendar, tools);

        assertEquals(3.58, rentalAgreement.finalCharge(), 0.00001);
    }

    // Test 3
    @Test
    public void testExtendedValidFourthOfJulyDiscountWithBiggerDiscount() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 2);

        RentalAgreement rentalAgreement = Checkout.createRentalAgreement("LADW", 5, 25, calendar, tools);

        assertEquals(5.97, rentalAgreement.finalCharge(), 0.00001);
    }

    // Test 4
    @Test
    public void testJackHammerLaborDay() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 3);

        RentalAgreement rentalAgreement = Checkout.createRentalAgreement("JAKD", 6, 0, calendar, tools);

        assertEquals(14.95, rentalAgreement.finalCharge(), 0.00001);
    }

    // Test 5
    @Test
    public void testJackHammerFourthOfJulyNoDiscount() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 2);

        RentalAgreement rentalAgreement = Checkout.createRentalAgreement("JAKR", 9, 0, calendar, tools);

        assertEquals(23.92, rentalAgreement.finalCharge(), 0.00001);

    }

    // Test 6
    @Test
    public void testJackHammerFourthOfJulyHalfOff() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 2);

        RentalAgreement rentalAgreement = Checkout.createRentalAgreement("JAKR", 4, 50, calendar, tools);

        assertEquals(4.48, rentalAgreement.finalCharge(), 0.00001);

    }
}
