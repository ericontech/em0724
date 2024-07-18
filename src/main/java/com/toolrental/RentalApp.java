package com.toolrental;

/**
 * The RentalApp class is the entry point for the tool rental application.
 * It accepts command line arguments and uses them to checkout a tool and generate a rental agreement.
 */
public class RentalApp {

  /**
   * The main method is the entry point of the application.
   * It accepts command line arguments and uses them to checkout a tool and generate a rental agreement.
   *
   * @param args The command line arguments. Expected arguments: <toolCode> <rentalDays> <discountPercent> <checkoutDate>
   */
  public static void main(String[] args) {
    if (args.length != 4) {
      System.out.println("Usage: java -jar tool-rental-1.0-SNAPSHOT.jar <toolCode> <rentalDays> <discountPercent> <checkoutDate>");
      System.out.println(args.length);
      System.exit(1);
    }
    try {
      String toolCode = args[0];
      int rentalDays = Integer.parseInt(args[1]);
      int discountPercent = Integer.parseInt(args[2]);
      ToolRental toolRental = new ToolRental();
      RentalAgreement rentalAgreement = toolRental.checkoutTool(toolCode, rentalDays, discountPercent, "9/3/15");
      System.out.println(rentalAgreement.toString());
    } catch (NumberFormatException e) {
      System.out.println("Invalid input. Please enter valid numbers for rental days and discount percent.");
      System.exit(1);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }
}
