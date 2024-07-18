package com.toolrental;

// Declare an enum named Brand, used to represent a fixed set of constants (brands)
public enum Brand {

  // Enum constants, each representing a specific brand with a string value
  WERNER("Werner"),
  RIDGID("Ridgid"),
  DEWALT("DeWalt"),
  STIHL("Stihl");

  // Private field to hold the brand name for each enum constant
  private String name;

  // Constructor for the enum, allowing each constant to initialize its name
  Brand(String name) {
    this.name = name;
  }

  // Public method to get the name of the brand
  public String getName() {
    return name;
  }
}
