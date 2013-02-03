P5RuntimeFullScreen
===================

Fullscreen mode for Processing that actually lets you switch (on the fly) between a windowed and a fullscreen sketch. With support for Processing 2 (beta), customisable hotkeys, OpenGL, Proscene, Eclipse, …
  
## Usage

The most basic example (also, see examples):

Download the package and place it in the **libraries** folder of your Processing sketch directory. A working and minimal result would be **/Processing/libraries/P5RuntimeFullScreen/library/P5RuntimeFullScreen.jar**.

**Import** the library.

	import com.insomnia.controllers.FullScreenController;


Create a **global variable**.

	FullScreenController fullScreenController;


**Initialise** the controller with your applet instance in your init() function and call super().

	void init() {
	  FullScreenController.init(this);
	  super.init();
	}


**Instantiate** the FullScreenController class with the **applet instance**, the **shortcut key** (used in combination with command on Mac or control on Windows) and the **title bar height**. The last two parameters are optional.

	void setup() {
		fullScreenController = new FullScreenController(this, 'f', 22);
	}


Call **draw** to draw the custom title bar.

	void draw() {
	  fullScreenController.draw();
	}
  
  
### Using Proscene?

Call **draw** between these Proscene functions to draw the title bar in 2D.

void draw() {
	scene.beingScreenDrawing();
	FullScreenController.draw();
	scene.endScreenDrawing();
}