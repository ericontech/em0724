package com.toolrental;

import java.util.Calendar;
import java.util.Date;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

/**
 * The ToolRental class represents a tool rental system.
 */
public class ToolRental {

  private static final String DATE_FORMAT = "MM/dd/yy";
  private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

  private ToolCatalogStatic toolCatalog = new ToolCatalogStatic();

  /**
   * Checks out a tool for rental.
   *
   * @param code            the code of the tool to be rented
   * @param rentalDays      the number of rental days
   * @param discountPct     the discount percentage
   * @param checkoutDateStr the checkout date in string format (MM/dd/yy)
   * @return a RentalAgreement object representing the rental agreement
   * @throws IllegalArgumentException if the tool code is not found, rental days is less than 1,
   *                                  or discount percentage is not between 0 and 100
   */
  public RentalAgreement checkoutTool(String code, int rentalDays, int discountPct, String checkoutDateStr) {

    Tool tool = toolCatalog.getTool(code);
    if (tool == null) {
      throw new IllegalArgumentException("Tool code not found");
    }
    if (rentalDays < 1) {
      throw new IllegalArgumentException("Rental days must be greater than 0");
    }
    if (discountPct < 0 || discountPct > 100) {
      throw new IllegalArgumentException("Discount percent must be between 0 and 100");
    }
    Date checkoutDate = null;
    try {
      checkoutDate = sdf.parse(checkoutDateStr);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid date format. Expected format is " + DATE_FORMAT);
    }

    return new RentalAgreement(tool, rentalDays, discountPct, checkoutDate);
  }

  /**
   * Calculates the number of charge days based on the checkout date, due date, and tool type.
   *
   * @param checkoutDate the checkout date
   * @param dueDate      the due date
   * @param toolType     the type of the tool
   * @return the number of charge days
   */
  public static int calculateChargeDays(Date checkoutDate, Date dueDate, ToolType toolType) {
    int chargeDays = 0;
    int[] weekdayCnts = countWeekdays(checkoutDate, dueDate);
    if (toolType.isWeekdayCharge()) {
      chargeDays += weekdayCnts[0];
    }
    if (toolType.isWeekendCharge()) {
      chargeDays += weekdayCnts[1];
    }
    if (toolType.isHolidayCharge()) {
      if (isLaborDayWithinRange(checkoutDate, dueDate)) {
        chargeDays += 1;
      }
      if (isJuly4thWithinRange(checkoutDate, dueDate)) {
        chargeDays += 1;
      }
    } else {
      if (isLaborDayWithinRange(checkoutDate, dueDate)) {
        chargeDays -= 1;
      }
      if (isJuly4thWithinRange(checkoutDate, dueDate)) {
        chargeDays -= 1;
      }
    }
    return chargeDays;
  }

  /**
   * Calculates the due date based on the checkout date and rental days.
   *
   * @param checkoutDate the checkout date
   * @param rentalDays   the number of rental days
   * @return the due date
   */
  public static Date calculateDueDate(Date checkoutDate, int rentalDays) {
    return new Date(checkoutDate.getTime() + ((rentalDays - 1) * 24 * 60 * 60 * 1000));
  }

  private static int[] countWeekdays(Date startDate, Date endDate) {
    Calendar start = Calendar.getInstance();
    start.setTime(startDate);

    Calendar end = Calendar.getInstance();
    end.setTime(endDate);

    int[] weekdayCnts = { 0, 0 };

    while (!start.after(end)) {
      int dayOfWeek = start.get(Calendar.DAY_OF_WEEK);
      if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
        weekdayCnts[0]++;
      } else {
        weekdayCnts[1]++;
      }
      start.add(Calendar.DAY_OF_MONTH, 1);
    }

    return weekdayCnts;
  }

  private static boolean isLaborDayWithinRange(Date startDate, Date endDate) {
    Calendar start = Calendar.getInstance();
    start.setTime(startDate);

    Calendar end = Calendar.getInstance();
    end.setTime(endDate);

    // Calculate Labor Day for the start year and end year
    Date laborDayStartYear = calculateLaborDay(start.get(Calendar.YEAR));
    Date laborDayEndYear = calculateLaborDay(end.get(Calendar.YEAR));

    // Check if Labor Day is within the range
    return (!laborDayStartYear.before(startDate) && !laborDayStartYear.after(endDate)) ||
        (!laborDayEndYear.before(startDate) && !laborDayEndYear.after(endDate));
  }

  private static Date calculateLaborDay(int year) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1); // First Monday of September

    return calendar.getTime();
  }

  private static boolean isJuly4thWithinRange(Date startDate, Date endDate) {
    Calendar start = Calendar.getInstance();
    start.setTime(startDate);

    Calendar end = Calendar.getInstance();
    end.setTime(endDate);

    // Calculate Labor Day for the start year and end year
    Date july4thStartYear = calculateJuly4thObservation(start.get(Calendar.YEAR));
    Date july4thEndYear = calculateJuly4thObservation(end.get(Calendar.YEAR));

    // Check if Labor Day is within the range
    return (!july4thStartYear.before(startDate) && !july4thStartYear.after(endDate)) ||
        (!july4thEndYear.before(startDate) && !july4thEndYear.after(endDate));
  }

  private static Date calculateJuly4thObservation(int year) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, Calendar.JULY);
    calendar.set(Calendar.DAY_OF_MONTH, 4);

    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

    // If July 4 is Saturday, observe on Friday
    if (dayOfWeek == Calendar.SATURDAY) {
      calendar.add(Calendar.DAY_OF_MONTH, -1);
    }
    // If July 4 is Sunday, observe on Monday
    else if (dayOfWeek == Calendar.SUNDAY) {
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    }

    return calendar.getTime();
  }

  /**
   * Calculates the pre-discount charge based on the daily rental charge and number of charge days.
   *
   * @param dailyRentalCharge the daily rental charge
   * @param chargeDays        the number of charge days
   * @return the pre-discount charge
   */
  public static Double calculatePreDiscountCharge(Double dailyRentalCharge, int chargeDays) {
    BigDecimal preDiscountCharge = new BigDecimal(dailyRentalCharge * chargeDays);
    preDiscountCharge = preDiscountCharge.setScale(2, RoundingMode.HALF_UP);
    return preDiscountCharge.doubleValue();
  }

  /**
   * Calculates the discount amount based on the pre-discount charge and discount percentage.
   *
   * @param preDiscountCharge the pre-discount charge
   * @param discountPercent   the discount percentage
   * @return the discount amount
   */
  public static Double calculateDiscountAmount(Double preDiscountCharge, int discountPercent) {
    BigDecimal discountAmount = new BigDecimal(preDiscountCharge * discountPercent / 100);
    discountAmount = discountAmount.setScale(2, RoundingMode.HALF_UP);
    return discountAmount.doubleValue();
  }

  /**
   * Calculates the final charge based on the pre-discount charge and discount amount.
   *
   * @param preDiscountCharge the pre-discount charge
   * @param discountAmount    the discount amount
   * @return the final charge
   */
  public static Double calculateFinalCharge(Double preDiscountCharge, Double discountAmount) {
    BigDecimal finalCharge = new BigDecimal(preDiscountCharge - discountAmount);
    finalCharge = finalCharge.setScale(2, RoundingMode.HALF_UP);
    return finalCharge.doubleValue();
  }
}
