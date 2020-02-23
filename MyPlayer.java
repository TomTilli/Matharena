package fi.tamk.project.heatmion;

import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
/**
 * 
 * 
 * @author Tom2
 *
 */
public class MyPlayer {
	private int currentPoints;
	private int score;
	private int mode;
	private boolean[] features;
	private boolean[] featuresUnlocked;
	private int backgroundNumber;
	private int characterNumber;
	private String name;
	
	private boolean desertUnlocked;
	private boolean forestUnlocked;
	private boolean seaUnlocked;
	private boolean rainbowUnlocked;
	private int backgroundAmount;
	
	private boolean fennecUnlocked;
	private boolean hedgehogUnlocked;
	private boolean dolphinUnlocked;
	private boolean unicornUnlocked;
	private int characterAmount;
	
	private boolean[] achievements;
	
	private int featuresAmount;
	
	private boolean wrongAnswers;
	private int storyWorldNumber;
	private int timeSpentOnALevel;
	private int timeLeft;
	private int pointsGathered;
	private int timesTheTimeRanOut;  //FROM RUSH
	private int stageReached;
	private int combo;
	private int extraPoints; //FROM JACKPOT
	private int extraTime; //FROM JACKPOT
	private int timesLost; //FROM SUDDEN DEATH
	private int difficulty;
	private boolean achievementsCompleted;
	
	private RecordStore rs;
	
	/**
	 * 
	 * @throws IOException
	 */
	public MyPlayer() throws IOException{
		mode = 1; //Leftover from previous code still in use
		
		achievements = new boolean[38];
		featuresUnlocked = new boolean[] {true, false, true, true, true, true};
		features = new boolean[] {false, false, false, false, false, false};
		
		desertUnlocked = true;
		forestUnlocked = true;
		seaUnlocked = true;
		rainbowUnlocked = true;
		
		fennecUnlocked = true;
		hedgehogUnlocked = true;
		dolphinUnlocked = true;
		unicornUnlocked = true;
		
		try {
		
			
			openRecordStore();
			System.out.println(recoverRecord(3));
			System.out.println("rs.getNumRecords() = " + rs.getNumRecords());
			
//			deletePlayerRecord();
			
			if(rs.getNumRecords() == 0){
				score = 0;
				changePlayerRecord(1, Integer.toString(score));
				name = "Your name here";
				changePlayerRecord(2, name);
				
				int muuttuja = achievements.length + featuresUnlocked.length + 8;
				String sana = "";
				
				for(int i = 0; i < muuttuja; i++){
					sana = sana + 0;
				}
				changePlayerRecord(3, sana);
				
			}else{
				score = Integer.parseInt(recoverRecord(1));
				name = recoverRecord(2);
			}
			System.out.println("tässä");
			
			int muuttuja = achievements.length + featuresUnlocked.length + 8;
			boolean[] taulukko = new boolean[muuttuja];
			String sana1 = recoverRecord(3);
			System.out.println("tässä");
			for(int i = 0; i < muuttuja; i++){
				System.out.print(sana1.charAt(i));
				if(sana1.charAt(i) == '1'){
					taulukko[i] = true;
				}else{
					taulukko[i] = false;
				}
			}
			
			
			desertUnlocked = taulukko[0];
			System.out.println(taulukko[0]);
			forestUnlocked = taulukko[1];
			seaUnlocked = taulukko[2];
			rainbowUnlocked = taulukko[3];
			fennecUnlocked = taulukko[4];
			hedgehogUnlocked = taulukko[5];
			dolphinUnlocked = taulukko[6];
			unicornUnlocked = taulukko[7];
			
			
			for(int i = 8; i < muuttuja - achievements.length; i++){
				featuresUnlocked[i - 8] = taulukko[i];
			}
			
			
			for(int i = 14; i < muuttuja; i++){
				achievements[i - 14] = taulukko[i];
			}
			
	
			closeRecordStore();
			
			
		} catch (RecordStoreException e) {
            System.out.println("Initializing record problem..." + e);
        }
		//TÄYTYY OTTAA TALLENNUKSESTA JOS MAIDEN VÄLISSÄ PYSTYY JATKAMAAN
		backgroundNumber = 1;
		
		characterNumber = 1;
		
		
		
		
	}
	
	public void openRecordStore(){
		try {
            rs = RecordStore.openRecordStore("PlayerRecords", true);
            System.out.println("RecordStore opened!");
           
        } catch (RecordStoreException e) {
            System.out.println("PlayerRecords Problem..." + e);
            
        }
	}
	
	public void savePlayerRecords(){
		openRecordStore();
		changePlayerRecord(1, Integer.toString(score));
		changePlayerRecord(2, name);
		
		int muuttuja = achievements.length + featuresUnlocked.length + 8;
		boolean[] taulukko1 = new boolean[muuttuja];
		
		 taulukko1[0] = desertUnlocked;
		 taulukko1[1] = forestUnlocked;
		 taulukko1[2] = seaUnlocked;
		 taulukko1[3] = rainbowUnlocked;
		 taulukko1[4] = fennecUnlocked;
		 taulukko1[5] = hedgehogUnlocked;
		 taulukko1[6] = dolphinUnlocked;
		 taulukko1[7] = unicornUnlocked;
		
		for(int i = 8; i < muuttuja - achievements.length; i++){
			 taulukko1[i] = featuresUnlocked[i - 8];
		}
		for(int i = 14; i < muuttuja; i++){
			 taulukko1[i] = achievements[i - 14];
		}
		
		String sana2 = "";
		for(int i = 0; i < muuttuja; i++){
			if(taulukko1[i]){
				 sana2 = sana2 + "1";
			}else{
				 sana2 = sana2 + "0";
			}
		}
		System.out.println(sana2);
		changePlayerRecord(3, sana2);
		closeRecordStore();
	}
	
	public void changePlayerRecord(int recordID, String savedRecord){
		try {
            // Save
            
            byte[] tb = savedRecord.getBytes(); //Change the String to bytes;
            if (rs.getNumRecords() <= recordID) { //Check if the record on this ID exists already
                rs.addRecord(tb, 0, tb.length); //Create a new record for this item.
            } else {
                rs.setRecord(recordID, tb, 0, tb.length); //Change the record
            }

        } catch (RecordStoreException e) {
            System.out.println("Saving record problem..." + e);
        }
	}
	
	public String recoverRecord(int recordID){
		byte[] fromrecord;
		String string = "";
		try {
			fromrecord = rs.getRecord(recordID);
			string = new String(fromrecord);
			
			
		}catch (RecordStoreException e) {
			System.out.println("Recovering record problem..." + e);
		}
		
		return string;
	}
	
	public void closeRecordStore(){
		try {
            if (rs != null) {
                rs.closeRecordStore();
                System.out.println("RecordStore closed!");
//                rs.deleteRecordStore("PlayerRecords"); //Kun tarttee poistaa recordit
            }
        } catch (RecordStoreException e) {
            System.out.println("Closing RecordStore Problem..." + e);
        }
	}
	
	public void deletePlayerRecord(){
		try{
			rs.closeRecordStore();
			rs.deleteRecordStore("PlayerRecords");
			System.out.println("Player Record deleted");
		} catch (RecordStoreException e) {
	        System.out.println("deleting RecordStore Problem..." + e);
	    }
		
		resetPlayerRecords();
	}
	
	public void resetPlayerRecords(){
		for(int i = 0; i < features.length; i++){
			features[i] = false;
			featuresUnlocked[i] = false;
		}
		for(int i = 0; i < achievements.length; i++){
			achievements[i] = false;
		}
		desertUnlocked = false;
		forestUnlocked = false;
		seaUnlocked = false;
		rainbowUnlocked = false;
		
		fennecUnlocked = false;
		hedgehogUnlocked = false;
		dolphinUnlocked = false;
		unicornUnlocked = false;
		
		score = 0;
		openRecordStore();
		changePlayerRecord(1, Integer.toString(score));
		name = "Your name here";
		changePlayerRecord(2, name);
		
		int muuttuja = achievements.length + featuresUnlocked.length + 8;
		String sana = "";
		
		for(int i = 0; i < muuttuja; i++){
			sana = sana + 0;
		}
		changePlayerRecord(3, sana);
		
//		changePlayerRecord(3, "0"); //desert
//		changePlayerRecord(4, "0"); //forest
//		changePlayerRecord(5, "0"); //sea
//		changePlayerRecord(6, "0"); //rainbow
//		changePlayerRecord(7, "0"); //fennec
//		changePlayerRecord(8, "0"); //hedgehog
//		changePlayerRecord(9, "0"); //dolphin
//		changePlayerRecord(10, "0"); //unicorn
//		String achievementRecord = "";
//		for(int i = 0; i < achievements.length; i++){
//			achievementRecord = achievementRecord + "0";
//			
//		}
//		changePlayerRecord(11, achievementRecord);
//		
//		String featuresRecord = "";
//		for(int i = 0; i < featuresUnlocked.length; i++){
//			featuresRecord = featuresRecord + "0";
//			
//		}
//		changePlayerRecord(12, featuresRecord);
//		closeRecordStore();
	}
//	/**
//	 * 
//	 * @return
//	 */
//	public Sprite getSprite(){
//		return sprite;
//	}
	
	
	////BACKGROUND SELECTION
	public void setBackground(int worldNumber){
		backgroundNumber = worldNumber;
	}
	
	public int getBackground(){
		return backgroundNumber;
	}
	
	////CHARACTER SELECTION
	public void setCharacter(int charNumber){
		characterNumber = charNumber;
	}
	
	public int getCharacter(){
		return characterNumber;
	}
	/**
	 * 
	 * @return
	 */
	public int getMode(){
		return mode;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public void addToScore(int addition){
		score = score + addition;
	}
	
	public int getScore(){
		return score;
	}
	
	//FEATURE SELECTION
	
	public void setFeature(int feature, boolean onOff){
		switch(feature){
		case 1:
			if(onOff){
				features[0] = true;
			}else{
				features[0] = false;
			}
			break;
		case 2:
			if(onOff){
				features[1] = true;
			}else{
				features[1] = false;
			}
			break;
		case 3:
			if(onOff){
				features[2] = true;
			}else{
				features[2] = false;
			}
			break;
		case 4:
			if(onOff){
				features[3] = true;
			}else{
				features[3] = false;
			}
			break;
		case 5:
			if(onOff){
				features[4] = true;
			}else{
				features[4] = false;
			}
			break;
		case 6:
			if(onOff){
				features[5] = true;
			}else{
				features[5] = false;
			}
			break;
		}
		
	}
	
	public boolean[] getFeaturesOnOff(){
		return features;
	}
	
	public void unlockFeature(int feature){
		switch(feature){
		case 1:
				features[0] = true;
			break;
		case 2:
				features[1] = true;
			break;
		case 3:
				features[2] = true;
			break;
		case 4:
				features[3] = true;
			break;
		case 5:
				features[4] = true;
			break;
		case 6:
				features[5] = true;
			break;
		}
		
	}
	
	public boolean[] getFeaturesUnlocked(){
		return featuresUnlocked;
	}
	
	public void setDesert(boolean unlocked){
		desertUnlocked = unlocked;
	}
	
	public boolean getDesert(){
		return desertUnlocked;
	}
	
	public void setForest(boolean unlocked){
		forestUnlocked = unlocked;
	}
	
	public boolean getForest(){
		return forestUnlocked;
	}
	
	public void setSea(boolean unlocked){
		seaUnlocked = unlocked;
	}
	
	public boolean getSea(){
		return seaUnlocked;
	}
	
	public void setRainbow(boolean unlocked){
		rainbowUnlocked = unlocked;
	}
	
	public boolean getRainbow(){
		return rainbowUnlocked;
	}
	
	public boolean getMenuBoolean(int menu, int choise){
		if(menu == 1){
			if(choise == 1){
				return desertUnlocked;
			}else if(choise == 2){
				return forestUnlocked;
			}else if(choise == 3){
				return seaUnlocked;
			}else{
				return rainbowUnlocked;
			}
		}else if(menu == 2){	
			if(choise == 1){
				return fennecUnlocked;
			}else if(choise == 2){
				return hedgehogUnlocked;;
			}else if(choise == 3){
				return dolphinUnlocked;
			}else{
				return unicornUnlocked;
			}
		}else{
			return false;
		}
	}
	
	
	public int getUnlockedBackgrounds(){
		
		backgroundAmount = 1;
		if(desertUnlocked){
			backgroundAmount++;
		}
		if(forestUnlocked){
			backgroundAmount++;
		}
		if(seaUnlocked){
			backgroundAmount++;
		}
		if(rainbowUnlocked){
			backgroundAmount++;
		}
		
		return backgroundAmount;
	}
	
	public int getUnlockedCharacters(){
		
		characterAmount = 1;
		
		if(fennecUnlocked){
			characterAmount++;
		}
		if(hedgehogUnlocked){
			characterAmount++;
		}
		if(dolphinUnlocked){
			characterAmount++;
		}
		if(unicornUnlocked){
			characterAmount++;
		}
		
		return characterAmount;
	}
	
	public int getUnlockedFeatures(){
		
		featuresAmount = 0;
		
		for(int i = 0; i < features.length; i++){
			if(features[i]){
				featuresAmount++;
			}
		}
		
		return featuresAmount;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	//ACHIEVEMENTS
	
	public void setAchievements(){
		int numero = 0;
		achievementsCompleted = false;
		
		System.out.println(difficulty);
		
		if(achievements[0] == false){ //Complete a level without any wrong answers 1
			System.out.println(difficulty);
			if(wrongAnswers == false && difficulty == 0){
				System.out.println(numero + " = ");
				
				achievements[0] = true;
				achievementsCompleted = true;
			}
		}numero++;
		if(achievements[1] == false){ //Complete the first level in Story Mode 2
			if(storyWorldNumber >= 2 && difficulty == 0){
				System.out.println(numero + " = ");
				featuresUnlocked[0] = true;
				fennecUnlocked = true;
				desertUnlocked = true;
				achievements[1] = true;
				achievementsCompleted = true;
			}
		}numero++;
		if(achievements[2] == false){ //Complete the second level in Story Mode 3
			if(storyWorldNumber >= 3 && difficulty == 0){
				System.out.println(numero + " = ");
				featuresUnlocked[1] = true;
				hedgehogUnlocked = true;
				forestUnlocked = true;
				achievements[2] = true;
				achievementsCompleted = true;
			}
		}numero++;
		if(achievements[3] == false){ //Complete the third level in Story Mode 4
			if(storyWorldNumber >= 4 && difficulty == 0){
				System.out.println(numero + " = ");
				featuresUnlocked[2] = true;
				dolphinUnlocked = true;
				seaUnlocked = true;
				achievements[3] = true;
				achievementsCompleted = true;
			}
		}numero++;
		if(achievements[4] == false){ //Complete the fourth level in Story Mode 5
			if(storyWorldNumber >= 5 && difficulty == 0){
				System.out.println(numero + " = ");
				featuresUnlocked[3] = true;
				unicornUnlocked = true;
				rainbowUnlocked = true;
				achievements[4] = true;
				achievementsCompleted = true;
			}
		}numero++;
		if(achievements[5] == false){ //Complete the fifth level in Story Mode 6
			if(storyWorldNumber == 6 && difficulty == 0){
				System.out.println(numero + " = ");
				featuresUnlocked[4] = true;
				featuresUnlocked[5] = true;
				achievements[5] = true;
				achievementsCompleted = true;
			}
		}numero++;
		if(achievements[6] == false){
			if(timeSpentOnALevel <= 30 && difficulty == 0){ //Complete a level in Story Mode in 30 seconds
				System.out.println(numero + " = ");
				
			achievements[6] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[7] == false){
			if(timeSpentOnALevel <= 50 && difficulty == 0){ //Complete a level in Story Mode in 50 seconds 8
				System.out.println(numero + " = ");
				
			achievements[7] = true;
			achievementsCompleted = true;
		}} //Arena mode: \/
		numero++;
		if(achievements[8] == false){
			if(timeLeft == 1){ //Complete a Level with only 1 second time left 9
				System.out.println(numero + " = ");
				
			achievements[8] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[9] == false){
			if(pointsGathered  >= 1000){ //Gain 1000 points in on round
				System.out.println(numero + " = ");
			achievements[9] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[10] == false){
			if(pointsGathered  >= 5000){ //Gain 5000 points in on round
				System.out.println(numero + " = ");
			achievements[10] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[11] == false){
			if(pointsGathered  >= 25000){ //Gain 25000 points in on round
				System.out.println(numero + " = ");
			achievements[11] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[12] == false){
			if(pointsGathered  >= 125000){ //Gain 125000 points in on round
				System.out.println(numero + " = ");
			achievements[12] = true;
			achievementsCompleted = true;
		}} //Rush: \/
		numero++;
		if(achievements[13] == false){
			if(timesTheTimeRanOut == 0 && difficulty != 0 && features[0]){ //Do not let the question time run out in Rush
				System.out.println(numero + " = ");
			achievements[13] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[14] == false){
			if(timeSpentOnALevel > 180 && difficulty == 1){ //Survive for 3 minutes in survival with the easy difficulty
				System.out.println(numero + " = ");
			achievements[14] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[15] == false){
			if(timeSpentOnALevel > 180 && difficulty == 2){ //Survive for 3 minutes in survival with the medium difficulty
				System.out.println(numero + " = ");
			achievements[15] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[16] == false){
			if(timeSpentOnALevel > 180 && difficulty == 3){ //Survive for 3 minutes in survival with the hard difficulty
				System.out.println(numero + " = ");
			achievements[16] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[17] == false){
			if(timeSpentOnALevel > 180 && difficulty == 4){ //Survive for 3 minutes in survival with the expert difficulty
				System.out.println(numero + " = ");
			achievements[17] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[18] == false){
			if(stageReached >= 10 && features[1] && difficulty != 0){ //Survive to stage 10 in with survival active
				System.out.println(numero + " = ");
			achievements[18] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[19] == false){
			if(stageReached >= 30 && features[1] && difficulty != 0){ // Survive to stage 30 in with survival active
				System.out.println(numero + " = ");
			achievements[19] = true;
			achievementsCompleted = true;
		}} //combo:
		numero++;
		if(achievements[20] == false){
			if(combo >= 5 && features[2]){ //Get a compo of 5 with Combo feature active
				System.out.println(numero + " = ");
			achievements[20] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[21] == false){
			if(combo >= 10 && features[2]){ //Get a combo of 10 with Combo feature active
				System.out.println(numero + " = ");
			achievements[21] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[22] == false){
			if(combo >= 30 && features[2]){ //Get a combo of 30 with Combo feature active
				System.out.println(numero + " = ");
			achievements[22] = true;
			achievementsCompleted = true;
		}} //Jackpot:
		numero++;
		if(achievements[23] == false){
			if(extraPoints >= 1000 && features[3]){ //Get 1000 extra points with Jackpot
				System.out.println(numero + " = ");
			achievements[23] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[24] == false){
			if(extraPoints >= 2000 && features[3]){ //Get 2000 extra points with Jackpot
				System.out.println(numero + " = ");
			achievements[24] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[25] == false){
			if(extraTime >= 60 && features[3]){ //Get a minute extra time with Jackpot
				System.out.println(numero + " = ");
			achievementsCompleted = true;
		}} //Sudden Death:
		numero++;
		if(achievements[26] == false){
			if(timesLost == 0 && pointsGathered >= 50 && features[4]){ //Get 50 points without having to get a new try in Tension.
				System.out.println(numero + " = ");
			achievements[26] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[27] == false){
			if(timesLost == 0 && pointsGathered >= 500 && features[4]){ //Get 500 points without having to get a new try in Tension.
				System.out.println(numero + " = ");
			achievements[27] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[28] == false){
			if(timesLost == 0 && pointsGathered >= 2000 && features[4]){ //Get 2000 points without having to get a new try.
				System.out.println(numero + " = ");
			achievements[28] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[29] == false){
			if(timesLost >= 6 && pointsGathered >= 50 && features[4]){ //Get a new chance 5 times in one game with sudden death
				System.out.println(numero + " = ");
			achievements[29] = true;
			achievementsCompleted = true;
		}} //All IN?:
		numero++;
		if(achievements[30] == false){
			if(stageReached >= 20 && features[0] && features[1] && features[2] && features[3] && features[4]){ //Get to stage 20 with all features active.
				System.out.println(numero + " = ");
			achievements[30] = true;
			achievementsCompleted = true;
		}} //General:
		numero++;
		if(achievements[31] == false){
			if(score >= 1000000){ //Gain 1 000 000 points (lvl 10)
				System.out.println(numero + " = ");
			achievements[31] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[32] == false){
			if(score >= 10000000){ //Gain 10 000 000 points
				System.out.println(numero + " = ");
			achievements[32] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[33] == false){
			if(fennecUnlocked && hedgehogUnlocked && dolphinUnlocked && unicornUnlocked){ //Unlock all Characters
				System.out.println(numero + " = ");
			achievements[33] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[34] == false){
			if(desertUnlocked && forestUnlocked && seaUnlocked && rainbowUnlocked){ //Unlock all Worlds
				System.out.println(numero + " = ");
			achievements[34] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[35] == false){
			if(featuresUnlocked[0] && featuresUnlocked[1] && featuresUnlocked[2] && featuresUnlocked[3] && featuresUnlocked[4]){  //Unlock all features
				System.out.println(numero + " = ");
			achievements[35] = true;
			featuresUnlocked[5] = true; //Unlocks All in
			achievementsCompleted = true;
		}}numero++;
		if(achievements[36] == false){ //Unlock all Characters, Worlds and Features
			
			if(achievements[33] && achievements[34] && achievements[35]){
				System.out.println(numero + " = ");
			achievements[36] = true;
			achievementsCompleted = true;
		}}numero++;
		if(achievements[37] == false){ //Calling you genius would be underrating
			
			boolean variable = true;
			for(int i = 0; i < achievements.length; i++){
				
				
				if(achievements[i] == false){
					i = achievements.length;
					variable = false;
				}
			}
			if(variable){
				achievements[37] = true;
				achievementsCompleted = true;
				System.out.println(numero + " = ");
			}
			
		} 
		
	}
	
	public boolean checkForAchievements(){
		return achievementsCompleted;
	}
	
	public boolean[] getAchievements(){
		return achievements;
	}
	
	public void setWrongAnswer(boolean x){
		wrongAnswers = x;
	}
	public void setTimeSpent(int x){
		timeSpentOnALevel = x;
	}
	public void setTimeLeft(int x){
		timeLeft = x;
	}
	public void setPointsGathered(int x){
		pointsGathered = x;
	}
	public void resetTimesTheTimerRanOut(){
		timesTheTimeRanOut = 0;
	}
	
	public void setTimesTheTimeRanOut(){
		timesTheTimeRanOut++;
	}
	public void setStageReached(int x){
		stageReached = x;
	}
	public void setCombo(int x){
		combo = x;
	}
	public void resetExtraPoints(){
		extraPoints = 0;
	}
	public void resetExtraTime(){
		extraTime = 0;
	}
	public void setExtraPoints(int x){
		extraPoints = extraPoints + x;
	}
	public void setExtraTime(int x){
		extraTime = extraTime + x;
	}
	public void resetTimesLost(){
		timesLost = 0;
	}
	public void setTimesLost(){
		timesLost++;
	}
	public void giveDifficulty(int x){
		difficulty = x;
	}
	public void setStoryWorldNumber(int x){
		storyWorldNumber = x;
	}
	
	public void unlockAll(){
		for(int i = 0; i < features.length; i++){
			featuresUnlocked[i] = true;
		}
		for(int i = 0; i < achievements.length; i++){
			achievements[i] = true;
		}
		desertUnlocked = true;
		forestUnlocked = true;
		seaUnlocked = true;
		rainbowUnlocked = true;
		
		fennecUnlocked = true;
		hedgehogUnlocked = true;
		dolphinUnlocked = true;
		unicornUnlocked = true;
	}
	
	
}
