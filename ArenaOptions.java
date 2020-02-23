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
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class ArenaOptions extends GameCanvas implements CommandListener{
	
	private Display display;
	private MyPlayer player;
	private TitleScreen title;
	private MyGameCanvas gameCanvas;
	private Command back;
	
	private Sprite background;
	private Sprite picMenu;
	private Sprite difMenu;
	private Sprite[] yes;
	private Sprite[] no;
	private Sprite notAvailable;
	private Sprite firstThumb;
	private Sprite secondThumb;
//	private Sprite forestThumb;
//	private Sprite chosenBackground;
	private int[] pressableBackgroundButtons;
	private int[] pressableCharacterButtons;
	
	private LayerManager spritesManager;
	private LayerManager thumbnailsManager;
	private TiledLayer backgroundMenu;
	private TiledLayer backgroundThumbs;
	private TiledLayer chosenBackground;
	private TiledLayer characterMenu;
	private TiledLayer characterThumbs;
	private TiledLayer chosenCharacter;
	private TiledLayer difficultyMenu;
	private TiledLayer difficultyThumbs;
	private TiledLayer chosenDifficulty;
	
	private boolean backgroundSelection;
	private boolean characterSelection;
	private boolean[] featuresSelections;
//	private boolean featuresSelection1;
//	private boolean featuresSelection2;
//	private boolean featuresSelection3;
//	private boolean featuresSelection4;
//	private boolean featuresSelection5;
//	private boolean featuresSelection6;
	private boolean difficultySelection;
	
	
	private Graphics g;
	private RecordStore rs;
	
	protected ArenaOptions(Display display, TitleScreen title, MyPlayer player) {
		super(true);
		
		this.display = display;
		this.title = title;
		this.player = player;
		
		
		g = getGraphics();
		featuresSelections = new boolean[6];
		
		try{
			//re.deleteRecordStore("ArenaRecords");
			openRecordStore();
			System.out.println("Arena rs.getNumRecords() = " + rs.getNumRecords());
			if(rs.getNumRecords() == 0){
				initializeRecords();
			}
			closeRecordStore();
		} catch (RecordStoreException e) {
	        System.out.println("Arena Records Problem..." + e);
	    }
		
		try {
			openRecordStore();
			initializeSprites();
			initializeTiledLayers();
//			initializeOptionsFromRecords();
			initializeFeatures();
			closeRecordStore();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("GGGGGGGGGGGGGGGGGGGG");
		}
		
		paintObjects();
		
		back = new Command("back", Command.BACK, 0);
		this.addCommand(back);
		this.setCommandListener(this);
	}
	
	public void startArenaOptions(){
		
		
		
	}
	
	public void initializeRecords(){
//		Valittu tausta, hahmo, vaikeus, featuret (Kuvat ja tiedot)
		changePlayerRecord(1, "1"); //BAKCGROUND
		changePlayerRecord(2, "1"); //CHARACTER
		changePlayerRecord(3, "1"); //DIFFICULTY
		changePlayerRecord(4, "0"); //FEATURE 1 (RUSH)
		changePlayerRecord(5, "0"); //FEATURE 2 (SURVIVAL)
		changePlayerRecord(6, "0"); //FEATURE 3 (COMBO)
		changePlayerRecord(7, "0"); //FEATURE 4	(JACKPOT)
		changePlayerRecord(8, "0"); //FEATURE 5	(SUUDDEN DEATH)
		changePlayerRecord(9, "0"); //FEATURE 6 (TBA)
	}
	
	public void initializeFeatures(){
		
		
		int recordID = 4;
		
		for(int i = 0; i < 6; i++){
			if(Integer.parseInt(recoverRecord(recordID)) == 1){
				//System.out.println(recordID + recoverRecord(recordID));
				
				featuresSelections[i] = true;
				
				player.setFeature(i + 1, true);
				
				if(player.getFeaturesUnlocked()[i]){
					yes[i].setVisible(true);
					no[i].setVisible(false);
				}
				
			}else{
				//System.out.println(recordID + recoverRecord(recordID));
				
				featuresSelections[i] = false;
				
				player.setFeature(i + 1, false);
				
				if(player.getFeaturesUnlocked()[i]){
					no[i].setVisible(true);
				}
				
			}
			recordID++;
		}
		
	}
	
//	public void initializeOptionsFromRecords(){
//		switch(){
//		case: 1
//			break;
//		case: 1
//		break;
//		case: 1
//		break;
//		case: 1
//		break;
//		}
//		
//	}
	
	public void initializeSprites() throws IOException{
		spritesManager = new LayerManager();
		thumbnailsManager = new LayerManager();
		
		background = new Sprite(Image.createImage("/arena_options.png"));
		background.setPosition(0, -15);
		
//		chosenBackground = new Sprite(Image.createImage("/" + getChosenGraphic("background", 1) + ".png"));
////		initializeChosenOptions(chosenBackground, 1);
//		chosenBackground.setPosition(23, 106 - 15 - 3 - 34);
//		spritesManager.append(chosenBackground);
		
		
		
//		picMenu = new Sprite(Image.createImage("/smallerbutton.png"));
//		difMenu = new Sprite(Image.createImage("/button.png"));
		yes = new Sprite[6];
		for(int i = 0; i < 6 /*player.getUnlockedFeatures()*/; i++){
			yes[i] = new Sprite(Image.createImage("/arena_yes.png")) ;
			spritesManager.append(yes[i]);
			yes[i].setVisible(false);
		}
		yes[0].setPosition(98, 186 - 16);
		yes[1].setPosition(98, 203 - 16);
		yes[2].setPosition(98, 221 - 16);
		yes[3].setPosition(191, 186 - 16);
		yes[4].setPosition(191, 203 - 16);
		yes[5].setPosition(191, 221 - 16);
		
		no = new Sprite[6];
		for(int i = 0; i < 6 /*player.getUnlockedFeatures()*/; i++){
			no[i] = new Sprite(Image.createImage("/arena_no.png")) ;
			spritesManager.append(no[i]);
			if(player.getFeaturesOnOff()[i]){
				no[i].setVisible(true);
			}else{
				no[i].setVisible(false);
			}
		}
		no[0].setPosition(98, 186 - 16);
		no[1].setPosition(98, 203 - 16);
		no[2].setPosition(98, 221 - 16);
		no[3].setPosition(191, 186 - 16);
		no[4].setPosition(191, 203 - 16);
		no[5].setPosition(191, 221 - 16);
		
		spritesManager.append(background);
		
		
	}
	
	public String getChosenGraphic(String sprite, int choise){
		
		
		if(sprite == "background"){
			if(choise == 1){
				return "iceThumbnail";
			}else if(choise == 2){
				return "desertThumbnail";
			}
		}
		
		return "nothing";
		
		
	}
	
	public void initializeTiledLayers() throws IOException{
		
		backgroundMenu = new TiledLayer(1, player.getUnlockedBackgrounds() + 2, Image.createImage("/omabutton.png"), 97, 34);
		backgroundMenu.setPosition(18, 58);
		backgroundMenu.setVisible(false);
		
		backgroundThumbs = new TiledLayer(1, player.getUnlockedBackgrounds(), Image.createImage("/thumbs.png"), 87, 34);
		backgroundThumbs.setPosition(23, 91);
		backgroundThumbs.setVisible(false);
		
		chosenBackground = new TiledLayer(1, 1, Image.createImage("/chosenThumb.png"), 87, 29);
		chosenBackground.setPosition(23, 54);
		chosenBackground.setCell(0, 0, Integer.parseInt(recoverRecord(1)));
		
		fillCells(backgroundMenu, backgroundThumbs);
		
		characterMenu = new TiledLayer(1, player.getUnlockedCharacters() + 2, Image.createImage("/omabutton.png"), 97, 34);
		characterMenu.setPosition(124 ,58);
		characterMenu.setVisible(false);
		
		characterThumbs = new TiledLayer(1, player.getUnlockedCharacters(), Image.createImage("/charthumbs.png"), 87, 34);
		characterThumbs.setPosition(129, 91);
		characterThumbs.setVisible(false);
		
		chosenCharacter = new TiledLayer(1, 1, Image.createImage("/chosenChar.png"), 87, 29);
		chosenCharacter.setPosition(129, 54);
		chosenCharacter.setCell(0, 0, Integer.parseInt(recoverRecord(2)));
		
		fillCells(characterMenu, characterThumbs);
		
		difficultyMenu = new TiledLayer(1, 4 + 1, Image.createImage("/difficultyMenu.png"), 97, 28);
		difficultyMenu.setPosition(124 ,112);
		difficultyMenu.setVisible(false);
		
		difficultyThumbs = new TiledLayer(1, 4, Image.createImage("/arena_difficultythumbs.png"), 87, 21);
		difficultyThumbs.setPosition(129, 138);
		difficultyThumbs.setVisible(false);
		
		chosenDifficulty = new TiledLayer(1, 1, Image.createImage("/arena_difficultythumbs.png"), 87, 21);
		chosenDifficulty.setPosition(128, 107);
		chosenDifficulty.setCell(0, 0, Integer.parseInt(recoverRecord(3)));
		
		fillCells(difficultyMenu, difficultyThumbs);
	}
	
	public void fillCells(TiledLayer layer, TiledLayer topLayer){
		
		int number;
		int maxAmount; //The max amount of graphics all together
		int menuType;
		
		if(layer == backgroundMenu){
			
			number = player.getUnlockedBackgrounds() - 1; //Remove the ice field which is always unlocked
			maxAmount = 5;
			menuType = 1;
			pressableBackgroundButtons = new int[player.getUnlockedBackgrounds()];
			pressableBackgroundButtons[0] = 1;
		}else if(layer == characterMenu){
			number = player.getUnlockedCharacters() - 1; //Remove the ice field which is always unlocked
			maxAmount = 5;
			menuType = 2;
			pressableCharacterButtons = new int[player.getUnlockedCharacters()];
			pressableCharacterButtons[0] = 1;
		}else{
			number = 3;
			maxAmount = 4;
			menuType = 3;
		}
		
		
		
		layer.setCell(0, 0, 3);
		layer.setCell(0, 1, 1); //Ice field background always
		topLayer.setCell(0, 0, 1); //Ice field thumbnail always
		
		
		int booleanIndex = 1;
		int thumbnailIndex = 2;
		
		if(layer == backgroundMenu || layer == characterMenu){
			for(int i = 1; i < maxAmount; i++){
				if(player.getMenuBoolean(menuType, booleanIndex)){
					layer.setCell(0, i + 1, 1);
					topLayer.setCell(0, i, thumbnailIndex);
					if(layer == backgroundMenu){
						pressableBackgroundButtons[i] = thumbnailIndex;
					}else if(layer == characterMenu){
						pressableCharacterButtons[i] = thumbnailIndex;
					}
				}else{
					i--;
					maxAmount--;
				}
				booleanIndex++;
				thumbnailIndex++;
			}
			
			layer.setCell(0, number + 2, 2);
		}else{
			for(int i = 1; i < maxAmount; i++){
				layer.setCell(0,i + 1,1);
				topLayer.setCell(0,i, i + 1);
			}
			
			layer.setCell(0, number + 1, 2);
		}
		
		
	}
	
	public void paintObjects(){
		
		spritesManager.paint(g, 0, 0);
		backgroundMenu.paint(g);
		backgroundThumbs.paint(g);
		chosenBackground.paint(g);
		characterMenu.paint(g);
		characterThumbs.paint(g);
		chosenCharacter.paint(g);
		difficultyMenu.paint(g);
		difficultyThumbs.paint(g);
		chosenDifficulty.paint(g);
		
		thumbnailsManager.paint(g,0,0);
		
		//g.drawString(player.recoverRecord(1), 0, 0, Graphics.TOP | Graphics.LEFT);
		
		flushGraphics();
	}
	
	
	
	public void pointerReleased(int x, int y){
		
		if(backgroundSelection == false && characterSelection == false && difficultySelection == false){
			if(x < 110 && x > 23 && y > 54 && y < 88){
				backgroundMenu.setVisible(true);
				backgroundThumbs.setVisible(true);
				backgroundSelection = true;
				paintObjects();
			}else if(x < 216 && x > 129 && y > 54 && y < 88){
				characterMenu.setVisible(true);
				characterThumbs.setVisible(true);
				characterSelection = true;
				chosenDifficulty.setVisible(false);
				paintObjects();
			}else if(x < 221 && x > 124 && y > 103 && y < 147){
				difficultyMenu.setVisible(true);
				difficultyThumbs.setVisible(true);
				difficultySelection = true;
				paintObjects();
			}else if(x > 98 && x < 115 && y > 170 && y < 195 && player.getFeaturesUnlocked()[0]){
				if(featuresSelections[0]){
					yes[0].setVisible(false);
					no[0].setVisible(true);
					featuresSelections[0] = false;
//					if(player.getFeaturesOnOff()[0]){
						player.setFeature(1, false);
//					}
				}else{
					yes[0].setVisible(true);
					no[0].setVisible(false);
					featuresSelections[0] = true;
					player.setFeature(1, true);
				}
				paintObjects();
			}else if(x > 98 && x < 115 && y > 188 && y < 213 && player.getFeaturesUnlocked()[1]){
				if(featuresSelections[1]){
					yes[1].setVisible(false);
					no[1].setVisible(true);
					featuresSelections[1] = false;
					player.setFeature(2, false);
				}else{
					yes[1].setVisible(true);
					no[0].setVisible(false);
					featuresSelections[1] = true;
					player.setFeature(2, true);
				}
				paintObjects();
			}else if(x > 98 && x < 115 && y > 205 && y < 230 && player.getFeaturesUnlocked()[2]){
				if(featuresSelections[2]){
					yes[2].setVisible(false);
					no[2].setVisible(true);
					featuresSelections[2] = false;
					player.setFeature(3, false);
				}else{
					yes[2].setVisible(true);
					no[2].setVisible(false);
					featuresSelections[2] = true;
					player.setFeature(3, true);
				}
				paintObjects();
			}else if(x > 191 && x < 208 && y > 170 && y < 195 && player.getFeaturesUnlocked()[3]){
				if(featuresSelections[3]){
					yes[3].setVisible(false);
					no[3].setVisible(true);
					featuresSelections[3] = false;
					player.setFeature(4, false);
				}else{
					yes[3].setVisible(true);
					no[3].setVisible(false);
					featuresSelections[3] = true;
					player.setFeature(4, true);
				}
				paintObjects();
			}else if(x > 191 && x < 208 && y > 188 && y < 213 && player.getFeaturesUnlocked()[4]){
				if(featuresSelections[4]){
					yes[4].setVisible(false);
					no[4].setVisible(true);
					featuresSelections[4] = false;
					player.setFeature(5, false);
				}else{
					yes[4].setVisible(true);
					no[4].setVisible(false);
					featuresSelections[4] = true;
					player.setFeature(5, true);
				}
				paintObjects();
			}else if(x > 191 && x < 208 && y > 205 && y < 230 && player.getFeaturesUnlocked()[5]){
				if(featuresSelections[5]){
					yes[0].setVisible(false);
					yes[1].setVisible(false);
					yes[2].setVisible(false);
					yes[3].setVisible(false);
					yes[4].setVisible(false);
					yes[5].setVisible(false);
					no[0].setVisible(true);
					no[1].setVisible(true);
					no[2].setVisible(true);
					no[3].setVisible(true);
					no[4].setVisible(true);
					no[5].setVisible(true);
					featuresSelections[5] = false;
					player.setFeature(1, false);
					player.setFeature(2, false);
					player.setFeature(3, false);
					player.setFeature(4, false);
					player.setFeature(5, false);
					player.setFeature(6, false);
				}else{
					yes[0].setVisible(true);
					yes[1].setVisible(true);
					yes[2].setVisible(true);
					yes[3].setVisible(true);
					yes[4].setVisible(true);
					yes[5].setVisible(true);
					no[0].setVisible(false);
					no[1].setVisible(false);
					no[2].setVisible(false);
					no[3].setVisible(false);
					no[4].setVisible(false);
					no[5].setVisible(false);
					featuresSelections[5] = true;
					player.setFeature(1, true);
					player.setFeature(2, true);
					player.setFeature(3, true);
					player.setFeature(4, true);
					player.setFeature(5, true);
					player.setFeature(6, true);
				}
				paintObjects();
			}else if(y > 250){ //START GAME!!!!!
				saveArenaRecords();
				openRecordStore();
				gameCanvas = new MyGameCanvas(display, title, player, Integer.parseInt(recoverRecord(1)), Integer.parseInt(recoverRecord(2)), Integer.parseInt(recoverRecord(3))); ///////TEMPORARY DIFFICULTY IS NUMBER 1 (EASY)
				display.setCurrent(gameCanvas);
				gameCanvas.startGame();
				title.nullArenaOptions();
				closeRecordStore();
			}
		}else{ 
			if(backgroundSelection){ //BACKGROUND MENU
				if(x < 110 && x > 23 && y > 94 && y < 94 + 29){
					chosenBackground.setCell(0,0,pressableBackgroundButtons[0]);
					player.setBackground(pressableBackgroundButtons[0]);
				}else if(player.getUnlockedBackgrounds() >= 2 && x < 110 && x > 23 
						&& y > 128 && y < 157){
					chosenBackground.setCell(0, 0, pressableBackgroundButtons[1]);
					player.setBackground(pressableBackgroundButtons[1]);
				}else if(player.getUnlockedBackgrounds() >= 3 && x < 110 && x > 23 
						&& y > 162 && y < 191){
					chosenBackground.setCell(0, 0, pressableBackgroundButtons[2]);
					player.setBackground(pressableBackgroundButtons[2]);
				}else if(player.getUnlockedBackgrounds() >= 4 && x < 110 && x > 23 
						&& y > 196 && y < 225){
					chosenBackground.setCell(0, 0, pressableBackgroundButtons[3]);
					player.setBackground(pressableBackgroundButtons[3]);
				}else if(player.getUnlockedBackgrounds() >= 5 && x < 110 && x > 23 
						&& y > 230 && y < 259){
					chosenBackground.setCell(0, 0, pressableBackgroundButtons[4]);
					player.setBackground(pressableBackgroundButtons[4]);
				}
			}else if(characterSelection){ //CHARACTER MENU
				if(x < 216 && x > 129 && y > 94 && y < 94 + 29){
					chosenCharacter.setCell(0,0,pressableCharacterButtons[0]);
					player.setCharacter(pressableCharacterButtons[0]);
				}else if(player.getUnlockedCharacters() >= 2 && x < 216 && x > 129 
						&& y > 128 && y < 157){
					chosenCharacter.setCell(0, 0, pressableCharacterButtons[1]);
					player.setCharacter(pressableCharacterButtons[1]);
				}else if(player.getUnlockedCharacters() >= 3 && x < 216 && x > 129 
						&& y > 162 && y < 191){
					chosenCharacter.setCell(0, 0, pressableCharacterButtons[2]);
					player.setCharacter(pressableCharacterButtons[2]);
				}else if(player.getUnlockedCharacters() >= 4 && x < 216 && x > 129 
						&& y > 196 && y < 225){
					chosenCharacter.setCell(0, 0, pressableCharacterButtons[3]);
					player.setCharacter(pressableCharacterButtons[3]);
				}else if(player.getUnlockedCharacters() >= 5 && x < 216 && x > 129 
						&& y > 230 && y < 259){
					chosenCharacter.setCell(0, 0, pressableCharacterButtons[4]);
					player.setCharacter(pressableCharacterButtons[4]);
				}
			}else if(difficultySelection){ //DIFFICULTY MENU 
				if(x < 221 && x > 124 && y > 139 && y < 162){
					chosenDifficulty.setCell(0,0,1);
					
					
				}else if(x < 221 && x > 124 && y > 162 && y < 185){
					chosenDifficulty.setCell(0, 0, 2);
					
					
				}else if(x < 221 && x > 124 && y > 185 && y < 210){
					chosenDifficulty.setCell(0, 0, 3);
					
					
				}else if(x < 221 && x > 124 && y > 210 && y < 233){
					chosenDifficulty.setCell(0, 0, 4);
					
					
				}
			}
			chosenDifficulty.setVisible(true);
			backgroundMenu.setVisible(false);
			backgroundThumbs.setVisible(false);
			backgroundSelection = false;
			characterMenu.setVisible(false);
			characterThumbs.setVisible(false);
			characterSelection = false;
			difficultyMenu.setVisible(false);
			difficultyThumbs.setVisible(false);
			difficultySelection = false;
			paintObjects();		
			
		}
	}


	public void commandAction(Command arg0, Displayable arg1) {
		// TODO Auto-generated method stub
		saveArenaRecords();
//		closeRecordStore();
		display.setCurrent(title);
		title.nullArenaOptions();
	}
	
	public void openRecordStore(){
		try {
            rs = RecordStore.openRecordStore("ArenaRecords", true);
            System.out.println("Arena RecordStore opened!");
           
        } catch (RecordStoreException e) {
            System.out.println("Arena Records Problem..." + e);
            
        }
	}
	
	public void saveArenaRecords(){
		openRecordStore();
		changePlayerRecord(1, Integer.toString(chosenBackground.getCell(0, 0))); //BACKGROUND
		changePlayerRecord(2, Integer.toString(chosenCharacter.getCell(0, 0))); //CHARACTER
		changePlayerRecord(3, Integer.toString(chosenDifficulty.getCell(0, 0))); //DIFFICULTY
		
//		if(featuresSelections[0]){
//			changePlayerRecord(4, "true"); //FEATURE 1
//		}else{
//			changePlayerRecord(4, "false");
//		}
		
		int recordID = 4;
		for(int i = 0; i < 6; i++){
			//System.out.println("" + recordID + player.getFeaturesOnOff()[i]);
			if(player.getFeaturesOnOff()[i] == true){ //FEATURES 1-6
				changePlayerRecord(recordID, "1");
			}else{
				changePlayerRecord(recordID, "0");
			}
			recordID++;
		}
		closeRecordStore();
	}
	
	
	public void changePlayerRecord(int recordID, String savedRecord){
		try {
            // Save
            
			//System.out.println("tallennetaan " + rs.getNumRecords() + "ja" + recordID);
			
            byte[] tb = savedRecord.getBytes(); //Change the String to bytes;
            if (rs.getNumRecords() < recordID) { //Check if the record on this ID exists already
                rs.addRecord(tb, 0, tb.length); //Create a new record for this item.
                
            } else {
                rs.setRecord(recordID, tb, 0, tb.length); //Change the record
                
            }

        } catch (RecordStoreException e) {
            System.out.println("Saving Arena record problem..." + e);
        }
	}
	
	public String recoverRecord(int recordID){
		byte[] fromrecord;
		String string = "";
		try {
			fromrecord = rs.getRecord(recordID);
			string = new String(fromrecord);
			
			
		}catch (RecordStoreException e) {
			System.out.println("Recovering Arena record problem..." + e);
		}
		
		return string;
	}
	
	public void closeRecordStore(){
		try {
            if (rs != null) {
                rs.closeRecordStore();
                System.out.println("Arena RecordStore closed!");
//                rs.deleteRecordStore("PlayerRecords"); //Kun tarttee poistaa recordit
            }
        } catch (RecordStoreException e) {
            System.out.println("Closing Arena RecordStore Problem..." + e);
        }
	}
	
	public void deleteArenaRecord(){
		
		try{
			rs.closeRecordStore();
			System.out.println("Arena RecordStore closed!");
		} catch (RecordStoreException e) {
	        System.out.println("Closing/Deleting Arena RecordStore Problem..." + e);
	    }
		
		try{
			
			rs.deleteRecordStore("ArenaRecords");
			System.out.println("Arena Record deleted");
		} catch (RecordStoreException e) {
	        System.out.println("deleting Arena RecordStore Problem..." + e);
	    }
	}
	
	public void initializeRecordStore(){
		try{
			//re.deleteRecordStore("ArenaRecords");
			openRecordStore();
			System.out.println("Arena rs.getNumRecords() = " + rs.getNumRecords());
			if(rs.getNumRecords() == 0){
				initializeRecords();
			}
		} catch (RecordStoreException e) {
	        System.out.println("Arena Records Problem..." + e);
	    }
	}
	
}
