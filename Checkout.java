import java.util.Calendar;
import java.util.List;

public class Checkout {
    public static RentalAgreement createRentalAgreement(String toolCode, int days, int discount, Calendar date,
            List<Tool> tools) throws IllegalArgumentException {
        if (days < 1) {
            throw new IllegalArgumentException("Day(s) entered must be greater than 1 day.");
        }
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount needs to be between 0-100.");
        }

        // Find correct tool to check out
        Tool tool = tools.stream()
                .filter(x -> x.getToolCode().equals(toolCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tool code " + toolCode + " not found."));

        return new RentalAgreement(tool, days, discount, date);

    }
}
