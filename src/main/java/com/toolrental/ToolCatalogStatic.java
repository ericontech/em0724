package com.toolrental;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a static tool catalog that stores tools and provides methods to retrieve them.
 */
public class ToolCatalogStatic implements ToolCatalog {

  private Map<String, Tool> tools = new HashMap<>();

  /**
   * Constructs a new ToolCatalogStatic object and initializes it with predefined tools.
   */
  ToolCatalogStatic() {
    this.tools.put("LADW", new Tool("LADW", ToolType.LADDER, Brand.WERNER));
    this.tools.put("CHNS", new Tool("CHNS", ToolType.CHAINSAW, Brand.STIHL));
    this.tools.put("JAKR", new Tool("JAKR", ToolType.JACKHAMMER, Brand.RIDGID));
    this.tools.put("JAKD", new Tool("JAKD", ToolType.JACKHAMMER, Brand.DEWALT));
  }

  /**
   * Retrieves the tool with the specified code from the tool catalog.
   *
   * @param code the code of the tool to retrieve
   * @return the tool with the specified code, or null if the tool is not found
   */
  public Tool getTool(String code) {
    return this.tools.get(code);
  }
}
