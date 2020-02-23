package fi.tamk.project.heatmion;
//import fi.tamk.project.utilities.Debug;

import java.io.IOException;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MyMIDlet extends MIDlet {
	
	private Display display;
	private MenuAndOptions menus;
	private TitleScreen titleScreen;
	
	

	public MyMIDlet() {
		// TODO Auto-generated constructor stub
		//Debug.loadDebugLevel(this);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
		titleScreen.saveRecords();
		titleScreen.closeRecords();

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
		
		
		
		
		display = display.getDisplay(this);
		
		titleScreen = new TitleScreen(display);
		
		display.setCurrent(titleScreen);
		
		
		//display.setCurrent(menus.getForm(1));
		
		
	}
	
	

}
