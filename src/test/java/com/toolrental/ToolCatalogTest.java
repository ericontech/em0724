package com.toolrental;

import org.junit.jupiter.api.Test;

public class ToolCatalogTest {
  @Test
  void testGetTool() {
    ToolCatalogStatic toolCatalog = new ToolCatalogStatic();
    Tool tool = toolCatalog.getTool("LADW");
    assert tool != null;
  }
}
