package com.toolrental;

import lombok.Getter;

/**
 * Represents the type of tool available for rental.
 */
public enum ToolType {

  LADDER("Ladder", 1.99, true, true, false),
  CHAINSAW("Chainsaw", 1.49, true, false, true),
  JACKHAMMER("Jackhammer", 2.99, true, false, false);

  @Getter private String name;
  @Getter private Double dailyCharge;
  @Getter private boolean weekdayCharge;
  @Getter private boolean weekendCharge;
  @Getter private boolean holidayCharge;

  /**
   * Constructs a ToolType object with the specified properties.
   *
   * @param name The name of the tool.
   * @param dailyCharge The daily rental charge for the tool.
   * @param weekdayCharge Indicates if the tool has a rental charge on weekdays.
   * @param weekendCharge Indicates if the tool has a rental charge on weekends.
   * @param holidayCharge Indicates if the tool has a rental charge on holidays.
   */
  ToolType(String name, Double dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
    this.name = name;
    this.dailyCharge = dailyCharge;
    this.weekdayCharge = weekdayCharge;
    this.weekendCharge = weekendCharge;
    this.holidayCharge = holidayCharge;
  }

}
