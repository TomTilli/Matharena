package fi.tamk.project.heatmion;

import java.io.IOException;
import java.util.Random;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;


public class MenuAndOptions extends GameCanvas implements CommandListener{
	
	private Display display;
	private TitleScreen titleScreen;
	private MyPlayer player;
	
	private Graphics g;
	private Command back;
	private int screenNumber; // 1 = options, 2 = about, 3 = help, 4 = profile, 5 = achievements, 6 = reseting profile
	private int level; //Levels 0 - 20
	private int nextLevel; //always double the amount
	
	private Sprite optionsScreen;
	private Sprite aboutScreen;
	private Sprite helpScreen;
	private Sprite profileScreen;
	private Sprite achievementsScreen;
	private Sprite resetingScreen;
	private LayerManager manager;
	private TiledLayer achiUnlocks;
	private int[] achiLocations;
	private TiledLayer profilePic;
	
	// Current location
	private int verticalLoc, horizontalLoc, verticalLocTiled;
	// Last pointer (finger) location
	private int lastX, lastY;
	
	public MenuAndOptions(Display display, TitleScreen titleScreen, MyPlayer player, int screenNumber){
		super(true);
		
		this.display = display;
		this.titleScreen = titleScreen;
		this.player = player;
		this.screenNumber = screenNumber;
		
		g = getGraphics();
		
		back = new Command("back", Command.BACK, 0);
		this.addCommand(back);
		this.setCommandListener(this);
		
		try {
			initializeSprites();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if(screenNumber == 1){
			optionsScreen.setVisible(true);
		}else if(screenNumber == 3){
			helpScreen.setVisible(true);
		}else{
			profileScreen.setVisible(true);
		}
		paintScreen();
		
	}
	
	public void startMenu(int screenNumber){
		
	}
	
	public void initializeSprites() throws IOException{
		
		manager = new LayerManager();
		
		optionsScreen = new Sprite(Image.createImage("/menu_options.png"));
		optionsScreen.setPosition(0, -15);
		optionsScreen.setVisible(false);
		
		aboutScreen = new Sprite(Image.createImage("/menu_about.png"));
		aboutScreen.setPosition(0, -15);
		aboutScreen.setVisible(false);
		
		helpScreen = new Sprite(Image.createImage("/menu_help.png"));
		helpScreen.setPosition(0, -15);
		helpScreen.setVisible(false);
		
		profileScreen = new Sprite(Image.createImage("/menu_profile.png"));
		profileScreen.setPosition(0, -15);
		profileScreen.setVisible(false);
		
		achievementsScreen = new Sprite(Image.createImage("/menu_achievements.png"));
		achievementsScreen.setPosition(0, -15);
		achievementsScreen.setVisible(false);
		
		resetingScreen = new Sprite(Image.createImage("/menu_resetingscreen.png"));
		resetingScreen.setPosition(getWidth()/2 - 106, getHeight()/2 - 98);
		resetingScreen.setVisible(false);
		
		if(screenNumber == 4){
//			Random random = new Random();
			
			profilePic = new TiledLayer(1, 1,Image.createImage("/profile_icon.png"),72, 72);
			profilePic.setPosition(40, 75);
			profilePic.setCell(0, 0,(new Random(new Random().nextInt()).nextInt(player.getUnlockedBackgrounds())) + 1);
			
		}
		
		manager.append(resetingScreen);
		manager.append(optionsScreen);
		manager.append(aboutScreen);
		manager.append(helpScreen);
		manager.append(profileScreen);
		manager.append(achievementsScreen);
		
	}
	
//	public void openOptions(){
//		optionsScreen.setVisible(true);
//	}
//	
//	public void openAbout(){
//		aboutScreen.setVisible(true);
//	}
//	
//	public void openHelp(){
//		helpScreen.setVisible(true);
//	}
	
	public void pointerReleased(int x, int y){ //ABOUT
		if(screenNumber == 1){
			if(x < 220 && x > 18 && y < 128 - 5 && y > 82 - 15){
				aboutScreen.setVisible(true);
				optionsScreen.setVisible(false);
				paintScreen();
				screenNumber = 2;
			}else if(x < 220 && x > 18 && y < 182 && y > 136){
				System.out.println("RESET PROFILE");
				resetingScreen.setVisible(true);
				paintScreen();
				screenNumber = 6;
			}else if(x < 220 && x > 18 && y < 262 && y > 195){
				System.out.println("RESET ARENAOPTIONS");
				titleScreen.deleteArenaOptionsOnly();
			}
		}else if(screenNumber == 4){
			if(x < 220 && x > 18 && y < 287 && y > 245){
				
				try {
					achiUnlocks = new TiledLayer(1, 133,Image.createImage("/menu_achiunlocked.png"),21, 22);
					achiUnlocks.setPosition(12, 83);
					
					achiLocations = new int[] {1, 4, 9, 14, 18, 22, 26, 29, 33, 36, 38, 41, 
							43, 46, 49, 53, 57, 62, 67, 70, 74, 78, 82, 85, 89, 92, 96, 
							100, 103, 106, 110, 113, 116, 120, 122, 125, 129, 133};
					for(int i = 0; i < achiLocations.length; i++){
						if(player.getAchievements()[i]){
							achiUnlocks.setCell(0, achiLocations[i] - 1, 1);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Achiunlocks failed tiled layer");
				}
				
				achievementsScreen.setVisible(true);
				profileScreen.setVisible(false);
				screenNumber = 5;
				paintScreen();
				verticalLoc = -15;
				verticalLocTiled = 83;
//				System.out.println("Achievements");
			}
		}else if(screenNumber == 6){
			if(x < 200 && x > 42 && y < 184 && y > 145){ //YES
				titleScreen.deleteRecords();
				resetingScreen.setVisible(false);
				screenNumber = 1;
				paintScreen();
			}else if(x < 200 && x > 42 && y < 240 && y > 200){  //NO
				resetingScreen.setVisible(false);
				screenNumber = 1;
				paintScreen();
			}
		}
	}
	
	public void pointerPressed(int x, int y){
		lastX = x;
		lastY = y;
		
//		System.out.println("x = " + x + ", y = " + y);
	}
	
	protected void pointerDragged(int x, int y) {
//		  // Calculate how much we moved horizontally
//		  int deltaHorizontal = lastX – x;
//		  horizontalLoc -= deltaHorizontal;
//		  lastX = x;
		 
		  //1918
		if(screenNumber == 5){
			// Calculate how much we moved vertically
			  int deltaVertical = lastY - y;
			  verticalLoc -= deltaVertical;
			  verticalLocTiled -= deltaVertical;
			  lastY = y;
			  
			  if(verticalLoc <= -15 && verticalLoc >= 0 - (achievementsScreen.getHeight() - getHeight())){
				  achievementsScreen.setPosition(0, verticalLoc);
				  achiUnlocks.setPosition(14, verticalLocTiled);
			  }else{
				  if(verticalLoc > -15){
					  verticalLoc = -15;
					  verticalLocTiled = 83;
				  }else{
					  verticalLoc = 0 - (achievementsScreen.getHeight() - getHeight() + 15);
					  verticalLocTiled = 0 - (133 * 22) + 85 + (4*22) + 14;
				  }
			  }
			 
		  // Repaint the screen since we have scrolled
		  paintScreen();
		}else if(screenNumber == 3){
			// Calculate how much we moved vertically
			  int deltaVertical = lastY - y;
			  verticalLoc -= deltaVertical;
			  lastY = y;
			  
			  
			  if(verticalLoc <= -15 && verticalLoc >= 0 - (helpScreen.getHeight() - getHeight())){
				  helpScreen.setPosition(0, verticalLoc);
			  }else{
				  if(verticalLoc > -15){
					  verticalLoc = -15;
				  }else{
					  verticalLoc = 0 - (helpScreen.getHeight() - getHeight() + 15);
				  }
			  }
			 
		  // Repaint the screen since we have scrolled
		  paintScreen();
		}
	}
	
	
	public void paintScreen(){
		
		manager.paint(g, 0, 0);
		if(screenNumber == 5){
			achiUnlocks.paint(g);
		}
		
		if(screenNumber == 4){
			g.setColor(0,0,0);
			
//			g.drawString(player.getName(), 127, 73, Graphics.TOP | Graphics.LEFT);
			g.drawString("Level: " + getLevel(), 127, 120, Graphics.TOP | Graphics.LEFT);
			g.drawString("Your rank:", 125, 66, Graphics.TOP | Graphics.LEFT);
			g.drawString("________", 127, 66, Graphics.TOP | Graphics.LEFT);
			g.drawString("" + getTitle(), 127, 92, Graphics.TOP | Graphics.LEFT);
			g.drawString("Total Points:  " + player.getScore(), 36 + 2, 160, Graphics.TOP | Graphics.LEFT);
			g.drawString("Points to", 38 /*+ 8 + 2*/, 169 + 15, Graphics.TOP | Graphics.LEFT);
			g.drawString("next level: ", 36 /*+ 8*/ + 2, 201, Graphics.TOP | Graphics.LEFT);
			g.drawString("" + getPointsToNextLevel(), 36 + 95 + 2, 197, Graphics.TOP | Graphics.LEFT);
			
			
		}
		if(screenNumber == 4){
			profilePic.paint(g);
		}
		flushGraphics();
		
	}

	public void commandAction(Command c, Displayable d) {
		// TODO Auto-generated method stub
		
		
		
		if(screenNumber == 2){
			optionsScreen.setVisible(true);
			aboutScreen.setVisible(false);
			paintScreen();
			screenNumber = 1;
		}else if(screenNumber == 5){
			profileScreen.setVisible(true);
			achievementsScreen.setVisible(false);
			achievementsScreen.setPosition(0, -15);
			verticalLoc = -15;
			screenNumber = 4;
			paintScreen();
			
		}else if(screenNumber == 6){
			resetingScreen.setVisible(false);
			screenNumber = 1;
			paintScreen();
		}else{
			if(screenNumber == 3){
				verticalLoc = -15;
			}
			optionsScreen.setVisible(false);
			aboutScreen.setVisible(false);
			helpScreen.setVisible(false);
			profileScreen.setVisible(false);
			display.setCurrent(titleScreen);
			titleScreen.nullMenuScreens();
		}
	}
	
	
	public int getLevel(){
		
		level = 0;
		nextLevel = 19;
		
//		if(player.getScore() < nextLevel){
//			return level;
		if(player.getScore() > nextLevel){
			for(int i = 0; i < 100; i++){
				level++;
				nextLevel = nextLevel * 2;
				if(player.getScore() < nextLevel){
					return level;
				}
			}
		}
		
		return level;
	}
	
	public int getPointsToNextLevel(){
		return nextLevel - player.getScore();
	}
	
	public String getTitle(){
		
		if(level == 0){
			
		}if(level == 1){
			return "Novice";
		}else if(level == 2){
			return "Learning";
		}else if(level == 3){
			return "Numerous";
		}else if(level == 4){
			return "Clever";
		}else if(level == 5){
			return "Cool";
		}else if(level == 6){
			return "Excellent";
		}else if(level == 7){
			return "Smart";
		}else if(level == 8){
			return "Exceptional";
		}else if(level == 9){
			return "Fantastic";
		}else if(level == 10){
			return "Expert";
		}else if(level == 11){
			return "Victorious";
		}else if(level == 12){
			return "Powerful";
		}else if(level == 13){
			return "Flawless";
		}else if(level == 14){
			return "Master";
		}else if(level == 15){
			return "Gifted";
		}else if(level == 16){
			return "Genius";
		}else if(level == 17){
			return "Calculator";
		}else if(level == 18){
			return "Majestic";
		}else if(level == 19){
			return "Unbeatable";
		}else if(level == 20){
			return "The One";
		}	
		
		
		
		return "Beginner";
	}
	
}

