package fi.tamk.project.heatmion;

import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

public class ScoreLoop extends GameCanvas implements Runnable, CommandListener {
	
	private Thread scoreLoop;
	private boolean SCORECOUNTER_ON;
	private boolean screenPressed;
	private int playerScore;
	private int gameScore;
	private int timeScore;
	
	private Graphics g;
	private Display display;
	private MyGameCanvas game;
	private TitleScreen title;
	private MyPlayer player; //Pisteiden antoa varten (Tästä sais myös playerin scoren suoraan)
	
	private Sprite scoreScreen;
	private Sprite victory;
	private Sprite defeat;
	private Sprite timeout;
	private Sprite contentUnlocked;
	private Sprite continueIcon;
	
	//ENDSCREENSTUFF//
	private Sprite endScreen;
	private boolean endVisible;
	private int timeToComplete;
	private int endCorrectAnswers;
	private int endWrongAnswers;
	private int endPointsGathered;
	private int world;
	//ENDSCREENSTUFF//
	
	private int pointsHeight;
	
	private LayerManager scoreManager; 
	
	private Command back;
	
	public ScoreLoop(int playerScore, int gameScore, int timeScore, String condition, Display display, MyGameCanvas game, TitleScreen title, MyPlayer player){
		super(true);
		
		g = getGraphics();
		this.display = display;
		this.game = game;
		this.title = title;
		this.player = player;
		
		this.playerScore = playerScore;
		this.gameScore = gameScore;
		this.timeScore = timeScore;
		pointsHeight = 145;
		endVisible = false;
		
		back = new Command("back", Command.BACK, 0);
		this.addCommand(back);
		this.setCommandListener(this);
		
		try {
			initializeScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		player.setAchievements();
		if(player.checkForAchievements()){
			contentUnlocked.setVisible(true);
		}
		this.player.savePlayerRecords();
		
		if(condition == "win"){
			victory.setVisible(true);
			defeat.setVisible(false);
			scoreManager.paint(g, 0, 0);
			flushGraphics();
		}else if(condition == "lose"){
			defeat.setVisible(true);
			victory.setVisible(false);
			scoreManager.paint(g, 0, 0);
			flushGraphics();
		}else if(condition == "time"){
			defeat.setVisible(false);
			victory.setVisible(false);
			timeout.setVisible(true);
			scoreManager.paint(g, 0, 0);
			flushGraphics();
		}else{
			defeat.setVisible(true);
			victory.setVisible(false);
			scoreManager.paint(g, 0, 0);
			flushGraphics();
		}
		
		
		
		
		
		
	}
	
	public void startScoreScreen(){
		
		paintScore();
		screenPressed = false;
		SCORECOUNTER_ON = true;
		
		scoreLoop = new Thread(this);
		scoreLoop.start();
	}
	
	public void initializeScreen() throws IOException{
		
		scoreManager = new LayerManager();
		
//		if(world > 5){
			endScreen = new Sprite(Image.createImage("/game_endscreen.png"));
			endScreen.setPosition(0, -15);
			scoreManager.append(endScreen);
			endScreen.setVisible(false);
//		}
		
		//SCORESCREEN//
		scoreScreen = new Sprite(Image.createImage("/score_background.png"));
		scoreScreen.setPosition(0, -15);
		victory = new Sprite(Image.createImage("/victory.png"));
		victory.setPosition((getWidth() - victory.getWidth()) / 2, 10);
		defeat = new Sprite(Image.createImage("/Defeat.png"));
		defeat.setPosition((getWidth() - defeat.getWidth()) / 2, 10);
		timeout = new Sprite(Image.createImage("/Defeat.png"));
		timeout.setPosition((getWidth() - defeat.getWidth()) / 2, 10);
		contentUnlocked = new Sprite(Image.createImage("/content_unlocked.png"));
		contentUnlocked.setPosition(35, 178);
		scoreManager.append(contentUnlocked);
		contentUnlocked.setVisible(false);
		scoreManager.append(victory);
		victory.setVisible(false);
		scoreManager.append(defeat);
		defeat.setVisible(false);
		scoreManager.append(timeout);
		timeout.setVisible(false);
		continueIcon = new Sprite(Image.createImage("/score_continue.png"));
		continueIcon.setPosition(22, 238);
		continueIcon.setVisible(false);
		scoreManager.append(continueIcon);
		scoreManager.append(scoreScreen);
		//SCORESCREEN//
		
		scoreManager.paint(g, 0, 0);
		flushGraphics();
				
	}
	
	public void paintScore(){
		g.setColor(0,0,0);
		g.drawString("Time Left: ", 35, 60, Graphics.TOP | Graphics.LEFT);
		g.drawString("" + timeScore, 205, 60, Graphics.TOP | Graphics.RIGHT);
		g.drawString("Points Gained: ", 35, 85, Graphics.TOP | Graphics.LEFT);
		g.drawString("" + gameScore, 205, 85, Graphics.TOP | Graphics.RIGHT);
		g.drawString("Total Points: ", 35, pointsHeight, Graphics.TOP | Graphics.LEFT);
		g.drawString("" + playerScore, 205, pointsHeight, Graphics.TOP | Graphics.RIGHT);
		flushGraphics();
	}
	
	public void run() {
		
		while(SCORECOUNTER_ON){
			if(screenPressed == false){
				if(timeScore > 0){
					gameScore++;
					timeScore--;
				}else if (gameScore > 0){
					playerScore++;
					gameScore--;
				}else{
					player.setScore(playerScore);
					SCORECOUNTER_ON = false;
					continueIcon.setVisible(true);
					flushGraphics();
				}
				scoreManager.paint(g, 0, 0);
				paintScore();
				
				try{
					Thread.sleep(1);
				}catch(Exception e){}
			}else{
				playerScore = playerScore + gameScore + timeScore;
				gameScore = 0;
				timeScore = 0;
				scoreManager.paint(g, 0, 0);
				player.setScore(playerScore);
				paintScore();
				SCORECOUNTER_ON = false;
			}
		}
		// TODO Auto-generated method stub
		
	}
	
	public int getPlayerScore(){
		return playerScore;
	}
	
	public int getGameScore(){
		return gameScore;
	}
	
	public int getTimeScore(){
		return timeScore;
	}
	
	public void stopScoreCounter(){
		SCORECOUNTER_ON = false;
	}
	
	public void pointerReleased(int x, int y){
		if(SCORECOUNTER_ON && screenPressed == false){
			screenPressed = true;
			continueIcon.setVisible(true);
			flushGraphics();
		}else if(endVisible){
			player.savePlayerRecords();
			display.setCurrent(title);
			game.nullScoreScreen();
			title.nullGameCanvas();
		}else if(y < 280 && y > 260 && x < 220 && x > 20){
			SCORECOUNTER_ON = false;
			if (world > 5 && endVisible == false){
				endScreen.setVisible(true);
				victory.setVisible(false);
				scoreManager.paint(g, 0, 0);
				g.drawString("Points Gathered: " + endPointsGathered, 42, 183, Graphics.TOP | Graphics.LEFT);
				g.drawString(endCorrectAnswers + " answered correctly. ", 42, 203, Graphics.TOP | Graphics.LEFT);
				g.drawString(endWrongAnswers + " answered wrong.", 42, 223, Graphics.TOP | Graphics.LEFT);
				g.drawString(timeToComplete + " seconds to finish.", 42, 243, Graphics.TOP | Graphics.LEFT);
				flushGraphics();
				endVisible = true;
			}else if(victory.isVisible()){
				game.showInfo();
				player.savePlayerRecords();
				display.setCurrent(game);
			}else{
				player.savePlayerRecords();
				display.setCurrent(title);
				title.nullGameCanvas();
				game.cleanUp();
				
			}
		}
	}

	public void commandAction(Command arg0, Displayable arg1) {
		if(SCORECOUNTER_ON == false){
			player.savePlayerRecords();
			display.setCurrent(title);
			title.nullGameCanvas();
			game.cleanUp();
		}else{
			screenPressed = true;
			continueIcon.setVisible(true);
			flushGraphics();
		}
		
	}
	
	public void getEndStats(int timeToComplete, int endCorrectAnswers, int endWrongAnswers, int endPointsGathered, int world){
		this.timeToComplete = timeToComplete;
		this.endCorrectAnswers = endCorrectAnswers;
		this.endWrongAnswers = endWrongAnswers;
		this.endPointsGathered = endPointsGathered;
		this.world = world;
	}
	
	public void showEndScreen(){
		
		
		scoreScreen.setVisible(false);
		victory.setVisible(false);
		defeat.setVisible(false);
		timeout.setVisible(false);
		contentUnlocked.setVisible(false);
		continueIcon.setVisible(false);
		
		scoreManager.paint(g,0,0);
		
		g.drawString("Points Gathered: " + endPointsGathered, 205, pointsHeight, Graphics.TOP | Graphics.RIGHT);
		g.drawString(endCorrectAnswers + " out of " + (endCorrectAnswers + endWrongAnswers) + "total answered correctly.", 205, pointsHeight, Graphics.TOP | Graphics.RIGHT);
		g.drawString(timeToComplete + " seconds to finish.", 205, pointsHeight, Graphics.TOP | Graphics.RIGHT);
		flushGraphics();
	}
	
	public void pointerPressed(int x, int y){
		System.out.println("x = " + x + ", y = " + y);
	}
}
