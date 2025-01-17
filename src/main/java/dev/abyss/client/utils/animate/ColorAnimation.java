package dev.abyss.client.utils.animate;

import java.awt.Color;

public class ColorAnimation {

	private SimpleAnimation[] animation = new SimpleAnimation[4];

	public ColorAnimation() {
		
		for(int i = 0; i < animation.length; i++) {
			animation[i] = new SimpleAnimation();
		}
	}
	
	public Color getColor(Color color, int speed) {
		
		animation[0].setAnimation(color.getRed(), speed);
		animation[1].setAnimation(color.getGreen(), speed);
		animation[2].setAnimation(color.getBlue(), speed);
		
		return new Color((int) animation[0].getValue(), (int) animation[1].getValue(), (int) animation[2].getValue(), color.getAlpha());
	}
	
	public Color getColor(Color color) {
		return getColor(color, 12);
	}
	
	public void setColor(Color color) {
		animation[0].setValue(color.getRed());
		animation[1].setValue(color.getGreen());
		animation[2].setValue(color.getBlue());
	}

	public Color animateAsState(boolean state, Color start, Color end) {

		if(state) {
			animation[0].setAnimation(start.getRed(), 12);
			animation[1].setAnimation(start.getGreen(), 12);
			animation[2].setAnimation(start.getBlue(), 12);
			animation[3].setAnimation(start.getAlpha(), 12);
		} else {
			animation[0].setAnimation(end.getRed(), 12);
			animation[1].setAnimation(end.getGreen(), 12);
			animation[2].setAnimation(end.getBlue(), 12);
			animation[3].setAnimation(end.getAlpha(), 12);
		}

		return new Color((int) animation[0].getValue(), (int) animation[1].getValue(), (int) animation[2].getValue(), (int) animation[3].getValue());
	}

	public Color animateAsState(boolean state, Color start, Color end, double speed) {

		if(state) {
			animation[0].setAnimation(start.getRed(), speed);
			animation[1].setAnimation(start.getGreen(), speed);
			animation[2].setAnimation(start.getBlue(), speed);
			animation[3].setAnimation(start.getAlpha(), speed);
		} else {
			animation[0].setAnimation(end.getRed(), speed);
			animation[1].setAnimation(end.getGreen(), speed);
			animation[2].setAnimation(end.getBlue(), speed);
			animation[3].setAnimation(end.getAlpha(), speed);
		}

		return new Color((int) animation[0].getValue(), (int) animation[1].getValue(), (int) animation[2].getValue(), (int) animation[3].getValue());
	}
}