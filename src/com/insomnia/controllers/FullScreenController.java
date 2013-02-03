package com.insomnia.controllers;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class FullScreenController {
	private PApplet p;
	private char triggerKey;
	private int draggerHeight;

	private boolean isDragging;
	private boolean isOverDragger;

	private int draggerAlpha;
	private Point mouseDownCompCoords;

	public boolean isFullscreen;

	public FullScreenController(PApplet $applet, char $triggerKey, int $draggerHeight) {
		p = $applet;
		triggerKey = $triggerKey;
		draggerHeight = $draggerHeight;

		isFullscreen = false;
		isOverDragger = false;
		isDragging = false;
		draggerAlpha = 0;

		p.registerMethod("keyEvent", this);
		p.registerMethod("mouseEvent", this);
		// p.registerMethod("draw", this);
	}
	public FullScreenController(PApplet $applet, char $triggerKey) {
		this($applet, $triggerKey, 80);
	}
	public FullScreenController(PApplet $applet) {
		this($applet, 'f');
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void toggleFullscreen() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		// TODO: support for multiple screens ¬
		// Get screen on which sketch is showing
		// Set full screen on that screen

		if(!isFullscreen) {
			if(gd.isFullScreenSupported()) {
				gd.setFullScreenWindow(p.frame);
			}
		} else {
			gd.setFullScreenWindow(null);
		}

		p.requestFocus(); // Let PApplet regain focus to receive events, …
		isFullscreen = !isFullscreen;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void keyEvent(KeyEvent event) {
		switch(event.getAction()) {
		case KeyEvent.RELEASE:
			if(event.getKey() == triggerKey && event.isMetaDown()) {
				toggleFullscreen();
			}
			break;
		}
	}
	public void mouseEvent(MouseEvent event) {
		if(isFullscreen) { // Disable frame dragging when fullscreen is in effect
			return;
		}

		switch(event.getAction()) {
		case MouseEvent.MOVE:
			isOverDragger = pointInRectangle(new Point(event.getX(), event.getY()), 0, 0, p.width, draggerHeight + 2);
			break;
		case MouseEvent.PRESS:
			isDragging = pointInRectangle(new Point(event.getX(), event.getY()), 0, 0, p.width, draggerHeight + 2);
			mouseDownCompCoords = new Point(event.getX(), event.getY());
			// TODO: disable *all* mouse events
			break;
		case MouseEvent.DRAG:
			if(isDragging) {
				Point currCoords = MouseInfo.getPointerInfo().getLocation();
				p.frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
				// PApplet.println(currCoords);
			}
			break;
		case MouseEvent.RELEASE:
			isDragging = false;
			mouseDownCompCoords = null;
			break;
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void draw() {
		if(!isFullscreen) {
			p.pushStyle();
			p.noStroke();
			// p.fill(255, isOverDragger ? 255 : 0);
			p.fill(255, draggerAlpha);
			p.rect(0, 0, p.width, draggerHeight);
			p.popStyle();
		}

		if(isOverDragger && draggerAlpha < 255) {
			draggerAlpha += 20;
		} else if(!isOverDragger && draggerAlpha > 0) {
			draggerAlpha -= 20;
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void init(PApplet $p) {
		$p.frame.removeNotify();
		$p.frame.setResizable(true);
		$p.frame.setUndecorated(true);
		$p.frame.addNotify();
	}

	public static boolean pointInRectangle(Point $point, double $x, double $y, double $width, double $height) {
		return (($point.x >= $x) && ($point.x <= $x + $width) && ($point.y >= $y) && ($point.y <= $y + $height));
	}
}
