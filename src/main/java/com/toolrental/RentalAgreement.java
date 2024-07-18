package com.toolrental;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import lombok.Getter;

/**
 * Represents a rental agreement for a tool.
 */
public class RentalAgreement {

  @Getter private Tool tool;
  @Getter private int rentalDays;
  @Getter private Date checkoutDate;
  @Getter private Date dueDate;
  @Getter private Double dailyRentalCharge;
  @Getter private int chargeDays;
  @Getter private Double preDiscountCharge;
  @Getter private int discountPercent;
  @Getter private Double discountAmount;
  @Getter private Double finalCharge;

  private static final String DATE_FORMAT = "MM/dd/yy";
  private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

  private static final NumberFormat currencyFormatter = 
        NumberFormat.getCurrencyInstance(Locale.US);

  /**
   * Constructs a RentalAgreement object with the specified tool, rental days, discount percentage, and checkout date.
   * @param tool the tool being rented
   * @param days the number of rental days
   * @param discountPct the discount percentage
   * @param checkoutDate the checkout date
   */
  public RentalAgreement(Tool tool, int days, int discountPct, Date checkoutDate) {
    this.tool = tool;
    this.rentalDays = days;
    this.discountPercent = discountPct;
    this.checkoutDate = checkoutDate;
    calculateFields();
  }

  private void calculateFields() {
    this.dueDate = ToolRental.calculateDueDate(this.checkoutDate, this.rentalDays);
    this.dailyRentalCharge = this.tool.getToolType().getDailyCharge();
    this.chargeDays = ToolRental.calculateChargeDays(this.checkoutDate, this.dueDate, this.tool.getToolType());
    this.preDiscountCharge = ToolRental.calculatePreDiscountCharge(this.dailyRentalCharge, this.chargeDays);
    this.discountAmount = ToolRental.calculateDiscountAmount(this.preDiscountCharge, this.discountPercent);
    this.finalCharge = ToolRental.calculateFinalCharge(this.preDiscountCharge, this.discountAmount);
  }

  /**
   * Returns a string representation of the RentalAgreement object.
   * @return a string representation of the RentalAgreement object
   */
  @Override
  public String toString() {
    String newline = System.getProperty("line.separator");
    return "Rental Agreement:"
            + newline + "Tool code: " + tool.getCode()
            + newline + "Tool type: " + tool.getToolType().getName()
            + newline + "Tool brand: " + tool.getBrand().getName()
            + newline + "Rental days: " + this.rentalDays
            + newline + "Checkout date: " + sdf.format(checkoutDate)
            + newline + "Due date: " + sdf.format(dueDate)
            + newline + "Daily rental charge: " + currencyFormatter.format(dailyRentalCharge)
            + newline + "Charge days: " + chargeDays
            + newline + "Pre-discount charge: " + currencyFormatter.format(preDiscountCharge)
            + newline + "Discount percent: " + discountPercent + "%"
            + newline + "Discount amount: " + currencyFormatter.format(discountAmount)
            + newline + "Final charge: " + currencyFormatter.format(finalCharge);
  }

  // Getters and setters omitted for brevity
}
