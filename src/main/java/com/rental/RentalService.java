package com.rental;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RentalService {
    private Map<String, Tool> tools = new HashMap<>();

    public RentalService() {
        tools.put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true));
        tools.put("LADW", new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false));
        tools.put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false));
        tools.put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false));
    }

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, Date checkoutDate) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }

        Tool tool = tools.get(toolCode);
        if (tool == null) {
            throw new IllegalArgumentException("Invalid tool code.");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkoutDate);
        calendar.add(Calendar.DAY_OF_YEAR, rentalDays);
        Date dueDate = calendar.getTime();

        int chargeDays = calculateChargeDays(tool, checkoutDate, dueDate);
        double preDiscountCharge = chargeDays * tool.getDailyCharge();
        double discountAmount = preDiscountCharge * discountPercent / 100;
        double finalCharge = preDiscountCharge - discountAmount;

        RentalAgreement agreement = new RentalAgreement(tool, rentalDays, checkoutDate, dueDate, chargeDays,
                preDiscountCharge, discountPercent, discountAmount, finalCharge);

        return agreement;
    }

    private int calculateChargeDays(Tool tool, Date checkoutDate, Date dueDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkoutDate);
        int chargeDays = 1;

        while (calendar.getTime().before(dueDate)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            boolean isWeekend = (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
            boolean isHoliday = isHoliday(calendar);

            if (tool.isWeekdayCharge() && !isWeekend && !isHoliday) {
                chargeDays++;
            } else if (tool.isWeekendCharge() && isWeekend) {
                chargeDays++;
            } else if (tool.isHolidayCharge() && isHoliday) {
                chargeDays++;
            }
        }
        return chargeDays;
    }

    private boolean isHoliday(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Independence Day
        if (month == Calendar.JULY && (day == 3 && dayOfWeek == Calendar.FRIDAY) || (day == 5 && dayOfWeek == Calendar.MONDAY)) {
            return true;
        }

        // Labor Day (first Monday of September)
        if (month == Calendar.SEPTEMBER && dayOfWeek == Calendar.MONDAY && day <= 7) {
            return true;
        }

        return false;
    }
}
