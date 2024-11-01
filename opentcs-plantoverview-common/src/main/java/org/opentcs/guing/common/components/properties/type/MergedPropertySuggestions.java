// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.components.properties.type;

import static java.util.Objects.requireNonNull;

import com.google.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.opentcs.components.plantoverview.PropertySuggestions;

/**
 * Merges {@link PropertySuggestions} instances to a single one.
 */
public class MergedPropertySuggestions
    implements
      PropertySuggestions {

  private final Set<String> keySuggestions = new TreeSet<>();
  private final Set<String> valueSuggestions = new TreeSet<>();
  private final Set<PropertySuggestions> propertySuggestions;

  /**
   * Creates a new instance, merging the keys/values of the given suggestions sets.
   *
   * @param propertySuggestions The suggestions to be merged.
   */
  @Inject
  public MergedPropertySuggestions(Set<PropertySuggestions> propertySuggestions) {
    this.propertySuggestions = requireNonNull(propertySuggestions, "propertySuggestors");
    for (PropertySuggestions suggestor : propertySuggestions) {
      keySuggestions.addAll(suggestor.getKeySuggestions());
      valueSuggestions.addAll(suggestor.getValueSuggestions());
    }
  }

  @Override
  public Set<String> getKeySuggestions() {
    return keySuggestions;
  }

  @Override
  public Set<String> getValueSuggestions() {
    return valueSuggestions;
  }

  @Override
  public Set<String> getValueSuggestionsFor(String key) {
    Set<String> mergedCustomSuggestions = new HashSet<>();
    for (PropertySuggestions suggestor : propertySuggestions) {
      Set<String> currentSuggestion = suggestor.getValueSuggestionsFor(key);
      if (currentSuggestion != null) {
        mergedCustomSuggestions.addAll(currentSuggestion);
      }
    }
    return mergedCustomSuggestions;
  }
}
