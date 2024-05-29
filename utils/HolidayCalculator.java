package utils;

import java.util.Calendar;

public class HolidayCalculator {
    public static boolean isIndependenceDay(Calendar date) {
        // Convert date to potential Independence Day
        int year = date.get(Calendar.YEAR);
        Calendar independenceDay = Calendar.getInstance();
        independenceDay.set(year, Calendar.JULY, 4);

        if (independenceDay.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            independenceDay.add(Calendar.DAY_OF_YEAR, -1);
        } else if (independenceDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            independenceDay.add(Calendar.DAY_OF_YEAR, 1);
        }

        return date.get(Calendar.MONTH) == independenceDay.get(Calendar.MONTH) &&
                date.get(Calendar.DAY_OF_MONTH) == independenceDay.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isLaborDay(Calendar date) {
        // Convert to potential Labor Day and compare
        int year = date.get(Calendar.YEAR);
        Calendar laborDay = Calendar.getInstance();
        laborDay.set(year, Calendar.SEPTEMBER, 1);

        while (laborDay.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            laborDay.add(Calendar.DAY_OF_YEAR, 1);
        }

        return date.get(Calendar.MONTH) == laborDay.get(Calendar.MONTH) &&
                date.get(Calendar.DAY_OF_MONTH) == laborDay.get(Calendar.DAY_OF_MONTH);
    }

    // Could add other holiday logic here in the future
}
