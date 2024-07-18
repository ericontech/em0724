package com.toolrental;

/**
 * The ToolCatalog interface represents a catalog of tools.
 * It provides a method to retrieve a tool based on its code.
 */
public interface ToolCatalog {

  /**
   * Retrieves a tool from the catalog based on its code.
   *
   * @param code the code of the tool to retrieve
   * @return the tool with the specified code, or null if not found
   */
  Tool getTool(String code);
}
