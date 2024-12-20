// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.components.kernel.routing;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Nonnull;
import org.opentcs.data.model.Path;

/**
 * A wrapper for {@link Path}s that can be used to build routing graphs.
 */
public class Edge {

  /**
   * The path in the model that is traversed on this edge.
   */
  private final Path path;
  /**
   * Whether the path is travelled in reverse direction.
   */
  private final boolean travellingReverse;

  /**
   * Creates a new instance.
   *
   * @param modelPath The path in the model that is traversed on this edge.
   * @param travellingReverse Whether the path is travelled in reverse direction.
   */
  public Edge(
      @Nonnull
      Path modelPath,
      boolean travellingReverse
  ) {
    this.path = requireNonNull(modelPath, "modelPath");
    this.travellingReverse = travellingReverse;
  }

  /**
   * Returns the path in the model that is traversed on this edge.
   *
   * @return The path in the model that is traversed on this edge.
   */
  public Path getPath() {
    return path;
  }

  /**
   * Indicates whether the path is travelled in reverse direction.
   *
   * @return Whether the path is travelled in reverse direction.
   */
  public boolean isTravellingReverse() {
    return travellingReverse;
  }

  /**
   * Returns the source vertex of this edge.
   *
   * @return The source vertex of this edge.
   */
  public String getSourceVertex() {
    return isTravellingReverse()
        ? path.getDestinationPoint().getName()
        : path.getSourcePoint().getName();
  }

  /**
   * Returns the target vertex of this edge.
   *
   * @return The target vertex of this edge.
   */
  public String getTargetVertex() {
    return isTravellingReverse()
        ? path.getSourcePoint().getName()
        : path.getDestinationPoint().getName();
  }

  @Override
  public String toString() {
    return "Edge{"
        + "path=" + path + ", "
        + "travellingReverse=" + travellingReverse + ", "
        + "sourceVertex=" + getSourceVertex() + ", "
        + "targetVertex=" + getTargetVertex()
        + '}';
  }
}
