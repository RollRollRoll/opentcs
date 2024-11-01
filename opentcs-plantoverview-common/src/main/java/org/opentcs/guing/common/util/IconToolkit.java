// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.util;

import java.net.URL;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class for loading icons.
 */
public class IconToolkit {

  /**
   * This class's logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(IconToolkit.class);
  /**
   * The default path for icons.
   */
  private static final String DEFAULT_PATH = "/org/opentcs/guing/res/symbols/";
  /**
   * The single instance of this class.
   */
  private static IconToolkit fInstance;

  /**
   * Creates a new instance.
   */
  private IconToolkit() {
  }

  /**
   * Returns the single instance of this class.
   *
   * @return The single instance of this class.
   */
  public static IconToolkit instance() {
    if (fInstance == null) {
      fInstance = new IconToolkit();
    }

    return fInstance;
  }

  /**
   * Creates an ImageIcon.
   *
   * @param fullPath The full (absolute) path of the icon file.
   * @return The icon, or <code>null</code>, if the file does not exist.
   */
  public ImageIcon getImageIconByFullPath(String fullPath) {
    URL url = getClass().getResource(fullPath);

    if (url != null) {
      return new ImageIcon(url);
    }
    else {
      LOG.warn("Icon not found: {}", fullPath);
      return null;
    }
  }

  /**
   * Creates an ImageIcon.
   *
   * @param relativePath The relative path of the icon file.
   * @return The icon, or <code>null</code>, if the file does not exist.
   */
  public ImageIcon createImageIcon(String relativePath) {
    return getImageIconByFullPath(DEFAULT_PATH + relativePath);
  }
}
