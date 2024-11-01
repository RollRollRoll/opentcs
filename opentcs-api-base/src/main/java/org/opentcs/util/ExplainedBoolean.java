// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.util;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Nonnull;

/**
 * A boolean with an explanation/reason for its value.
 */
public class ExplainedBoolean {

  /**
   * The actual value.
   */
  private final boolean value;
  /**
   * A reason/explanation for the value.
   */
  private final String reason;

  /**
   * Creates a new instance.
   *
   * @param value The actual value.
   * @param reason A reason/explanation for the value.
   */
  public ExplainedBoolean(
      boolean value,
      @Nonnull
      String reason
  ) {
    this.value = value;
    this.reason = requireNonNull(reason, "reason");
  }

  /**
   * Returns the actual value.
   *
   * @return The actual value.
   */
  public boolean getValue() {
    return value;
  }

  /**
   * A reason/explanation for the value.
   *
   * @return The reason
   */
  @Nonnull
  public String getReason() {
    return reason;
  }
}
