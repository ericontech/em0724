package com.toolrental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ToolRentalTest {

  private static final String DATE_FORMAT = "MM/dd/yy";
  private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

  private static ToolRental toolRental;

  @BeforeAll
  static void setUp() {
    toolRental = new ToolRental();
  }

  @Test
  void testCheckoutToolExceptions() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      toolRental.checkoutTool("XXXX", 0, 0, "9/3/15");
    });
    assertEquals("Tool code not found", thrown.getMessage());

    thrown = assertThrows(IllegalArgumentException.class, () -> {
      toolRental.checkoutTool("LADW", 0, 0, "9/3/15");
    });
    assertEquals("Rental days must be greater than 0", thrown.getMessage());

    thrown = assertThrows(IllegalArgumentException.class, () -> {
      toolRental.checkoutTool("LADW", 10, 101, "9/3/15");
    });
    assertEquals("Discount percent must be between 0 and 100", thrown.getMessage());
  }

  @Test
  void testCheckoutToolWeekendNoCharge() {
    RentalAgreement rentalAgreement = toolRental.checkoutTool("JAKR", 5, 10, "7/18/24");
    assertEquals("JAKR", rentalAgreement.getTool().getCode());
    assertEquals(5, rentalAgreement.getRentalDays());
    assertEquals(10, rentalAgreement.getDiscountPercent());
    assertEquals("07/18/24", sdf.format(rentalAgreement.getCheckoutDate()));
    assertEquals("07/22/24", sdf.format(rentalAgreement.getDueDate()));
    assertEquals(2.99d, rentalAgreement.getDailyRentalCharge());
    assertEquals(3, rentalAgreement.getChargeDays());
    assertEquals(8.97d, rentalAgreement.getPreDiscountCharge());
    assertEquals(0.90d, rentalAgreement.getDiscountAmount());
    assertEquals(8.07d, rentalAgreement.getFinalCharge());
  }

  @Test
  void testCheckoutToolWeekendCharge() {
    RentalAgreement rentalAgreement = toolRental.checkoutTool("LADW", 5, 10, "7/18/24");
    assertEquals("LADW", rentalAgreement.getTool().getCode());
    assertEquals(5, rentalAgreement.getRentalDays());
    assertEquals(10, rentalAgreement.getDiscountPercent());
    assertEquals("07/18/24", sdf.format(rentalAgreement.getCheckoutDate()));
    assertEquals("07/22/24", sdf.format(rentalAgreement.getDueDate()));
    assertEquals(1.99d, rentalAgreement.getDailyRentalCharge());
    assertEquals(5, rentalAgreement.getChargeDays());
    assertEquals(9.95d, rentalAgreement.getPreDiscountCharge());
    assertEquals(0.99d, rentalAgreement.getDiscountAmount());
    assertEquals(8.96d, rentalAgreement.getFinalCharge());
  }

  @Test
  void testCheckoutToolJuly4thNoCharge() {
    RentalAgreement rentalAgreement = toolRental.checkoutTool("LADW", 3, 10, "7/3/24");
    assertEquals("LADW", rentalAgreement.getTool().getCode());
    assertEquals(3, rentalAgreement.getRentalDays());
    assertEquals(10, rentalAgreement.getDiscountPercent());
    assertEquals("07/03/24", sdf.format(rentalAgreement.getCheckoutDate()));
    assertEquals("07/05/24", sdf.format(rentalAgreement.getDueDate()));
    assertEquals(1.99d, rentalAgreement.getDailyRentalCharge());
    assertEquals(2, rentalAgreement.getChargeDays());
    assertEquals(3.98d, rentalAgreement.getPreDiscountCharge());
    assertEquals(0.40d, rentalAgreement.getDiscountAmount());
    assertEquals(3.58d, rentalAgreement.getFinalCharge());
  }

  @Test
  void testCheckoutToolJuly4thOnWeekendNoCharge() {
    RentalAgreement rentalAgreement = toolRental.checkoutTool("LADW", 3, 10, "7/3/26");
    assertEquals(2, rentalAgreement.getChargeDays());
  }

  @Test
  void testCheckoutToolJuly4thOnWeekendCharge() {
    RentalAgreement rentalAgreement = toolRental.checkoutTool("CHNS", 3, 10, "7/1/26");
    assertEquals(3, rentalAgreement.getChargeDays());
  }
}
