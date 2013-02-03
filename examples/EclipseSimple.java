import processing.core.PApplet;
import com.insomnia.controllers.FullScreenController;

@SuppressWarnings("serial")
public class EclipseSimple extends PApplet {
	private FullScreenController fullScreenController;

	public static void main(String args[]) {
		PApplet.main(new String[] {"EclipseSimple"});
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void init() {
		FullScreenController.init(this); // Initialize FullScreenController (just sets your frame undecorated and resizable)
		super.init();
	}

	public void setup() {
		size(1280, 720, OPENGL);
		frameRate(30);

		//
		fullScreenController = new FullScreenController(this, 'f', 22); // PApplet instance, command/control + "key" shortcut, title bar height
	}
	public void draw() {
		pushStyle();
		noStroke();
		fill(0, 80);
		rect(0, 0, width, height);
		popStyle();

		// The only line you actually need for the FullScreenController
		fullScreenController.draw(); // Call draw on FullScreenController (draws title bar)

		// Mouse draw stroke test
		stroke(255);
		if(mousePressed) {
			line(mouseX, mouseY, pmouseX, pmouseY);
		}

		// Display framerate
		text(frameRate, 10, 20);
	}
}