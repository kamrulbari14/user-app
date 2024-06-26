package com.kamrul.userapp.enums;

/**
 * Enum representing the active status of entities.
 */
public enum ActiveStatus {
  ACTIVE(1), DELETE(3);

  private final int value;

  /**
   * Constructs an ActiveStatus enum with the specified integer value.
   *
   * @param value The integer value of the active status.
   */
  ActiveStatus(int value) {
    this.value = value;
  }

  /**
   * Gets the integer value of the active status.
   *
   * @return The integer value of the active status.
   */
  public int getValue() {
    return this.value;
  }
}
