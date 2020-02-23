package fi.tamk.project.heatmion;

import java.io.IOException;
import java.util.Random;

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

/**
 * Prints the game on the screen.
 * <p>
 * Prints the game without any features which will be brought from other
 * methods.
 * 
 * @author Tom Tilli
 * @version 180314
 * @since
 * 
 */
public class MyGameCanvas extends GameCanvas implements Runnable,
		CommandListener {

	private volatile boolean hey;
//	private boolean timerCreated;
	
	// Game booleans
	private boolean IS_COUNTDOWN_ON;
	private boolean IS_GAME_ON;
	private boolean isGamePaused;
	// private boolean newRound;
	private boolean NEXT_LEVEL;
	private boolean scoreScreenVisible;
//	private boolean timeOut;
	private int GAMEMODE; // 1 = story mode, 2 = arena mode, 3 = custom mode

	private boolean COLOR_VISIBLE = false;
//	private int numero;

	private Command back;
	private Display display;
	private TitleScreen title;
//	private ArenaOptions arenaOptions;
	private ScoreLoop scoreScreen;

	private Thread gameloop;

	// Sprite stuff
	private Sprite background;
	private int backgroundPosX;
	private int backgroundPosY;
	private Graphics g;
	private Sprite playerSprite;
	private int playerPosX;
	private int playerPosY;
	private Sprite loseAnimationStart;
	private Sprite loseAnimation;
	private boolean animationSlowener;
	private Sprite idleAnimation;
	private int animaatioaika;
	private int idleaika;
	private Sprite winAnimation;
	private int startTime;

	// Layer managers
	private LayerManager manager;
	private LayerManager infoManager;
	// private LayerManager scoreManager;

	private Sprite[] answerSquares;
	private Sprite redBox;
	private Sprite greenBox;
	private Sprite questionBox;
	private Sprite additionalBox;
	private Sprite pointsBox;
	private Sprite timeBox;

	private Sprite exitScreen;
	private boolean backPressed;
	
	private Sprite infoScreen;
	private Sprite countdown;
	private int countdownCounter;

	private int stageNumber;
	private int worldNumber;
	private int characterNumber;
	// LUMIHIUTALEITA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private Sprite fallingObject1;
	private Sprite fallingObject2;
	private Sprite fallingObject3;
	private Sprite fallingObject4;
	private int object1X;
	private int object2X;
	private int object3X;
	private int object4X;
	private int object1Y;
	private int object2Y;
	private int object3Y;
	private int object4Y;
	private Random rndSeed;
	// LUMIHIUTALEITA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	private TiledLayer answerBox1;
	private TiledLayer answerBox2;
	private TiledLayer answerBox3;
	private TiledLayer question;
	private TiledLayer questionNextRow;
	private TiledLayer answerTimeBox;
	private int numberSpriteHeight;
	private int numberSpriteWidth;
	private int smallNumberSpriteHeight;
	private int smallNumberSpriteWidth;
	private char[] questionBeginning;

	private int gamePoints;
	private int pointsFromCorrectAnswer;
	private int rightAnswersAmount;
	private int correctForNextStage;
	

	private boolean pressingAllowed;
	private int pauseTime;
	private int addedPauseTime;
	// private int

	private MyPlayer player;
	private SpeedMode mode;
	private Background world;

	// FEATURE THINGS
	private Feature features;
	// answering time things
	private int answeringTime;
	private boolean answeringTimeOn = true;
	private char[] answeringTimeArray;
	// survival things
	private int survivalBonus;
	private int survivalBonusWidth;
	// Combos things
	private int correctCombo;
//	private int correctComboBonus = 5; //Not used. Was supposed to be a seperate point system.
	private int failCombo;
	private int failComboBonus;
	private Sprite comboBoxLeft;
	private Sprite comboBoxRight;
	//jackpot things
	private TiledLayer jackpotBulbs;
	private int jackpotAnimation;
	private int bulbsOn;
//	private boolean bulbFalse;
	private int jackpotColor;
	//SuddenDeath
	private Sprite suddenDeathScreen;
	private boolean suddenDeathScreenOn;
	private Sprite suddenDeathSad;
	private Sprite suddenDeathHappy;
	private int suddenDeathChoise;
	
	//Endgame
	private int timeToComplete;
	private int endCorrectAnswers;
	private int endWrongAnswers;
	private int endPointsGathered;

	private Timer timer;
	
	//Arena stuff
	private int difficulty;
	private int[] arenaStartNumbers;
//	private int[] arenaStartAdditions;
	private int[] arenaAdditions;
	private int questionNumber;

	// //STORY MODE//////////////////////////////
	public MyGameCanvas(Display display, TitleScreen title, MyPlayer player) {
		super(true);

		this.player = player;
		this.display = display;
		this.title = title;
		GAMEMODE = 1;
		IS_GAME_ON = false;
		isGamePaused = false;
		backPressed = false;
		// newRound = true;
		
		backgroundPosX = 0;
		backgroundPosY = -10;// -85;
		playerPosX = (getWidth() / 2) - 34;
		playerPosY = (getHeight() / 2) + 40;
		
		animaatioaika = 21;
		idleaika = 0;
		pointsFromCorrectAnswer = 2;
		correctForNextStage = 5;
		rightAnswersAmount = 0;
		stageNumber = 1;
		worldNumber = 1;
		characterNumber = 1;
		suddenDeathScreenOn = false;
		player.setFeature(1, false);
		player.setFeature(2, false);
		player.setFeature(3, false);
		player.setFeature(4, false);
		player.setFeature(5, false);
		
		player.giveDifficulty(0);
		
		gamePoints = 0;
		endCorrectAnswers = 0;
		endWrongAnswers = 0;
//		timerCreated = false;

		scoreScreenVisible = false;
		pressingAllowed = true;

		back = new Command("back", Command.BACK, 0);
		this.addCommand(back);
		this.setCommandListener(this);

	}

	// //////////ARENA MODE///////////////////////////////
	public MyGameCanvas(Display display, TitleScreen title, MyPlayer player,
			int world, int character, int difficulty) {
		super(true);

		this.player = player;
		this.display = display;
		this.title = title;
//		this.arenaOptions = arenaOptions;
		GAMEMODE = 2;
		IS_GAME_ON = false;
		isGamePaused = false;
		backPressed = false;

		backgroundPosX = 0;
		backgroundPosY = -10;// -85;
		playerPosX = (getWidth() / 2) - 34;
		playerPosY = (getHeight() / 2) + 40;
		
		animaatioaika = 21;
		idleaika = 0;
		//This determines how many points you get from a single correct answer.
		switch(difficulty){
		case 1: //EASY
			pointsFromCorrectAnswer = 2;
			break;
		case 2: //MEDIUM
			pointsFromCorrectAnswer = 3;
			break;
		case 3: //HARD
			pointsFromCorrectAnswer = 4;
			break;
		case 4: //EXPERT
			pointsFromCorrectAnswer = 5;
			break;
		}
		
		player.giveDifficulty(difficulty);
		
		correctForNextStage = 5; /////SAMA 5 KUIN STORYSSA?
		rightAnswersAmount = 0;
		stageNumber = 1;
		questionNumber = 1;
		suddenDeathScreenOn = false;
		suddenDeathChoise = 0;
//		timerCreated = false;
		
		this.difficulty = difficulty;
		worldNumber = world;
		characterNumber = character;
		
		setStartArenaNumbers(difficulty);

		// MUUTETTAVA NIIN ETTEI NOLLAANNU SEURAAVASSA
		// TASOSSA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// NOLLAANTUUKO???? KYLLÄ!
		gamePoints = 0;

		scoreScreenVisible = false;
		pressingAllowed = true;

		back = new Command("back", Command.BACK, 0);
		this.addCommand(back);
		this.setCommandListener(this);

	}

	/**
	 * Starts game, gets graphics and paints the screen
	 * <p>
	 * Initializes everything needed for the gameloop and starts it.
	 * 
	 * @param player
	 *            contains necessary files
	 */
	// Start game, get graphics and paint the screen
	public void startGame() {
		g = getGraphics();

		player.setBackground(worldNumber);
		player.setCharacter(characterNumber);
		world = new Background(player.getBackground(), player.getCharacter());
		
		System.out.println("Active thread count: " + Thread.activeCount());
		
		player.setWrongAnswer(false);
		player.setTimeSpent(0); 
		player.resetTimesTheTimerRanOut();
		player.setStageReached(1);
		player.setCombo(0);
		player.resetExtraPoints();
		player.resetExtraTime();
		player.resetTimesLost();
		player.setStoryWorldNumber(1);
		

		
		try {
			initializeSprites();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer = new Timer(90, false);
		// timeOut = false;

		// FEATURE OPTIONS START
		// timedAnswer, survival, combos,
		features = new Feature(player.getFeaturesOnOff()[0],
				player.getFeaturesOnOff()[1], player.getFeaturesOnOff()[2], 
				player.getFeaturesOnOff()[3], player.getFeaturesOnOff()[4]);

		if (features.getTimedAnswer()) {
			if(GAMEMODE == 2){
				switch(difficulty){
				case 1:
					answeringTime = 10;
					break;
				case 2:
					answeringTime = 10;
					
					break;
				case 3:
					answeringTime = 10;
					break;
				case 4:
					answeringTime = 10;
					break;
				}
			}else{
				answeringTime = 10;
			}
			
			timer.setTimedAnswering(answeringTime);
			pointsFromCorrectAnswer++;
		}
		if (features.getSurvival()) {
			timer.setStartTime(20);
			if(GAMEMODE == 2){
				survivalBonus = 3;
			}else{
				survivalBonus = 5;
			}
			pointsFromCorrectAnswer++;
		}
		if(features.getCombos()){
			pointsFromCorrectAnswer--;
//			correctComboBonus = 5;
			failComboBonus = 15;
		}
		if(features.getJackpot()){
			
			jackpotAnimation = 4;
			bulbsOn = 0;
//			bulbFalse = false;
		}
		
		correctCombo = 0;
		failCombo = 0;

		// FEATURE OPTIONS END

		paintObjects();

		chooseMode(GAMEMODE);
		
//		if(GAMEMODE == 2 && stageNumber == 1){
//			mode.setFirstArenaQuestion(difficulty, arenaStartNumbers);
//		}
		mode.startMode(worldNumber, stageNumber, GAMEMODE);
		initializeTiledLayers();
		
		
		countdownCounter = 0;
		IS_COUNTDOWN_ON = true;
		// IS_GAME_ON = true;

		

		gameloop = new Thread(this);
		gameloop.start();

	}

	/**
	 * Initializes the sprites to be used on the game screen.
	 * <p>
	 * 
	 * 
	 * 
	 * @throws IOException
	 */

	public void initializeSprites() throws IOException {

		answerSquares = new Sprite[3];
		manager = new LayerManager();
		infoManager = new LayerManager();
		// scoreManager = new LayerManager();
		
		
		
		background = new Sprite(Image.createImage("/"
				+ world.getBackgroundGraphic() + ".png"));
		background.setPosition(backgroundPosX, backgroundPosY);

		playerSprite = new Sprite(Image.createImage("/" + world.getCharacter()
				+ ".png"));
		playerSprite.setPosition(playerPosX, playerPosY);

		idleAnimation = new Sprite(Image.createImage("/"
				+ world.getIdleAnimation() + ".png"), 72, 72);
		idleAnimation.setPosition(playerPosX, playerPosY);

		loseAnimationStart = new Sprite(Image.createImage("/"
				+ world.getLoseAnimationStart() + ".png"), 72, 72);
		loseAnimationStart.setPosition(playerPosX, playerPosY);

		loseAnimation = new Sprite(Image.createImage("/"
				+ world.getLoseAnimation() + ".png"), 72, 72);
		loseAnimation.setPosition(playerPosX, playerPosY);

		winAnimation = new Sprite(Image.createImage("/"
				+ world.getWinAnimation() + ".png"), 72, 72);
		winAnimation.setPosition(playerPosX, playerPosY);

		countdown = new Sprite(Image.createImage("/" + world.getCountdown()
				+ ".png"), 48, 71);
		countdown.setPosition((getWidth() / 2) - 24, (getHeight() / 2) - 36);

		questionBox = new Sprite(Image.createImage("/" + world.getQuestionBox() + ".png"));
		questionBox.setPosition((getWidth() / 2) - 95, (getHeight() / 2) - (94));
		
		
		additionalBox = new Sprite(Image.createImage("/" + world.getQuestionBox() + ".png"));
		additionalBox.setPosition((getWidth() / 2) - 95, (getHeight() / 2) - (94 - 14));
		additionalBox.setVisible(false);
		
		
		pointsBox = new Sprite(Image.createImage("/" + world.getPointsBox()
				+ ".png"));
		pointsBox.setPosition(getWidth() - pointsBox.getWidth(), 0);
		timeBox = new Sprite(Image.createImage("/" + world.getPointsBox()
				+ ".png"));

		redBox = new Sprite(Image.createImage("/" + world.getRedBox() + ".png"));
		manager.append(redBox);
		redBox.setVisible(false);
		greenBox = new Sprite(Image.createImage("/" + world.getGreenBox()
				+ ".png"));
		manager.append(greenBox);
		greenBox.setVisible(false);

		// LUMIHIUTALEITA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		object1X = 50;
		object2X = 100;
		object3X = 200;
		object4X = 0;
		object1Y = -200;
		object2Y = -100;
		object3Y = -20;
		object4Y = -50;
		
		fallingObject1 = new Sprite(Image.createImage("/"
				+ world.getFallingObjects() + ".png"));
		fallingObject1.setPosition(object1X, object1Y);
		fallingObject2 = new Sprite(Image.createImage("/"
				+ world.getFallingObjects() + ".png"));
		fallingObject2.setPosition(object2X, object2Y);
		fallingObject3 = new Sprite(Image.createImage("/"
				+ world.getFallingObjects() + ".png"));
		fallingObject3.setPosition(object3X, object3Y);
		fallingObject4 = new Sprite(Image.createImage("/"
				+ world.getFallingObjects() + ".png"));
		fallingObject4.setPosition(object4X, object4Y);
		// LUMIHIUTALEITA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


		// //INFOSCREEN////
		infoScreen = new Sprite(Image.createImage("/infosScreen.png"));
		infoScreen.setPosition(10, (getHeight() / 2) - 110);
		infoManager.append(infoScreen);
		// //INFOSCREEN/////
		
		//EXITSCREEN//
		exitScreen = new Sprite(Image.createImage("/game_exitscreen.png"));
		exitScreen.setPosition(0, -15);
		manager.append(exitScreen);
		exitScreen.setVisible(false);
		//EXITSCREEN//
		
		//COMBO//
		comboBoxLeft = new Sprite(Image.createImage("/combobox.png"));
		comboBoxLeft.setPosition(0, 23);
		
		
		
		comboBoxRight = new Sprite(Image.createImage("/combobox2.png"));
		comboBoxRight.setPosition(getWidth() - 42, 23);
		
		comboBoxLeft.setVisible(false);
		comboBoxRight.setVisible(false);
		
		//COMBO//
		
		//SUDDENDEATH
		suddenDeathSad = new Sprite(Image.createImage("/feature_sad.png"));
		manager.append(suddenDeathSad);
		suddenDeathSad.setVisible(false);
		
		suddenDeathHappy = new Sprite(Image.createImage("/feature_happy.png"));
		manager.append(suddenDeathHappy);
		suddenDeathHappy.setVisible(false);
		
		suddenDeathScreen = new Sprite(Image.createImage("/feature_tryagain.png"));
		suddenDeathScreen.setPosition(0, -15);
		manager.append(suddenDeathScreen);
		suddenDeathScreen.setVisible(false);
		
		//SUDDENDEATH
		
		manager.append(countdown);
		for (int i = 0; i < 3; i++) {
			answerSquares[i] = new Sprite(Image.createImage("/"
					+ world.getAnswerSquares() + ".png"));
			manager.append(answerSquares[i]);
		}
		answerSquares[0].setPosition(22, (getHeight() / 2) - 50);
		answerSquares[1].setPosition(92, (getHeight() / 2) - 50);
		answerSquares[2].setPosition(162, (getHeight() / 2) - 50);
		manager.append(questionBox);
		manager.append(additionalBox);
		manager.append(pointsBox);
		manager.append(timeBox);
		//COMBO//
		manager.append(comboBoxLeft);
		manager.append(comboBoxRight);
		//COMBO//
		// LUMIHIUTALEITA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		manager.append(fallingObject1);
		manager.append(fallingObject2);
		manager.append(fallingObject3);
		manager.append(fallingObject4);
		// LUMIHIUTALEITA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		manager.append(winAnimation);
		winAnimation.setVisible(false);
		manager.append(loseAnimationStart);
		loseAnimationStart.setVisible(false);
		manager.append(loseAnimation);
		loseAnimation.setVisible(false);
		manager.append(idleAnimation);
		idleAnimation.setVisible(false);
		manager.append(playerSprite);
		manager.append(background);

	}

	/**
	 * Initializes the titled layers for the game screen.
	 * 
	 */
	public void initializeTiledLayers() {

		numberSpriteHeight = 22;
		numberSpriteWidth = 15;
		smallNumberSpriteHeight = 18;
		smallNumberSpriteWidth = 10;

		try {
			answerBox1 = new TiledLayer(mode.getAnswerLength(0), 1,
					Image.createImage("/" + world.getSmallNumbers() + ".png"),
					smallNumberSpriteWidth, smallNumberSpriteHeight);
			answerBox1
					.setPosition(
							53 - ((mode.getAnswerLength(0) * smallNumberSpriteWidth) / 2),
							((getHeight() / 2) - 28));
			fillCells(answerBox1, mode.getAnswerAsChars(0));

			answerBox2 = new TiledLayer(mode.getAnswerLength(1), 1,Image.createImage("/" + world.getSmallNumbers() + ".png"),smallNumberSpriteWidth, smallNumberSpriteHeight);
			answerBox2.setPosition(123 - ((mode.getAnswerLength(1) * smallNumberSpriteWidth) / 2),
							((getHeight() / 2) - 28));
			fillCells(answerBox2, mode.getAnswerAsChars(1));

			answerBox3 = new TiledLayer(mode.getAnswerLength(2), 1,
					Image.createImage("/" + world.getSmallNumbers() + ".png"),
					smallNumberSpriteWidth, smallNumberSpriteHeight);
			answerBox3
					.setPosition(
							193 - ((mode.getAnswerLength(2) * smallNumberSpriteWidth) / 2),
							((getHeight() / 2) - 28));
			fillCells(answerBox3, mode.getAnswerAsChars(2));
			
			int firstQuestionBoxLength;
			/////////////////////////////////////////////////////////////////////////////////////////
			System.out.println(mode.getQuestionLength());
			if(mode.getQuestionLength() > 12){
				
				firstQuestionBoxLength = checkWhereToCutQuestion(mode.getQuestionAsChars());
				
				questionBeginning = new char[firstQuestionBoxLength];
				char[] restOfQuestion = new char[mode.getQuestionLength() - firstQuestionBoxLength];
				
					
					for(int i = 0; i < firstQuestionBoxLength; i++){
						questionBeginning[i] = mode.getQuestionAsChars()[i];
						
						
					}
					
					int number = 0;
					for(int i = firstQuestionBoxLength; i < mode.getQuestionLength(); i++){
						restOfQuestion[number] = mode.getQuestionAsChars()[i];
						
						
						number++;
					}
				
//				printStuff(mode.getQuestionAsChars());
				
//				printStuff(restOfQuestion);
				
				questionNextRow = new TiledLayer(mode.getQuestionLength() - firstQuestionBoxLength, 1,
						Image.createImage("/" + world.getNumbers() + ".png"), numberSpriteWidth, numberSpriteHeight);
				questionNextRow.setPosition((getWidth() / 2) - (mode.getQuestionLength() - firstQuestionBoxLength) * (numberSpriteWidth / 2), (getHeight() / 2) - 75);
				fillCells(questionNextRow, restOfQuestion);
			}else{
				firstQuestionBoxLength = mode.getQuestionLength();
			}
			
			question = new TiledLayer(firstQuestionBoxLength, 1,Image.createImage("/" + world.getNumbers() + ".png"), numberSpriteWidth, numberSpriteHeight);
			
			// Screenin leveydestäpuolet - title layerin leveydestä puolet
			if(mode.getQuestionLength() > 12){
				question.setPosition(((getWidth() / 2) - firstQuestionBoxLength * (numberSpriteWidth / 2)) - 2, (getHeight() / 2) - 98);
				fillCells(question, questionBeginning);
			}else{
				question.setPosition(((getWidth() / 2) - firstQuestionBoxLength * (numberSpriteWidth / 2)) - 2, (getHeight() / 2) - 90);
				fillCells(question, mode.getQuestionAsChars());
			}
			

			// Timed answering feature.
//			if (player.getFeaturesOnOff()[0]) {

				// answeringTimeArray = new
				// char[Integer.toString(timer.getTimedAnswering()).length()];
				// for(int i = 0; i <
				// Integer.toString(timer.getTimedAnswering()).length(); i++){
				// answeringTimeArray[i] =
				// Integer.toString(timer.getTimedAnswering()).charAt(i);
				// }

				// answerTimeBox = new
				// TiledLayer(Integer.toString(timer.getTimedAnswering()).length(),
				// 1, Image.createImage("/" + world.getNumbers() + ".png"),
				// numberSpriteWidth, numberSpriteHeight);
				// answerTimeBox.setPosition((getWidth() / 2) -
				// (answeringTimeArray.length / 2), 25);
				// fillCells(answerTimeBox, answeringTimeArray);
				// answerTimeBox.setCell(0, 0, 9);
//			}
			
			if(player.getFeaturesOnOff()[3] && bulbsOn == 0){
				jackpotBulbs = new TiledLayer(5, 1, Image.createImage("/feature_bulbs.png"), 24, 24);
				jackpotBulbs.setPosition(getWidth()/2 - 60 + 3, getHeight()/2 + 8);
				jackpotBulbs.setCell(0,0,1);
				jackpotBulbs.setCell(2,0,1);
				jackpotBulbs.setCell(4,0,1);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void fillCells(TiledLayer layer, char[] array) {
		for (int i = 0; i < array.length; i++) {
			
			switch (array[i]) {
			case '0':
				layer.setCell(i, 0, 1);
				break;
			case '1':
				layer.setCell(i, 0, 2);
				break;
			case '2':
				layer.setCell(i, 0, 3);
				break;
			case '3':
				layer.setCell(i, 0, 4);
				break;
			case '4':
				layer.setCell(i, 0, 5);
				break;
			case '5':
				layer.setCell(i, 0, 6);
				break;
			case '6':
				layer.setCell(i, 0, 7);
				break;
			case '7':
				layer.setCell(i, 0, 8);
				break;
			case '8':
				layer.setCell(i, 0, 9);
				break;
			case '9':
				layer.setCell(i, 0, 10);
				break;
			case '+':
				layer.setCell(i, 0, 11);
				break;
			case '-':
				layer.setCell(i, 0, 12);
				break;
			case '*':
				layer.setCell(i, 0, 13);
				break;
			case '/':
				layer.setCell(i, 0, 14);
				break;
			case '=':
				layer.setCell(i, 0, 15);
				break;
			case '?':
				layer.setCell(i, 0, 16);
				break;
			}
		}

		// x position on kohassa leveyden keskikohta miinus puolet tiled alyerin
		// pituudesta.
		// pituus = (mode.getQuestionLength()/2) * numberSpriteWidth (15)

	}

	/**
	 * Paints the objects for the screen.
	 * <p>
	 * Paints the main objects when the IS_GAME_ON boolean is false and includes
	 * the rest when it is true.
	 */
	public void paintObjects() {

		manager.paint(g, 0, 0);

		if (IS_GAME_ON && IS_COUNTDOWN_ON == false) {
			g.setColor(0, 0, 0);
			
			
			if (pressingAllowed) {
				if (animaatioaika > 18) {
					if (animaatioaika == 19) {
						idleaika = 0;
						animaatioaika++;
					}
					// System.out.println(idleaika);
					idleAnimation.setVisible(false);
					loseAnimation.setVisible(false);
					loseAnimationStart.setVisible(false);
					playerSprite.setVisible(true);
					idleaika++;
				}
				if (idleaika > 29) {
					if (idleaika == 30) {
						animaatioaika = 0;
						idleaika++;
					}
					// System.out.println(animaatioaika);
					playerSprite.setVisible(false);
					loseAnimation.setVisible(false);
					loseAnimationStart.setVisible(false);
					idleAnimation.setVisible(true);
					idleAnimation.nextFrame();
					animaatioaika++;
				}
			} else {
				playerSprite.setVisible(false);
				idleAnimation.setVisible(false);
			}

			answerBox1.paint(g);
			answerBox2.paint(g);
			answerBox3.paint(g);
			
			question.paint(g);
			if(mode.getQuestionLength() > 12){
				questionNextRow.paint(g);
				additionalBox.setVisible(true);
				questionBox.setPosition((getWidth() / 2) - 95, (getHeight() / 2) - (94 + 10));
			}else{
				additionalBox.setVisible(false);
				questionBox.setPosition((getWidth() / 2) - 95, (getHeight() / 2) - (94));
			}

			if (features.getTimedAnswer()) {
				try {
					answerTimeBox = new TiledLayer( Integer.toString(timer.getTimedAnswering()).length(), 1, Image.createImage("/" + world.getNumbers() + ".png"), numberSpriteWidth, numberSpriteHeight);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				answeringTimeArray = new char[Integer.toString(timer.getTimedAnswering()).length()];
				for (int i = 0; i < Integer.toString(timer.getTimedAnswering()).length(); i++) {answeringTimeArray[i] = Integer.toString(timer.getTimedAnswering()).charAt(i);
				}
				answerTimeBox.setPosition((getWidth() / 2) - ((answeringTimeArray.length * numberSpriteWidth) / 2), 25);
				fillCells(answerTimeBox, answeringTimeArray);
				// answerTimeBox.setCell(0, 0, timer.getTimedAnswering() + 1);
				answerTimeBox.paint(g);
				// g.drawString(Integer.toString(timer.getTimedAnswering()),
				// getWidth()/2, 20, Graphics.TOP | Graphics.HCENTER);
			}
			if(features.getCombos()){
				comboBoxLeft.setVisible(true);
				comboBoxRight.setVisible(true);
			}
			if(features.getJackpot()){
				jackpotBulbs.paint(g);
			}
			
		}

		if (IS_COUNTDOWN_ON) {
			countdown.nextFrame();
		}

		g.setColor(255, 255, 255); // white
		// g.fillRect(getWidth() - 50, 0, 50, 20); //(xPos, yPos, width, height)
		// g.fillRect(0, 0, 50, 20); //(xPos, yPos, width, height)

		g.drawString(("STAGE " + stageNumber), getWidth() / 2, 0, Graphics.TOP
				| Graphics.HCENTER);

		g.setColor(0, 0, 0); // black
		g.drawString(timer.getTimer(), 10, 2, Graphics.TOP | Graphics.LEFT);
		g.drawString(Integer.toString(gamePoints), getWidth() - 10, 2,
				Graphics.TOP | Graphics.RIGHT);
		
		if(features.getCombos() && IS_GAME_ON){
			g.setColor(139, 35, 35);
			g.drawString("x" + failCombo, 9, 27, Graphics.TOP | Graphics.LEFT);
			g.setColor(127, 255, 0);
			g.drawString("x" + correctCombo, getWidth() - 33, 27, Graphics.TOP | Graphics.LEFT);
		}
		
//		g.setColor(205,51,51);
		
		
		
		flushGraphics();

	}

	/**
	 * Runs the gameloop.
	 * 
	 */
	public void run(){
//		hey = true;
		
//		while(hey){
			
			
		while (IS_COUNTDOWN_ON) {
			
			// Thread paused here if isGamePaused is
			// true!!!!!!!!!!!!!!!!!!!!!!!!!
			checkIfPause();

			paintObjects();

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
//				Thread.currentThread().interrupt();
			}

			if (countdownCounter >= 2 && IS_COUNTDOWN_ON) {
				IS_COUNTDOWN_ON = false;
				IS_GAME_ON = true;

				countdown.setVisible(false);
			} else if (IS_COUNTDOWN_ON) {
				// countdown.nextFrame();

				countdownCounter++;
			}

		}

		while (IS_GAME_ON) {
			
			// Thread paused here if isGamePaused is
			// true!!!!!!!!!!!!!!!!!!!!!!!!!
			checkIfPause();
			
			if(features.getJackpot()){
				if(jackpotBulbs.getCell(0, 0) == 4 || jackpotBulbs.getCell(0, 0) == 5 || jackpotBulbs.getCell(0, 0) == 6){
					jackpotBulbs.setCell(0,0,jackpotAnimation);
				}
				if(jackpotBulbs.getCell(2, 0) == 4 || jackpotBulbs.getCell(2, 0) == 5 || jackpotBulbs.getCell(2, 0) == 6){
					jackpotBulbs.setCell(2,0,jackpotAnimation);
				}
				if(jackpotBulbs.getCell(4, 0) == 4 || jackpotBulbs.getCell(4, 0) == 5 || jackpotBulbs.getCell(4, 0) == 6){
					jackpotBulbs.setCell(4,0,jackpotAnimation);
				}
				jackpotAnimation++;
				if(jackpotAnimation == 7){
					jackpotAnimation = 4;
				}
			}
			
			
			
			// Happens when a correct answer is chosen and the block is changed
			// to a green color
			if (COLOR_VISIBLE) {

				if (features.getSurvival()) {
					g.setColor(0, 0, 0);
					
					g.drawString("+" + survivalBonus + " sec", survivalBonusWidth, (getHeight()/2) - 46, Graphics.TOP | Graphics.LEFT);
					flushGraphics();
					
				}

				pause();

				try {
					Thread.sleep(200);
				} catch (Exception e) {
//					Thread.currentThread().interrupt();
				}
				resume();
				
				greenBox.setVisible(false);
				COLOR_VISIBLE = false;
			}

			if (countdownCounter >= 2) {
				timer.startTimer();
//				timerCreated = true;
				countdownCounter = 0;
			}
			// LUMIHIUTALEITA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			if (worldNumber == 1) {
				moveFallingObjects();
			}
			// LUMIHIUTALEITA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			paintObjects();

			// If timer has reached zero then show the info screen.
			if (timer.timerOn() == false) {
				IS_GAME_ON = false;
				showScoreScreen("lose");
				// timeOut = true;
			}

			if (features.getTimedAnswer() && timer.getTimedAnswering() == 0
					&& answeringTimeOn) {
				
				player.setTimesTheTimeRanOut();
				timer.setTimedAnswering(0);
				pressingAllowed = false;
				pauseTime = 0;
				answeringTimeOn = false;
			}
			
			if (stageNumber == 6 && GAMEMODE == 1) { // NEXT WORLD ONLY IN STORY MODE
				// Move to next world
				System.out.println("Stage number " + stageNumber);
				worldNumber++;
				if(worldNumber == 2){ //world 1 = 2pts, world 2 = 4 pts, worlds 3 = 3pts, world 4 = 4 pts + bonus, world 5 = 5 pts + bonus
					pointsFromCorrectAnswer = 4;
				}else if(worldNumber == 3){
					pointsFromCorrectAnswer = 3;
				}else if(worldNumber == 4){
					pointsFromCorrectAnswer = 2;
				}else if(worldNumber == 5){
					pointsFromCorrectAnswer = 3;
				}

				characterNumber++;
				player.setFeature(worldNumber - 1, true);
				// /////////////////////
				stageNumber = 1;
				// NEXT_LEVEL = true;

				timer.stopTimer();

				winAnimation.setVisible(true);
				int winAnimationTime = 22;
				while (winAnimationTime > 0) {

					playerSprite.setVisible(false);
					idleAnimation.setVisible(false);
					paintObjects();
					
					winAnimation.nextFrame();
					winAnimationTime--;
					try {
						Thread.sleep(1000 / 15);
					} catch (Exception e) {
//						Thread.currentThread().interrupt();
					}
				}
				winAnimation.setVisible(false);
				IS_GAME_ON = false;
				showScoreScreen("win");
				// scoreScreenVisible = true;
				
				pressingAllowed = true;
				mode.nextStage(worldNumber, characterNumber);
				mode.startMode(worldNumber, stageNumber, GAMEMODE);
				initializeTiledLayers();
			}

			// Pause time for wrong answers.
			if (pressingAllowed == false) {
				pauseTime++;
				idleAnimation.setVisible(false);
				// playerSprite.setVisible(false);
				if (pauseTime < 2) {

					loseAnimationStart.setVisible(true);
					if (animationSlowener) {
						loseAnimationStart.nextFrame();
						animationSlowener = false;
					} else if (animationSlowener = false) {
						animationSlowener = true;
					}

				} else if (pauseTime > 14 + addedPauseTime) {
					loseAnimation.setVisible(false);
					loseAnimationStart.setVisible(true);
					loseAnimationStart.nextFrame();
					
				}else {
					loseAnimationStart.setVisible(false);
					loseAnimation.setVisible(true);
					loseAnimation.nextFrame();
					
				}
			}
			if (pauseTime > 15 + addedPauseTime) {
				pressingAllowed = true;

				redBox.setVisible(false);
				if (pauseTime < 17 + addedPauseTime) {
					playerSprite.setVisible(true);
					pauseTime = 18 + addedPauseTime;
				}
				loseAnimation.setVisible(false);
				loseAnimationStart.setVisible(false);
				
				if(suddenDeathScreenOn){
					suddenDeathScreen.setVisible(true);
					redBox.setVisible(false);
					manager.paint(g,0,0);
					flushGraphics();
					pause();
				}
				
				if (features.getTimedAnswer() && answeringTimeOn == false) {
					timer.setTimedAnswering(answeringTime);
					answeringTimeOn = true;
					if(features.getCombos()){
						correctCombo = 0;
					}
					if(features.getSuddenDeath()){
						suddenDeathScreenOn = true;
						player.setTimesLost();
					}
					
					mode.startMode(worldNumber, stageNumber, GAMEMODE);
					initializeTiledLayers();
				}
				
				
			}

			try {
				Thread.sleep(1000 / 30);
			} catch (Exception e) {
//				Thread.currentThread().interrupt();
			}

		}
		
//		}
		
	}

	/**
	 * Checks which button is pressed.
	 * <p>
	 * The button is pressed where the pointer is released. The method will
	 * check if the correct answer was pressed or not and responses accordingly.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.Canvas#pointerReleased(int, int)
	 */

	public void pointerReleased(int x, int y) {
		
		if(backPressed){
			if(x < 220 && x > 18 && y < 151 && y > 125){ //YES(EXIT)
				
				
				if (GAMEMODE == 1) {
					display.setCurrent(title);
					title.nullGameCanvas();
					cleanUp();
				} else {
					
					display.setCurrent(title);
					title.nullGameCanvas();
					cleanUp();
					//TODO Ei suostu vaihtamaan arenaoptionssiin. Ei ymmärrä mitä eroa titleen ja arenaoptionssiin vaihtamisil on. Ei onnistunu uudellee painttaamalla.
				}
				timer.stopTimer();
                IS_GAME_ON = false; 
			}else if(x < 220 && x > 18 && y < 227 && y > 175){ //NO(RESUME)
				backPressed = false;
				exitScreen.setVisible(false);
				if(pressingAllowed == false){
					redBox.setVisible(true);
				}
				manager.paint(g,0,0);
				resume();
			}
			
			
			
		}
		else if(suddenDeathScreenOn){ //SUDDEN DEATH SCREEN!!
			boolean variable = features.getRandomSuddenDeath();
			
			if(suddenDeathChoise > 0){
				if(suddenDeathChoise == 1){
					suddenDeathScreen.setVisible(false);
					suddenDeathHappy.setVisible(false);
					suddenDeathChoise = 0;
//					redBox.setVisible(true);
//					manager.paint(g,0,0);
//					pauseTime = pauseTime + addedPauseTime;
					mode.startMode(worldNumber, stageNumber, GAMEMODE);
					initializeTiledLayers();
					resume();
					suddenDeathScreenOn = false;
				}else if(suddenDeathChoise == 2){
					IS_GAME_ON = false;
					showScoreScreen("suddenLose");
				}
				
			}else if(x > 21 && x < 107 && y < 232 && y > 167){ //LEFTT CHOISE
				if(variable){
					System.out.println("YOU won");
					suddenDeathHappy.setVisible(true);
					suddenDeathHappy.setPosition(18, 161);
					manager.paint(g, 0, 0);
					flushGraphics();
					suddenDeathChoise = 1;
				}else{
					System.out.println("YOU LOST");
					suddenDeathSad.setVisible(true);
					suddenDeathSad.setPosition(18, 161);
					manager.paint(g, 0, 0);
					flushGraphics();
					suddenDeathChoise = 2;
				}
			}else if(x > 135 && x < 221 && y < 233 && y > 168){ //RIGHT CHOISE
				if(variable){
					System.out.println("YOU won");
					suddenDeathHappy.setVisible(true);
					suddenDeathHappy.setPosition(132, 161);
					manager.paint(g, 0, 0);
					flushGraphics();
					suddenDeathChoise = 1;
				}else{
					System.out.println("YOU LOST");
					suddenDeathSad.setVisible(true);
					suddenDeathSad.setPosition(132, 161);
					manager.paint(g, 0, 0);
					flushGraphics();
					suddenDeathChoise = 2;
				}
			}else if(x > 20 && x < 222 && y < 291 && y > 250){ //END GAME
				//HERE WHEN YOU SKIP AND GIVE UP
				System.out.println("GIVE UP");
				showScoreScreen("time");
			}
		}
		else if (pressingAllowed) {
			if (IS_GAME_ON) {

				if (y > (getHeight() / 2) - 40 && y < getHeight() / 2 + 15
						&& x > 22 && x < 77) { //LEFT
					System.out.println("Meni 1");

					if (mode.getAnswerArray()[0] == mode.getAnswerArray()[3]) {
						greenBox.setPosition(22, (getHeight() / 2) - 50);
						greenBox.setVisible(true);
//						numero = 8;
						survivalBonusWidth = (getWidth()/2) - 23 - 70;
						COLOR_VISIBLE = true;

						paintObjects();

						// g.drawString("YOU GOT IT RIGHT", getWidth()/2,
						// getHeight()/2, Graphics.BOTTOM | Graphics.HCENTER);

						gamePoints = gamePoints + pointsFromCorrectAnswer
								+ (correctCombo * pointsFromCorrectAnswer);

						// player.setScore(pointsFromCorrectAnswer); //TÄSSÄ
						// LISÄTÄÄN PISTEET SUORAAN PELAAJAN KOKONAIS PISTEISIIN

						rightAnswersAmount++;
						endCorrectAnswers++;

						// FEATURE OPTIONS START
						if (features.getTimedAnswer()) {
							timer.setTimedAnswering(answeringTime);
						}
						if (features.getSurvival()) {
							timer.setMoreTime(survivalBonus);
						}
						if (features.getCombos()) {
							correctCombo++;
							failCombo = 0;
							player.setCombo(correctCombo);
						}
						if (features.getJackpot()){
							
							lightABulp();
							
						}
						System.out.println("BULBS: " + bulbsOn);
						// FEATURE OPTIONS END

						if (GAMEMODE == 1) {
							if (rightAnswersAmount == correctForNextStage && stageNumber < 6) {
								stageNumber++;
								 // Story mode stages (Different for arena mode)
								if(stageNumber < 6){
									mode.nextStage(worldNumber, characterNumber);
									mode.startMode(worldNumber, stageNumber, GAMEMODE);
									initializeTiledLayers();
								}else{
									pressingAllowed = false;
								}

								rightAnswersAmount = 0;
								// IS_GAME_ON = false;
								// timer.stopTimer();
								// showInfo();
								
							}else if(rightAnswersAmount < 5){
								mode.startMode(worldNumber, stageNumber, GAMEMODE);
								initializeTiledLayers();
							}
						}else if(GAMEMODE == 2){
							questionNumber++;
							if(stageNumber % 5 == 0){
								mode.nextArenaStage(difficulty, questionNumber, arenaAdditions);
							}
							mode.giveNumbersAndAdditions(arenaStartNumbers, arenaAdditions);
							getNumbersForNextArenaStage();
//							mode.nextArenaQuestion(difficulty, arenaStartNumbers, arenaAdditions);
						
							mode.startMode(worldNumber, questionNumber, GAMEMODE);
							initializeTiledLayers();
						}
					} else {
						redBox.setPosition(22, (getHeight() / 2) - 50);
						redBox.setVisible(true);
						// COLOR_VISIBLE = true;
						animaatioaika = 21;
						idleaika = 0;

						// g.drawString("WRONG", getWidth()/2, getHeight()/2,
						// Graphics.BOTTOM | Graphics.HCENTER);
						pressingAllowed = false;
						pauseTime = 0;
						addedPauseTime = failCombo * failComboBonus;
						
						if(GAMEMODE == 1){
							player.setWrongAnswer(true);
						}
						
						endWrongAnswers++;
						
						// FEATURE OPTIONS START
						if (features.getCombos()) {
							failCombo++;
							correctCombo = 0;
						}
						if(bulbsOn == 3){
							bulbsOn = 4;
							if(jackpotBulbs.getCell(4, 0) == 3){
								if(gamePoints < 300){
									gamePoints = 0;
								}else{
									gamePoints = gamePoints - 300;
								}
							}
							jackpotBulbs.setCell(0,0,1);
							jackpotBulbs.setCell(2,0,1);
							jackpotBulbs.setCell(4,0,1);
							jackpotBulbs.paint(g);
							
						}
						if(features.getSuddenDeath()){
							
							suddenDeathScreenOn = true;
							player.setTimesLost();
							
						}
						// FEATURE OPTIONS END
					}

				} else if (y > (getHeight() / 2) - 40
						&& y < getHeight() / 2 + 15 && x > 92 && x < 147) { //MIDDLE
					System.out.println("Meni 2");

					if (mode.getAnswerArray()[1] == mode.getAnswerArray()[3]) {
						greenBox.setPosition(92, (getHeight() / 2) - 50);
						greenBox.setVisible(true);
//						numero = 8;
						survivalBonusWidth = (getWidth()/2) - 23;
						COLOR_VISIBLE = true;

						paintObjects();

						// g.drawString("YOU GOT IT RIGHT", getWidth()/2,
						// getHeight()/2, Graphics.BOTTOM | Graphics.HCENTER);

						gamePoints = gamePoints + pointsFromCorrectAnswer
								+ (correctCombo * pointsFromCorrectAnswer);

						// player.setScore(pointsFromCorrectAnswer); //TÄSSÄ
						// LISÄTÄÄN PISTEET SUORAAN PELAAJAN KOKONAIS PISTEISIIN

						rightAnswersAmount++;
						endCorrectAnswers++;

						// FEATURE OPTIONS START
						if (features.getTimedAnswer()) {
							timer.setTimedAnswering(answeringTime);
						}
						if (features.getSurvival()) {
							timer.setMoreTime(survivalBonus);
						}
						if (features.getCombos()) {
							correctCombo++;
							failCombo = 0;
							player.setCombo(correctCombo);
						}
						if (features.getJackpot()){
							
							lightABulp();
						}
						// FEATURE OPTIONS END

						if (GAMEMODE == 1) {
							if (rightAnswersAmount == correctForNextStage && stageNumber < 6) {
								stageNumber++;
								 // Story mode stages (Different for arena mode)
								if(stageNumber < 6){
									mode.nextStage(worldNumber, characterNumber);
									mode.startMode(worldNumber, stageNumber, GAMEMODE);
									initializeTiledLayers();
								}else{
									pressingAllowed = false;
								}
								

								rightAnswersAmount = 0;
								// IS_GAME_ON = false;
								// timer.stopTimer();
								// showInfo();
								
							}else if(rightAnswersAmount < 5){
								mode.startMode(worldNumber, stageNumber, GAMEMODE);
								initializeTiledLayers();
							}
						}else if(GAMEMODE == 2){
							questionNumber++;
							if(stageNumber % 5 == 0){
								mode.nextArenaStage(difficulty, questionNumber, arenaAdditions);
							}
							mode.giveNumbersAndAdditions(arenaStartNumbers, arenaAdditions);
							getNumbersForNextArenaStage();
//							mode.nextArenaQuestion(difficulty, arenaStartNumbers, arenaAdditions);
						
							mode.startMode(worldNumber, questionNumber, GAMEMODE);
							initializeTiledLayers();
						}
					} else {
						redBox.setPosition(92, (getHeight() / 2) - 50);
						redBox.setVisible(true);
						// COLOR_VISIBLE = true;
						animaatioaika = 21;
						idleaika = 0;

						// g.drawString("WRONG", getWidth()/2, getHeight()/2,
						// Graphics.BOTTOM | Graphics.HCENTER);
						pressingAllowed = false;
						pauseTime = 0;
						addedPauseTime = failCombo * failComboBonus; 

						if(GAMEMODE == 1){
							player.setWrongAnswer(true);
						}
						
						endWrongAnswers++;
						
						// FEATURE OPTIONS START
						if (features.getCombos()) {
							failCombo++;
							correctCombo = 0;
						}
						if(bulbsOn == 3){ //KOLMEN JÄLKEE VÄÄRÄ VASTAUS MUUTTAA PUNASEKS
							bulbsOn = 4;
							if(jackpotBulbs.getCell(4, 0) == 3){
								if(gamePoints < 300){
									gamePoints = 0;
								}else{
									gamePoints = gamePoints - 300;
								}
							}
							jackpotBulbs.setCell(0,0,1);
							jackpotBulbs.setCell(2,0,1);
							jackpotBulbs.setCell(4,0,1);
							jackpotBulbs.paint(g);
							
						}
						if(features.getSuddenDeath()){
//							suddenDeathScreen.setVisible(true);
//							redBox.setVisible(false);
//							manager.paint(g,0,0);
//							pause();
							suddenDeathScreenOn = true;
							player.setTimesLost();
						}
						// FEATURE OPTIONS END
					}

				} else if (y > (getHeight() / 2) - 40
						&& y < getHeight() / 2 + 15 && x > 162 && x < 217) {
					System.out.println("Meni 3");

					if (mode.getAnswerArray()[2] == mode.getAnswerArray()[3]) {
						greenBox.setPosition(162, (getHeight() / 2) - 50);
						greenBox.setVisible(true);
//						numero = 8;
						survivalBonusWidth = (getWidth()/2) - 23 + 70;
						COLOR_VISIBLE = true;

						paintObjects();

						// g.drawString("YOU GOT IT RIGHT", getWidth()/2,
						// getHeight()/2, Graphics.BOTTOM | Graphics.HCENTER);

						gamePoints = gamePoints + pointsFromCorrectAnswer
								+ (correctCombo * pointsFromCorrectAnswer);

						// player.setScore(pointsFromCorrectAnswer); //TÄSSÄ
						// LISÄTÄÄN PISTEET SUORAAN PELAAJAN KOKONAIS PISTEISIIN

						rightAnswersAmount++;
						endCorrectAnswers++;

						// FEATURE OPTIONS START
						if (features.getTimedAnswer()) {
							timer.setTimedAnswering(answeringTime);
						}
						if (features.getSurvival()) {
							timer.setMoreTime(survivalBonus);
						}
						if (features.getCombos()) {
							correctCombo++;
							failCombo = 0;
							player.setCombo(correctCombo);
						}
						if (features.getJackpot()){
							
							lightABulp();
						}
						// FEATURE OPTIONS END

						if (GAMEMODE == 1) { //When using story mode
							
							if (rightAnswersAmount == correctForNextStage && stageNumber < 6) {
								stageNumber++;
								 // Story mode stages (Different for arena mode)
								if(stageNumber < 6){
									mode.nextStage(worldNumber, characterNumber);
									mode.startMode(worldNumber, stageNumber, GAMEMODE);
									initializeTiledLayers();
								}else{
									pressingAllowed = false;
								}
								

								rightAnswersAmount = 0;
								// IS_GAME_ON = false;
								// timer.stopTimer();
								// showInfo();
								
							}else if(rightAnswersAmount < 5){
								mode.startMode(worldNumber, stageNumber, GAMEMODE);
								initializeTiledLayers();
							}
							
							
						} else if(GAMEMODE == 2){
							questionNumber++;
							if(stageNumber % 5 == 0){
								mode.nextArenaStage(difficulty, questionNumber, arenaAdditions);
							}
							
							mode.giveNumbersAndAdditions(arenaStartNumbers, arenaAdditions);
							getNumbersForNextArenaStage();
//							mode.nextArenaQuestion(difficulty, arenaStartNumbers, arenaAdditions);
						
							mode.startMode(worldNumber, questionNumber, GAMEMODE);
							
							initializeTiledLayers();
						}
					} else {
						redBox.setPosition(162, (getHeight() / 2) - 50);
						redBox.setVisible(true);
						// COLOR_VISIBLE = true;
						animaatioaika = 21;
						idleaika = 0;

						// g.drawString("WRONG", getWidth()/2, getHeight()/2,
						// Graphics.BOTTOM | Graphics.HCENTER);
						pressingAllowed = false;
						pauseTime = 0;
						addedPauseTime = failCombo * failComboBonus; 
						
						if(GAMEMODE == 1){
							player.setWrongAnswer(true);
						}
						
						endWrongAnswers++;
						
						// FEATURE OPTIONS START
						if (features.getCombos()) {
							failCombo++;
							correctCombo = 0;
						}
						if(bulbsOn == 3){
							bulbsOn = 4;
							if(jackpotBulbs.getCell(4, 0) == 3){
								if(gamePoints < 300){
									gamePoints = 0;
								}else{
									gamePoints = gamePoints - 300;
								}
							}
							jackpotBulbs.setCell(0,0,1);
							jackpotBulbs.setCell(2,0,1);
							jackpotBulbs.setCell(4,0,1);
							jackpotBulbs.paint(g);
							
						}
						if(features.getSuddenDeath()){
//							suddenDeathScreen.setVisible(true);
//							redBox.setVisible(false);
//							manager.paint(g,0,0);
//							pause();
							suddenDeathScreenOn = true;
							player.setTimesLost();
						}
						// FEATURE OPTIONS END
					}
				}
				flushGraphics();
			} else if (IS_GAME_ON == false && IS_COUNTDOWN_ON == false) {
				if (NEXT_LEVEL) {
					rightAnswersAmount = 0;
					startGame();

				}
				// else if(scoreScreenVisible && timeOut == false){
				// paintObjects();
				// showInfo();///Tähän seuraavan mapin grafiikkojen optionssit.
				// NEXT_LEVEL = true;
				//
				// }
				else {
					
					display.setCurrent(title);
					cleanUp();
					if(GAMEMODE == 2){
						title.nullArenaOptions();
					}
				}
			}
			
		}
	}

	/**
	 * 
	 * 
	 */
	public void chooseMode(int GAMEMODE) {

		switch (GAMEMODE) {
		case 1:
			mode = new SpeedMode();
			// sMode.startMode();
			break;
		case 2:
			mode = new SpeedMode(difficulty);
			break;
		}	

	}

	// public void chooseWorld(){
	//
	// switch(player.getBackground()){
	// case 1:
	// world = new SpeedMode();
	// //sMode.startMode();
	// break;
	// }
	//
	// }

	/**
	 * Shows the info Screen.
	 */
	public void showInfo() {
		IS_GAME_ON = false;
		NEXT_LEVEL = true;
		infoManager.paint(g, 0, 0);
//		(10, (getHeight() / 2) - 110);
		g.setColor(0,0,0);
		if(worldNumber == 2){
			g.drawString("RUSH", 98, 95, Graphics.TOP | Graphics.LEFT);
			g.drawString("Answer the questions", 40, 115, Graphics.TOP | Graphics.LEFT);
			g.drawString("fast. You only have a", 40, 135, Graphics.TOP | Graphics.LEFT);
			g.drawString("few seconds to do it.", 40, 155, Graphics.TOP | Graphics.LEFT);
		}else if(worldNumber == 3){
			g.drawString("SURVIVAL", 85, 95, Graphics.TOP | Graphics.LEFT);
			g.drawString("Your time is low but", 40, 115, Graphics.TOP | Graphics.LEFT);
			g.drawString("answer correctly to", 40, 135, Graphics.TOP | Graphics.LEFT);
			g.drawString("gain more.", 40, 155, Graphics.TOP | Graphics.LEFT);
		}else if(worldNumber == 4){
			g.drawString("COMBO", 91, 95, Graphics.TOP | Graphics.LEFT);
			g.drawString("Get multiple correct", 40, 115, Graphics.TOP | Graphics.LEFT);
			g.drawString("to gain more points.", 40, 135, Graphics.TOP | Graphics.LEFT);
			g.drawString("Just don't keep failing.", 40, 155, Graphics.TOP | Graphics.LEFT);
		}else if(worldNumber == 5){
			g.drawString("JACKPOT", 89, 95, Graphics.TOP | Graphics.LEFT);
			g.drawString("Green gives points,", 42, 115, Graphics.TOP | Graphics.LEFT);
			g.drawString("yellow gives time and", 42, 135, Graphics.TOP | Graphics.LEFT);
			g.drawString("red might remove some.", 42, 155, Graphics.TOP | Graphics.LEFT);
		}
		
		flushGraphics();
	}

	public void showScoreScreen(String text) {
		IS_GAME_ON = false;
		
		if(GAMEMODE == 2){
			player.setStageReached(stageNumber);
			player.setPointsGathered(gamePoints);
		}else if(GAMEMODE == 1){
			player.setTimeLeft(timer.getSeconds() + (timer.getMinutes() * 60));
			player.setTimeSpent(timer.getTimeSpent()); 
			player.setStoryWorldNumber(worldNumber);
			
			timeToComplete = timeToComplete + timer.getTimeSpent();
			endPointsGathered = endPointsGathered + gamePoints;
			
			
		}
		
		if (text == "win") {
			scoreScreen = new ScoreLoop(player.getScore(), gamePoints,
					timer.getSeconds() + (timer.getMinutes() * 60), "win",
					display, this, title, player);
		} else if (text == "lose") {
			scoreScreen = new ScoreLoop(player.getScore(), gamePoints / 2,
					0, "lose",
					display, this, title, player);
		} else if (text == "suddenLose"){
			scoreScreen = new ScoreLoop(player.getScore(), 0,
					0, "suddenLose",
					display, this, title, player);
		} else if (text == "time"){
			scoreScreen = new ScoreLoop(player.getScore(), gamePoints,
					timer.getSeconds() + (timer.getMinutes() * 60), "time",
					display, this, title, player);
		}
		if(worldNumber > 5){
			scoreScreen.getEndStats(timeToComplete, endCorrectAnswers, endWrongAnswers, endPointsGathered, worldNumber);
		}
		scoreScreen.startScoreScreen();
		
		display.setCurrent(scoreScreen);
	}

	/**
	 * 
	 * 
	 */
	public void commandAction(Command arg0, Displayable arg1) {
		if(backPressed == false){
			pause();
			backPressed = true;
			if(pressingAllowed == false){
				redBox.setVisible(false);
			}
			exitScreen.setVisible(true);
			manager.paint(g, 0, 0);
			flushGraphics();
		}else{
			
			if (GAMEMODE == 1) {
				display.setCurrent(title);
				
				cleanUp();
				title.nullGameCanvas();
			} else {
				display.setCurrent(title);
				
				
				
				cleanUp();
				//TODO Ei suostu vaihtamaan arenaoptionssiin. Ei ymmärrä mitä eroa titleen ja arenaoptionssiin vaihtamisil on. Ei onnistunu uudellee painttaamalla.
			}
			timer.stopTimer();
			IS_GAME_ON = false;
		}

	}

	// LUMIHIUTALEITA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public void moveFallingObjects() {
		rndSeed = new Random();

		object1Y = object1Y + 1;
		object2Y = object2Y + 1;
		object3Y = object3Y + 1;
		object4Y = object4Y + 1;

		if (object1Y >= 250) {
			object1X = (new Random(rndSeed.nextInt()).nextInt(50));
			object1Y = -20;
		}
		if (object2Y >= 250) {
			object2X = (new Random(rndSeed.nextInt()).nextInt(50)) + 60;
			object2Y = -20;
		}
		if (object3Y >= 250) {
			object3X = (new Random(rndSeed.nextInt()).nextInt(50)) + 110;
			object3Y = -20;
		}
		if (object4Y >= 250) {
			object4X = (new Random(rndSeed.nextInt()).nextInt(50)) + 160;
			object4Y = -20;
		}
		fallingObject1.setPosition(object1X, object1Y);
		fallingObject2.setPosition(object2X, object2Y);
		fallingObject3.setPosition(object3X, object3Y);
		fallingObject4.setPosition(object4X, object4Y);
	}
	
	public void setStartArenaNumbers(int difficulty){
		
		
		
		
		arenaStartNumbers = new int[] {10, 2, 10, 2, 4, 2, 4, 2};
//		plusMax = 10;
//		plusMin = 2;
//		minusMax = 10;
//		minusMin = 2;
//		multiMax = 4;
//		multiMin = 2;
//		diviMax = 3;
//		diviMin = 1;
		
		switch(difficulty){
		case 1:
			arenaAdditions = new int[] {2, 1, 2, 1, 0, 0, 0, 0};
			
			break;
		case 2:
			arenaAdditions = new int[] {4, 2, 4, 2, 0, 0, 0, 0};
			break;
		case 3:
			arenaAdditions = new int[] {5, 2, 5, 2, 0, 0, 0, 0};
			break;
		case 4:
			arenaAdditions = new int[] {10, 5, 10, 5, 0, 0, 0, 0};
			break;
		}
	}
	
	public void getNumbersForNextArenaStage(){
		
		
		for(int i = 0; i < arenaStartNumbers.length; i++){
			arenaStartNumbers[i] = mode.getNewNumbers()[i];
			
		}
	}
	
	public int checkWhereToCutQuestion(char[] array){
		
		
		for(int i = 12; i > 0; i--){
			if(array[i] == '+' || array[i] == '-' || array[i] == '*' || array[i] == '/'){
				return i;
			}
		}
		
		return 0;
	}
	
	// Game pause methods

	public void hideNotify() {
		System.out.println("Hide!");
//		if(backPressed == false){
			pause();
//		}
	}

	public void showNotify() {
		System.out.println("Resume!");
		resume();
	}

	public void pause() {
		
			isGamePaused = true;
			
			if (timer.timerOn()) {
				timer.setTimerOnWait();
			}
		
	}

	public void resume() {

		isGamePaused = false;
		if (timer.timerOn()) {
			timer.resumeTimer();
		}
		synchronized (this) {
			notify();
		}
	}

	private void checkIfPause() {
		synchronized (this) {
			while (isGamePaused) {
				try {
					wait();

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void printStuff(char[] array){
		for(int i = 0; i < array.length; i++){
			System.out.print(array[i]);
		}
		System.out.println();
	}
	
	public void lightABulp(){
		if (features.getJackpot()){
			
			
			if(bulbsOn == 3 || bulbsOn == 4){
				jackpotBulbs.setCell(0,0,1);
				jackpotBulbs.setCell(2,0,1);
				jackpotBulbs.setCell(4,0,1);
				bulbsOn = 0;
				switch(jackpotColor){ //PRIZE FROM CORRECT ANSWER
				case 2: //GREEN
					//LISÄPISTEITÄ
					gamePoints = gamePoints + 500;
					System.out.println("LISÄPISTEIT");
					player.setExtraPoints(500);
					break;
				case 3: //RED
					//EI MITÄÄN? VÄÄRÄLLÄ MENETTÄÄ AIKAA TAI PISTEITÄ
					System.out.println("PUNANEN");
					break;
				case 4: //YELLOW 1
					//LISÄAIKAA
//					gamePoints = gamePoints + 500;
//					player.setExtraPoints(500);
					timer.setMoreTime(30);
					player.setExtraTime(30);
					System.out.println("LISÄAIKAA");
					break;
				case 5: //YELLOW 2
					//LISÄAIKAA
//					gamePoints = gamePoints + 500;
					timer.setMoreTime(30);
					player.setExtraTime(30);
					System.out.println("LISÄAIKAA");
					break;
				case 6: //YELLOW 3
					//LISÄAIKAA
					gamePoints = gamePoints + 500;
					timer.setMoreTime(30);
					player.setExtraTime(30);
					System.out.println("LISÄAIKAA");
					break;
				}
				
				
			}
			else{
				int variable = features.getRandomJackpot(bulbsOn, jackpotBulbs.getCell(2, 0));
				
					switch(variable){
					case 1:
						switch(bulbsOn){ //GREEN
						case 0:
							jackpotBulbs.setCell(0,0,2);
							break;
						case 1:
							jackpotBulbs.setCell(2,0,2);
							break;
						case 2:
							jackpotBulbs.setCell(4,0,2);
							break;
						}
						break;
					case 2:
						switch(bulbsOn){ //RED
						case 0:
							jackpotBulbs.setCell(0,0,3);
							break;
						case 1:
							jackpotBulbs.setCell(2,0,3);
							break;
						case 2:
							jackpotBulbs.setCell(4,0,3);
							break;
						}
						break;
					case 3:
						switch(bulbsOn){ //YELLOW
						case 0:
							jackpotBulbs.setCell(0,0,4);
							break;
						case 1:
							jackpotBulbs.setCell(2,0,4);
							break;
						case 2:
							jackpotBulbs.setCell(4,0,4);
							break;
						}
						break;
					}
					
				
				bulbsOn++;
				
				if(bulbsOn == 2){
					int first = jackpotBulbs.getCell(0, 0);
					int second = jackpotBulbs.getCell(2, 0);
					
					if(first == second){
						//SKIP
					}else if(first < 7 && first > 3 && second < 7 && second > 3){
						//SKIP
					}else{
						bulbsOn = 0;
					}
				}
				
				if(bulbsOn == 3){
					int first = jackpotBulbs.getCell(0, 0);
					int second = jackpotBulbs.getCell(2, 0);
					int third = jackpotBulbs.getCell(4, 0);
					
					if(first == second){
						if(second == third){
							jackpotColor = third;
						}else if(first < 7 && first > 3 && second < 7 && second > 3 && third < 7 && third > 3){
							jackpotColor = third;
						}else{
							bulbsOn = 0;
						}
					}else if(first < 7 && first > 3 && second < 7 && second > 3 && third < 7 && third > 3){
						jackpotColor = third;
					}else{
						bulbsOn = 0;
					}
					
				}
				
				jackpotBulbs.paint(g);
			}
		}
	}
	
//	public void pointerPressed(int x, int y){
//		System.out.println("x = " + x + ", y = " + y);
//	}

	public void nullScoreScreen() {
		scoreScreen = null;
		
	}
	
	public void cleanUp(){
		IS_GAME_ON = false;
		IS_COUNTDOWN_ON = false;
//		hey = false;
//		gameloop.interrupt();
////		if(timerCreated){
////			timer.interruptLoop();
////			timer.nullTimer();
////			timer = null;
////		}
//		
//		isGamePaused = false;
//		scoreScreen = null;
////		features = null;
////		mode.nullMyMath();
////		mode = null;
//		
////		world = null; // Graphics
//		gameloop = null;
//		title.nullGameCanvas();
//		title.nullArenaOptions();
	}
	
}
