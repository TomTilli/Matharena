package fi.tamk.project.heatmion;

import java.util.Random;

public class Feature {
	
	private boolean timedAnswerOn;
	private boolean survivalOn;
	private boolean combosOn;
	private boolean jackpotOn;
	private boolean suddenDeathOn;
	
	private Random rndSeed;
	
	public Feature(boolean timedAnswers, boolean survival, boolean combos, boolean jackpot, boolean suddenDeath){
		timedAnswerOn = timedAnswers;
		survivalOn = survival;
		combosOn = combos;
		jackpotOn = jackpot;
		suddenDeathOn = suddenDeath;
		
		rndSeed = new Random();
	}
	
	//Feature timedAnswering
	public void setTimedAnsweriOn(boolean choice){
		timedAnswerOn = choice;
	}
	
	public boolean getTimedAnswer(){
		return timedAnswerOn;
	}
	//Survival
	public void setSurvivalOn(boolean choice){
		survivalOn = choice;
	}
	public boolean getSurvival(){
		return survivalOn;
	}
	//Combos
	public void setCombosOn(boolean choice){
		combosOn = choice;
	}
	public boolean getCombos(){
		return combosOn;
	}
	//Jackpot
	public void setJackpotOn(boolean choise){
		jackpotOn = choise;
	}
	public boolean getJackpot(){
		return jackpotOn;
	}
	//SuddenDeath
	public void setSuddenDeathOn(boolean choise){
		suddenDeathOn = choise;
	}
	public boolean getSuddenDeath(){
		return suddenDeathOn;
	}
	
	public int getRandomJackpot(int bulbsOn, int secondBulb){
		int jackpot = (new Random(rndSeed.nextInt()).nextInt(12));
		
		
		if(bulbsOn < 2){
			if(jackpot < 4){
				return 1;
			}else if (jackpot < 8){
				return 2;
			}
			return 3;
		}
		
		switch(secondBulb){
		case 2: //GREEN
			if(jackpot < 7){
				return 1;
			}else if (jackpot < 9){
				return 2;
			}
			return 3;
		case 3: //RED
			if(jackpot < 2){
				return 1;
			}else if (jackpot < 9){
				return 2;
			}
			return 3;
			
			
		}
		//IF NOTHING ELSE HAPPENS THEN THE SECOND BULB IS YELLOW
		if(jackpot < 3){
			return 1;
		}else if (jackpot < 6){
			return 2;
		}
		return 3;
		
	}
	
	public boolean getRandomSuddenDeath(){
		int suddenDeath = (new Random(rndSeed.nextInt()).nextInt(2));
		
		if(suddenDeath < 1){
			return true;
		}
		
		return false;
	}
	//
}
