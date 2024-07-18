package com.toolrental;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RentalAgreementTest {

  private static ToolRental toolRental;

  @BeforeAll
  static void setUp() {
    toolRental = new ToolRental();
  }

  @Test
  void testToString() {
    RentalAgreement rentalAgreement = toolRental.checkoutTool("JAKR", 5, 10, "7/18/24");
    String rentalDoc = rentalAgreement.toString();
    assert rentalDoc != null;
    assertTrue(rentalDoc.contains("Tool code: JAKR"));
    assertTrue(rentalDoc.contains("Tool type: Jackhammer"));
    assertTrue(rentalDoc.contains("Tool brand: Ridgid"));
    assertTrue(rentalDoc.contains("Rental days: 5"));
    assertTrue(rentalDoc.contains("Checkout date: 07/18/24"));
    assertTrue(rentalDoc.contains("Due date: 07/22/24"));
    assertTrue(rentalDoc.contains("Daily rental charge: $2.99"));
    assertTrue(rentalDoc.contains("Charge days: 3"));
    assertTrue(rentalDoc.contains("Pre-discount charge: $8.97"));
    assertTrue(rentalDoc.contains("Discount percent: 10%"));
    assertTrue(rentalDoc.contains("Discount amount: $0.90"));
    assertTrue(rentalDoc.contains("Final charge: $8.07"));
  }
}
