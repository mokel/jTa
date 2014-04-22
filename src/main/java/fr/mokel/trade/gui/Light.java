package fr.mokel.trade.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.apache.commons.lang.BooleanUtils;

public class Light extends JPanel {

	private Boolean green = null;
	private Integer state = null;

	public Boolean isGreen() {
		return green;
	}

	public void setGreen(boolean green) {
		this.green = Boolean.valueOf(green);
		repaint();
	}

	public Light() {
		setMaximumSize(new Dimension(15,15));
		setMinimumSize(new Dimension(15,15));
		setPreferredSize(new Dimension(15,15));
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, 15, 15);
		g.setColor(getColor());
		g.fillOval(0, 0, 15, 15);
	}
	
	private Color getColor() {
		Color c = Color.DARK_GRAY;
		if(BooleanUtils.isTrue(green)) {
			c = Color.GREEN;
		}
		if(BooleanUtils.isFalse(green)) {
			c = Color.RED;
		}
		if(state != null) {
			if(state.intValue() > 0) {
				c = new Color(255 - state.intValue(), 255,255 - state.intValue());
			}
			if(state.intValue() < 0) {
				c = new Color(255, 255 + state.intValue(),255 + state.intValue());
			}
		}
		return c;
	}

	public void setState(Integer state) {
		this.state = state;
		repaint();
	}

	public void setGray(boolean b) {
		green = null;
		repaint();
	}
}
