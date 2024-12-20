// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.components.drawing.figures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import org.jhotdraw.draw.AttributeKeys;
import org.opentcs.guing.common.components.drawing.course.Origin;

/**
 * An OffsetFigure is an (invisible) figure that moves as the user drags the view
 * beyond its current bounds, so the view becomes larger resp is repainted
 * larger.
 */
public class OffsetFigure
    extends
      OriginFigure {

  @SuppressWarnings("this-escape")
  public OffsetFigure() {
    super();
    setModel(new Origin()); // The figure needs a model to work
    set(AttributeKeys.STROKE_COLOR, Color.darkGray);
    setVisible(false);      // only visible for test
  }

  @Override
  protected void drawStroke(Graphics2D g) {
    // Shape: "Crosshair" with square
    Rectangle r = (Rectangle) fDisplayBox.clone();

    if (r.width > 0 && r.height > 0) {
      g.drawLine(r.x + r.width / 2, r.y, r.x + r.width / 2, r.y + r.height);
      g.drawLine(r.x, r.y + r.height / 2, r.x + r.width, r.y + r.height / 2);
      r.grow(-4, -4);
      g.drawRect(r.x, r.y, r.width, r.height);
    }
  }
}
