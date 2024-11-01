// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.data;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Nonnull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A transient reference to a {@link TCSObject}.
 *
 * @param <E> The actual object class.
 */
public class TCSObjectReference<E extends TCSObject<E>>
    implements
      Serializable {

  /**
   * The referenced object's class.
   */
  private final Class<?> referentClass;
  /**
   * The referenced object's name.
   */
  private final String name;

  /**
   * Creates a new TCSObjectReference.
   *
   * @param referent The object this reference references.
   */
  protected TCSObjectReference(
      @Nonnull
      TCSObject<E> referent
  ) {
    requireNonNull(referent, "newReferent");

    referentClass = referent.getClass();
    name = referent.getName();
  }

  /**
   * Returns the referenced object's class.
   *
   * @return The referenced object's class.
   */
  public Class<?> getReferentClass() {
    return referentClass;
  }

  /**
   * Returns the referenced object's name.
   *
   * @return The referenced object's name.
   */
  public final String getName() {
    return name;
  }

  @Override
  public boolean equals(Object otherObj) {
    if (otherObj == this) {
      return true;
    }
    if (!(otherObj instanceof TCSObjectReference)) {
      return false;
    }

    TCSObjectReference<?> otherRef = (TCSObjectReference<?>) otherObj;
    return Objects.equals(referentClass, otherRef.referentClass)
        && Objects.equals(name, otherRef.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return "TCSObjectReference{"
        + "referentClass=" + referentClass
        + ", name=" + name
        + '}';
  }
}
