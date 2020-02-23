package fi.tamk.project.heatmion;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;


/**
 * Starts the title screen.
 * <p>
 * A screen to navigate between games and options.
 * 
 * @author Tom2
 *
 */
public class TitleScreen extends GameCanvas implements PlayerListener{

	private Graphics g;
	
//	private PlaySoundThread playsoundthread;
	private Player music;
	
	private Sprite matharenaScreen;
	private Sprite background;
	private Sprite soundsOn;
	private Sprite soundsOff;
	private LayerManager titleManager;
	private Display display;
	
	private boolean firstScreen;
	
	private MyGameCanvas gameCanvas;
	private ArenaOptions arenaOptions;
	private MenuAndOptions menus;
	private MyPlayer player;
	
	private boolean sounds;
	private boolean changingScreens;
	
//	private RecordStore rs;
//	private RecordStore arenaRS;
	
	public TitleScreen(Display display){
		super(true);
		
		this.display = display;
		g = getGraphics();
		sounds = false;
		firstScreen = true;
		
		loadSoundEffect();
		
		try {
			paintScreen();
			player = new MyPlayer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		changingScreens = false;
		
		//Get information for player object.
		
	}
	
	
	/**
	 * Paints the title screen.
	 * 
	 * @throws IOException
	 */
	
	protected void paintScreen() throws IOException {
		// TODO Auto-generated method stub
		titleManager = new LayerManager();
		
		matharenaScreen= new Sprite(Image.createImage("/menu_matharenascreen.png"));
		matharenaScreen.setPosition(0, -15);
		
		background = new Sprite(Image.createImage("/menu_title.png"));
		background.setPosition(0, -15);
		
		soundsOn = new Sprite(Image.createImage("/menu_volumeon.png"));
		soundsOn.setPosition(190, 266);
		soundsOn.setVisible(false);
		
		soundsOff = new Sprite(Image.createImage("/menu_volumeoff.png"));
		soundsOff.setPosition(190, 266);
		
		titleManager.append(matharenaScreen);
		titleManager.append(soundsOn);
		titleManager.append(soundsOff);
		titleManager.append(background);
		
		titleManager.paint(g, 0, 0);
		
		
//		g.drawString("START A NORMAL GAME", 40, 50, Graphics.TOP | Graphics.LEFT);
//		g.drawString("START AN ARENA MODE", 20, 200, Graphics.TOP | Graphics.LEFT);
		
		g.setColor(0,0,0);
		g.fillRect(0, 0, 30, 30);
		
		
		flushGraphics();
		
	}
	
	
	/**
	 * Checks if an area on the screen has been pressed.
	 * <p>
	 * The pressed area will be determined when a spot on the screen will be released.
	 */
	public void pointerReleased(int x, int y){
		
		if(firstScreen){
			firstScreen = false;
			matharenaScreen.setVisible(false);
			titleManager.paint(g,0,0);
			flushGraphics();
		}else if(y < 30 && x < 30 && changingScreens == false){ //DELETE CORNER
			deleteRecords();
		}else if(x < 220 && x > 18 && y < 98 && y > 55 && changingScreens == false){ //STORY MODE
			changingScreens = true;
			gameCanvas = new MyGameCanvas(display, this, player);
			display.setCurrent(gameCanvas);
			gameCanvas.startGame();
		}else if(x < 220 && x > 18 && y < 148 && y > 105 && changingScreens == false){//ARENA MODE
			changingScreens = true;
			arenaOptions = new ArenaOptions(display, this, player);
//			arenaOptions.startArenaOptions();
			display.setCurrent(arenaOptions);
		}else if(x < 220 && x > 18 && y < 246 - 47 && y > 185 - 29 && changingScreens == false){ //PROFILE
			changingScreens = true;
			menus = new MenuAndOptions(display, this, player, 4);
//			menus.startMenu(4);
			display.setCurrent(menus);
		}else if(x < 220 && x > 18 && y < 249 && y > 206 && changingScreens == false){ //OPTIONS
			changingScreens = true;
			menus = new MenuAndOptions(display, this, player, 1);
//			menus.startMenu(1);
			display.setCurrent(menus);
		}else if(x < 52 && x > 18 && y < 309 && y > 309 - 34 && changingScreens == false){ //HELP
			changingScreens = true;
			menus = new MenuAndOptions(display, this, player, 3);
//			menus.startMenu(3);
			display.setCurrent(menus);
		}else if(x < 220 && x > 186 && y < 309 && y > 260){
			
			if(sounds){
				sounds = false;
				// Play the sound if it's not on.
//				if(playsoundthread.isSoundOn()) {
//					playsoundthread.stop();
//				}
				playerControl(false);
				soundsOn.setVisible(false);
				soundsOff.setVisible(true);
				titleManager.paint(g, 0, 0);
				flushGraphics();
			}else{
				sounds = true;
				// Play the sound if it's not on.
//				if(!playsoundthread.isSoundOn()) {
//					playsoundthread.start();
//				}
				playerControl(true);
				soundsOn.setVisible(true);
				soundsOff.setVisible(false);
				titleManager.paint(g, 0, 0);
				flushGraphics();
			}
			
			
		}else if(x < 175 && x > 140 && y < 309 && y > 260){
			player.unlockAll();
			System.out.println("Everything Unlocked");
		}
		
		
		
		
	}
	
	public void saveRecords(){
//		System.out.println("höm");
		player.savePlayerRecords();
	}
	
	public void closeRecords(){
		
		player.closeRecordStore();
		try{
			arenaOptions.closeRecordStore();
		}catch(NullPointerException e){
			System.out.println("No ArenaOptions created yet");
		}
		
	}
	
	public void deleteRecords(){
		player.deletePlayerRecord();
		player.openRecordStore();
		try{
			arenaOptions.deleteArenaRecord();
			arenaOptions.openRecordStore();
			arenaOptions.initializeRecordStore();
		}catch(NullPointerException e){
			System.out.println("No ArenaOptions created yet");
		}
	}
	
	public void deleteArenaOptionsOnly(){
		try{
			arenaOptions.deleteArenaRecord();
			arenaOptions.openRecordStore();
			arenaOptions.initializeRecordStore();
		}catch(NullPointerException e){
			System.out.println("No ArenaOptions created yet");
		}
	}
	
	public void nullGameCanvas(){
		gameCanvas = null;
		changingScreens = false;
		System.out.println("game nulled");
	}
	
	public void nullMenuScreens(){
		menus = null;
		changingScreens = false;
		System.out.println("menus nulled");
	}
	
	public void nullArenaOptions(){
		try{
			arenaOptions = null;
		}catch(Exception e){
			System.out.println("nothing to null");
		}
		changingScreens = false;
		System.out.println("arenaOptions nulled");
	}
	
//	private void loadSoundEffect() {
//		playsoundthread = new PlaySoundThread("happy_adveture.mp3", this);
//	}
	
	public void playerUpdate(Player player, String event, Object eventData) {
		// Create new thread if sound has been played
//		if (event.equals(PlayerListener.END_OF_MEDIA)) {
////			playsoundthread = new PlaySoundThread("happy_adveture.mp3", this);
//			playerControl(true);
//		}
	}
	
	private void loadSoundEffect() {
        InputStream in = getClass().getResourceAsStream("/happy test.mp3");
        try {
            music = Manager.createPlayer(in, "audio/mpeg");
//            music.addPlayerListener(this);
            // Let's load the file and it should be ready to go.
            
            // You don't have to invoke realize, since prefetch will do that
            // for you:
            //   "If prefetch is called when the Player is in the UNREALIZED state, 
            //   it will implicitly call realize."
            music.realize();
            music.prefetch();
            
            
        } catch (IOException e) {
            System.out.println("Failed to load external sound file.");
            e.printStackTrace();
        } catch (MediaException e) {
            System.out.println("Failed to understand the file.");
            e.printStackTrace();
        }
        
        music.setLoopCount(-1);
    }
	
	private void playerControl(boolean onOff) {
        try {
        	if(onOff){
        		music.start();
        	}else{
        		music.stop();
        	}
        } catch (MediaException e) {
            System.out.println("Failed to play the sound!");
            e.printStackTrace();
        }
    }
}
