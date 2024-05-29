import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import utils.HolidayCalculator;

public class RentalAgreement {
    private final Tool tool;

    private final String toolCode;
    private final String toolType;
    private final int rentalDays;
    private final int discount;
    private final Calendar checkoutDate;
    private final Calendar dueDate;
    private final double dailyRentalCharge;
    private final int chargeDays;
    private final double preDiscountCharge;
    private final double discountAmount;
    private final double finalCharge;

    RentalAgreement(Tool tool, int rentalDays, int discount, Calendar checkoutDate) {
        this.tool = tool;
        this.toolCode = tool.getToolCode();
        this.toolType = tool.getToolType();
        this.rentalDays = rentalDays;
        this.discount = discount;
        this.checkoutDate = checkoutDate;
        this.dueDate = calculateDueDate(checkoutDate, rentalDays);
        this.dailyRentalCharge = tool.getDailyRentalFee();
        this.chargeDays = calculateChargeDays(checkoutDate, rentalDays);
        this.preDiscountCharge = calculatePreDiscountCharge();
        this.discountAmount = calculateDiscountAmount();
        this.finalCharge = calculateFinalCharge();
    }

    private double calculateFinalCharge() {
        return preDiscountCharge - discountAmount;
    }

    private double calculateDiscountAmount() {
        double convertedDiscount = discount / 100.0;
        BigDecimal bigDecimal = new BigDecimal(convertedDiscount);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

        double currentTotal = preDiscountCharge * convertedDiscount;

        BigDecimal finalDiscountAmount = new BigDecimal(currentTotal);
        finalDiscountAmount = finalDiscountAmount.setScale(2, RoundingMode.HALF_UP);

        return finalDiscountAmount.doubleValue();
    }

    private double calculatePreDiscountCharge() {
        double currentValue = chargeDays * dailyRentalCharge;
        BigDecimal bigDecimal = new BigDecimal(currentValue);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    private int calculateChargeDays(Calendar checkoutDate, int rentalDays) {
        int chargeDays = 0;
        Calendar calendar = (Calendar) checkoutDate.clone();

        for (int i = 0; i < rentalDays; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            boolean isWeekend = (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
            boolean isHoliday = isHoliday(calendar);

            if ((tool.isWeekdayCharge() && !isWeekend && !isHoliday) ||
                    (tool.isWeekendCharge() && isWeekend) ||
                    (tool.isHolidayCharge() && isHoliday)) {
                chargeDays++;
            }
        }

        return chargeDays;
    }

    private boolean isHoliday(Calendar checkoutDate) {
        return HolidayCalculator.isIndependenceDay(checkoutDate) || HolidayCalculator.isLaborDay(checkoutDate);
    }

    private Calendar calculateDueDate(Calendar checkoutDate, int rentalDays) {
        Calendar dueDate = (Calendar) checkoutDate.clone();
        dueDate.add(Calendar.DAY_OF_YEAR, rentalDays);
        return dueDate;
    }

    public Tool getTool() {
        return tool;
    }

    public double finalCharge() {
        return finalCharge;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return "======= Rental Agreement =======\n" +
                "Tool Code: " + toolCode + "\n" +
                "Tool Type: " + toolType + "\n" +
                "Tool Brand: " + tool.getBrand() + "\n" +
                "Rental Days: " + rentalDays + "\n" +
                "Checkout Date: " + sdf.format(checkoutDate.getTime()) + "\n" +
                "Due Date: " + sdf.format(dueDate.getTime()) + "\n" +
                "Daily Rental Charge: $" + String.format("%.2f", dailyRentalCharge) + "\n" +
                "Charge Days: " + chargeDays + "\n" +
                "Pre-discount Charge: $" + String.format("%.2f", preDiscountCharge) + "\n" +
                "Discount Percent: " + discount + "%\n" +
                "Discount Amount: $" + String.format("%.2f", discountAmount) + "\n" +
                "Final Charge: $" + String.format("%.2f", finalCharge) + "\n" +
                "===== End of Rental Agreement =====" + "\n";
    }
}
