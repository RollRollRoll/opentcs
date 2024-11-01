// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.servicewebapi.v1.binding.posttransportorder;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.List;
import org.opentcs.kernel.extensions.servicewebapi.v1.binding.shared.Property;

/**
 * A destination of a transport.
 */
public class Destination {

  private String locationName;

  private String operation;

  private List<Property> properties;

  @JsonCreator
  public Destination(
      @Nonnull
      @JsonProperty(required = true, value = "locationName")
      String locationName,
      @Nonnull
      @JsonProperty(required = true, value = "operation")
      String operation,
      @Nullable
      @JsonProperty(required = false, value = "properties")
      List<Property> properties
  ) {
    this.locationName = requireNonNull(locationName, "locationName");
    this.operation = requireNonNull(operation, "operation");
    this.properties = properties;
  }

  public Destination() {
  }

  @Nonnull
  public String getLocationName() {
    return locationName;
  }

  public Destination setLocationName(
      @Nonnull
      String locationName
  ) {
    this.locationName = requireNonNull(locationName, "locationName");
    return this;
  }

  @Nonnull
  public String getOperation() {
    return operation;
  }

  public Destination setOperation(
      @Nonnull
      String operation
  ) {
    this.operation = requireNonNull(operation, "operation");
    return this;
  }

  @Nullable
  public List<Property> getProperties() {
    return properties;
  }

  public Destination setProperties(
      @Nullable
      List<Property> properties
  ) {
    this.properties = properties;
    return this;
  }
}
