import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

public class CheckoutTest {

    // Test 1
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDiscount() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 3);

        Checkout.createRentalAgreement(5, 101, calendar,
                new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, true, false));
    }

    /*
     * Ideally I would have had one calendar instance in the
     * setup
     * and then
     * use @ParameterizedTest along with
     * 
     * @CsvSource to input the tool code, rental days, and the date so it'd be
     * easier to add to the test for different scenarios. Also would have inputted
     * the tool arguments. I ran into issues
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

        RentalAgreement rentalAgreement = Checkout.createRentalAgreement(3, 10, calendar,
                new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false));

        assertEquals(3.58, rentalAgreement.finalCharge(), 0.00001);
        assertEquals("07/05/2020", rentalAgreement.dueDate());
    }

    // Test 3
    @Test
    public void testExtendedValidFourthOfJulyDiscountWithBiggerDiscount() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 2);

        RentalAgreement rentalAgreement = Checkout.createRentalAgreement(5, 25, calendar,
                new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true));

        assertEquals(3.35, rentalAgreement.finalCharge(), 0.00001);
        assertEquals("07/07/2015", rentalAgreement.dueDate());
    }

    // Test 4
    @Test
    public void testJackHammerLaborDay() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 3);

        RentalAgreement rentalAgreement = Checkout.createRentalAgreement(6, 0, calendar,
                new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, true, false));

        assertEquals(14.95, rentalAgreement.finalCharge(), 0.00001);
        assertEquals("09/09/2015", rentalAgreement.dueDate());
    }

    // Test 5
    @Test
    public void testJackHammerFourthOfJulyNoDiscount() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 2);

        RentalAgreement rentalAgreement = Checkout.createRentalAgreement(9, 0, calendar,
                new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, true, false));

        assertEquals(23.92, rentalAgreement.finalCharge(), 0.00001);
        assertEquals("07/11/2015", rentalAgreement.dueDate());

    }

    // Test 6
    @Test
    public void testJackHammerFourthOfJulyHalfOff() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 2);

        RentalAgreement rentalAgreement = Checkout.createRentalAgreement(4, 50, calendar,
                new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, true, false));

        assertEquals(4.48, rentalAgreement.finalCharge(), 0.00001);
        assertEquals("07/06/2020", rentalAgreement.dueDate());
    }
}
