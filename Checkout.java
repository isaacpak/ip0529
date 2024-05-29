import java.util.Calendar;

public class Checkout {
    public static RentalAgreement createRentalAgreement(int days, int discount, Calendar date,
            Tool tool) throws IllegalArgumentException {
        if (days < 1) {
            throw new IllegalArgumentException("Day(s) entered must be greater than 1 day.");
        }
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount needs to be between 0-100.");
        }
        return new RentalAgreement(tool, days, discount, date);
    }
}
