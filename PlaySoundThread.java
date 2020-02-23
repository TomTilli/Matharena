package fi.tamk.project.heatmion;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

class PlaySoundThread extends Thread {
	private Player soundEffect;

	private boolean isSoundOn = false;
	
	public boolean isSoundOn() {
		return isSoundOn;
	}
	
	public PlaySoundThread(String name, TitleScreen host) {
		loadSoundEffect(name);
		
		// Will start to listen player states
		soundEffect.addPlayerListener(host);
	}
	
	private void loadSoundEffect(String name) {
		InputStream in = getClass().getResourceAsStream("/" + name);
		try {
			soundEffect = Manager.createPlayer(in, "audio/pmeg");
			soundEffect.prefetch();	    
		} catch (IOException e) {
			System.out.println("Failed to load external sound file.");
			e.printStackTrace();
		} catch (MediaException e) {
			System.out.println("Failed to understand the file.");
			e.printStackTrace();
		}
	}

//	public void stop(){
//		playerOn = false;
//	}
	
	public void run() {
		
		try {
			isSoundOn = true;
			soundEffect.start();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}	
}
