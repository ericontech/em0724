package com.toolrental;

import lombok.Getter;

/**
 * Represents a tool used for rental purposes.
 */
public class Tool {

  @Getter private String code;
  @Getter private ToolType toolType;
  @Getter private Brand brand;

  /**
   * Constructs a new Tool object with the specified code, tool type, and brand.
   *
   * @param code      the code of the tool
   * @param toolType  the type of the tool
   * @param brand     the brand of the tool
   */
  public Tool(String code, ToolType toolType, Brand brand) {
    this.code = code;
    this.toolType = toolType;
    this.brand = brand;
  }

}
