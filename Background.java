package fi.tamk.project.heatmion;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Background {
	
	private int worldNumber;
	private int characterNumber;
	
	private String background;
	private String answerSquare;
	private String fallingObject;
	private String questionNumbers;
	private String answerNumbers;
	private String countdown;
	private String questionBox;
	private String pointsBox;
	private String redBox;
	private String greenBox;
	
	private String character;
	private String loseAnimationStart;
	private String loseAnimation;
	private String idleAnimation;
	private String winAnimation;
	
	public Background(int background, int character){
		worldNumber = background;
		characterNumber = character;
		setWorldGraphics();
		
		pointsBox = "bugcalcubox";
		redBox = "Redicecube";
		greenBox = "greenicecube";
		countdown = "countdown";
	}
	
	public void setWorldGraphics(){
		
		//BACKGROUND
		switch(worldNumber){
		case 1:
			background = "ice_background";
			answerSquare = "ice_cube";
			fallingObject = "ice_snowflake";
			questionNumbers = "ice_numbers";
			answerNumbers = "ice_smallnumbers";
			questionBox = "ice_questionbox";
			
			//Spriteen liittyviä nyt väliaikaisesti tässä /////////////////////
//			loseAnimationStart = "pingitkustart";
//			loseAnimation = "pingitku";
//			character = "pingviinipeliin";
//			idleAnimation = "Pingviiniidle";
//			winAnimation = "Tanssikoodiin";
			break;
		case 2:
			background = "desert_background"; //Fennec
			answerSquare = "desert_cube"; //Fennec
			fallingObject = "ice_snowflake";
			questionNumbers = "ice_numbers";
			answerNumbers = "ice_smallnumbers";
			questionBox = "desert_questionbox"; //Fennec
			//Spriteen liittyviä nyt väliaikaisesti tässä /////////////////////
//			loseAnimationStart = "pingitkustart";
//			loseAnimation = "pingitku";
//			character = "fenneckoodiin"; //OIKEA
//			idleAnimation = "Pingviiniidle";
//			winAnimation = "Tanssikoodiin";
			break;
		case 3:
			background = "forest_background";
			answerSquare = "forest_cube";
			fallingObject = "ice_snowflake";
			questionNumbers = "ice_numbers";
			answerNumbers = "ice_smallnumbers";
			questionBox = "forest_questionbox";
			break;
		case 4:
			background = "sea_background";
			answerSquare = "sea_cube";
			fallingObject = "ice_snowflake";
			questionNumbers = "ice_numbers";
			answerNumbers = "ice_smallnumbers";
			questionBox = "sea_questionbox";
			break;
		case 5:
			background = "rainbow_background";
			answerSquare = "rainbow_cube";
			fallingObject = "ice_snowflake";
			questionNumbers = "ice_numbers";
			answerNumbers = "ice_smallnumbers";
			questionBox = "rainbow_questionbox";
			break;
		}
		
		
		//CHARACTER
		switch(characterNumber){
		case 1: //Penguin
			//Spriteen liittyviä nyt väliaikaisesti tässä /////////////////////
			loseAnimationStart = "penguin_losestart";
			loseAnimation = "penguin_lose";
			character = "pingviinipeliin";
			idleAnimation = "penguin_idle";
			winAnimation = "penquin_win";
			break;
		case 2: //fennec
			//Spriteen liittyviä nyt väliaikaisesti tässä /////////////////////
			loseAnimationStart = "fennec_losestart";
			loseAnimation = "fennec_lose";
			character = "fennec"; //Fennec
			idleAnimation = "fennec_idle";
			winAnimation = "penquin_win";
			break;
		case 3: //hedgehog
			loseAnimationStart = "hedgehog_losestart";
			loseAnimation = "hedgehog_lose";
			character = "hedgehog";
			idleAnimation = "hedgehog_idle";
			winAnimation = "penquin_win";
			break;
		case 4: //dolphin
			loseAnimationStart = "dolphin_losestart";
			loseAnimation = "dolphin_lose";
			character = "dolphin";
			idleAnimation = "penguin_idle";
			winAnimation = "penquin_win";
			break;
		case 5: //unicorn
			loseAnimationStart = "unicorn_losestart";
			loseAnimation = "unicord_lose";
			character = "unicorn";
			idleAnimation = "penguin_idle";
			winAnimation = "penquin_win";
			break;
		}
	}
	
//	public void setCharacterGraphics(){
//		switch(worldNumber){
//		case 1:
//			//Spriteen liittyviä nyt väliaikaisesti tässä /////////////////////
//			loseAnimation = "kavelyanimaatio2";
//			character = "pingviinipeliin";
//			break;
//		case 2:
//			break;
//		case 3:
//			break;
//		case 4:
//			break;
//		case 5:
//			break;
//		}
//	}
	
	public String getBackgroundGraphic(){
		return background;
	}
	
	public String getAnswerSquares(){
		return answerSquare;
	}
	
	public String getFallingObjects(){
		return fallingObject;
	}
	
	public String getNumbers(){
		return questionNumbers;
	}
	
	public String getSmallNumbers(){
		return answerNumbers;
	}
	
	public String getCountdown(){
		return countdown;
	}
	
	public String getQuestionBox(){
		return questionBox;
	}
	
	public String getPointsBox(){
		return pointsBox;
	}
	
	public String getRedBox(){
		return redBox;
	}
	
	public String getGreenBox(){
		return greenBox;
	}
	////Character graphics from here on////
	public String getCharacter(){
		return character;
	}
	
	public String getLoseAnimationStart(){
		return loseAnimationStart;
	}
	
	public String getLoseAnimation(){
		return loseAnimation;
	}
	
	public String getIdleAnimation(){
		return idleAnimation;
	}
	
	public String getWinAnimation(){
		return winAnimation;
	}
	
}
