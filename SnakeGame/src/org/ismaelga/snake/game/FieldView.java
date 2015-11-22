package org.ismaelga.snake.game;

import java.awt.*;
import javax.swing.*;

/**
 * Game board.
 * 
 * @Ismael @06-2010
 */
public class FieldView extends JPanel {

	private final int GRID_VIEW_SCALING_FACTOR = 7;

	private int gridWidth, gridHeight;
	private int xScale, yScale;
	Dimension size;
	private Graphics g;
	private Image fieldImage;

	/**
	 * Create a new FieldView component.
	 */
	public FieldView(int height, int width) {
		gridHeight = height;
		gridWidth = width;
		size = new Dimension(0, 0);

	}

	/**
	 * Tell the GUI manager how big we would like to be.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR, gridHeight * GRID_VIEW_SCALING_FACTOR);
	}

	/**
	 * @return Width in pixels.
	 */
	public int getWith() {
		return gridWidth * GRID_VIEW_SCALING_FACTOR;
	}

	/**
	 * @return Height in pixels.
	 */
	public int getHeight() {
		return gridHeight * GRID_VIEW_SCALING_FACTOR;
	}

	/**
	 * Change he size
	 * 
	 * @param width
	 * @param height
	 */
	public void setTamanho(int width, int height) {
		gridHeight = height;
		gridWidth = width;
	}

	/**
	 * Prepare for a new round of painting. Since the component may be resized,
	 * compute the scaling factor again.
	 */
	public void preparePaint() {
		if (!size.equals(getSize())) { // if the size has changed...
			size = getSize();
			fieldImage = this.createImage(size.width, size.height);
			g = fieldImage.getGraphics();

			xScale = size.width / gridWidth;
			if (xScale < 1) {
				xScale = GRID_VIEW_SCALING_FACTOR;
			}
			yScale = size.height / gridHeight;
			if (yScale < 1) {
				yScale = GRID_VIEW_SCALING_FACTOR;
			}
		}
	}

	/**
	 * Paint on grid location on this field in a given color. If the color its
	 * white draws a rectangular shape, if not draw an oval.
	 */
	public void drawMark(int x, int y, Color color) {
		g.setColor(color);
		if (color == color.white)
			g.fillRect(x * xScale, y * yScale, xScale, yScale);
		else
			g.fillOval(x * xScale, y * yScale, xScale, yScale);
	}

	/**
	 * The field view component needs to be redisplayed. Copy the internal image
	 * to screen.
	 */
	public void paintComponent(Graphics g) {
		if (fieldImage != null) {
			g.drawImage(fieldImage, 0, 0, null);
		}
	}
}
