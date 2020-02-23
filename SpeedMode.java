package fi.tamk.project.heatmion;

import java.util.Random;

/**
 * 
 * @author Tom2
 *
 */
public class SpeedMode{

	private MyMath myMath;
	
	private int numberAmount; //the amount of numbers there will be in the question
	private int maxDistance;
	private int minDistance;
	
	
	private int wrongAnswer1;
	private int wrongAnswer2;
	private int[] answerArray;
	
	
	private char[] questionChars;
	private int charArrayLength;
	
	private Random rndSeed;
	
	
	private int difficulty;
	private int[] numbers;
	private int[] arenaAdditions;
	private boolean removeNegatives = true;
	
	public SpeedMode(){	
		maxDistance = 4; //t‰m‰ + MinDistance = luku kuinka kaukana vastauksesta v‰‰r‰t vastaukset voivat olla.
        minDistance = 2; //minimi kuinka l‰hell‰ v‰‰r‰t vastaukset voivat olla oikeasta.
		
	}
	public SpeedMode(int difficulty){
		maxDistance = 4; //t‰m‰ + MinDistance = luku kuinka kaukana vastauksesta v‰‰r‰t vastaukset voivat olla.
        minDistance = 2; //minimi kuinka l‰hell‰ v‰‰r‰t vastaukset voivat olla oikeasta.
        
        this.difficulty = difficulty;
        
	}
	
	/**
	 * 
	 * 
	 */
	public void startMode(int worldNumber, int stageNumber, int gameMode){
		
		rndSeed = new Random();
		
		
		
		System.out.println("STAGE = " + stageNumber);
			myMath = new MyMath();
			
			
			if(gameMode == 2){
				if(stageNumber == 1){
					setFirstArenaQuestion(difficulty);
				}else{
					nextArenaQuestion(difficulty);
				}
			}else if(gameMode == 1){
				
				nextStage(worldNumber, stageNumber);
			}
			numberAmount = myMath.getNumberAmount();
			
//			minusPlusSize = testi.getMinusPlusSize();
//			multiDiviSize = testi.getMultiDiviSize();
			
			myMath.calculationTypes();
			myMath.randomizeNumbers();
			myMath.calculateResults();

        
        /*randomoidaan v‰‰r‰t vastaukset metodissa randomWrongAnswer*/
		int[] wrongAnswersArray = new int[2];
		wrongAnswersArray = randomWrongAnswer(myMath.getAnswer());
        wrongAnswer1 = wrongAnswersArray[0];
        wrongAnswer2 = wrongAnswersArray[1];
        
//        do{
//            
//            wrongAnswer2 = randomWrongAnswer(testi.getAnswer())[1];
//            
//        }while(checkRandomSimilarity(wrongAnswer1, wrongAnswer2));
        
        
        //Printataan esille testej‰ varten
        
//        for(int i = 1;i < numberAmount - 2; i++){
//        	System.out.print(testi.getNumbersArray()[i] + " " +testi.getCalcTypesArray()[i]);
//        }
//        System.out.print(testi.getNumbersArray()[numberAmount - 1]);
//        
//        System.out.println(" = " + testi.getAnswer());
//        
//        System.out.println(wrongAnswer1 + " " + wrongAnswer2 +" " + testi.getAnswer());
        
        arrayAnswers(wrongAnswer1 ,wrongAnswer2, myMath.getAnswer());
        questionToArray();
        
	}
	/**
	 * Chooses the stage and world setup.
	 */
	public void nextStage(int worldNumber, int stageNumber){
		
//		public void setOptions(boolean plus, boolean minus, boolean multi, boolean divi, int numberAmount, int plusSize, 
//		int minPlusSize, int minusSize, int minMinusSize, int multiSize, int minMultiSize, int diviSize, int minDiviSize){
		
		switch(worldNumber){
		case 1:
			System.out.println("World 1 stage number in " + stageNumber);
			switch(stageNumber){ // no features
			case 1://2, 10, 2
				
				myMath.setOptions(true, false, false, false, 2, 9, 2, 10, 1, 0, 0, 0, 0);
				break;
			case 2:
				myMath.setOptions(false, true, false, false, 2, 0, 0, 9, 2, 0, 0, 0, 0);
				break;
			case 3:
				myMath.setOptions(false, false, true, false, 2, 0, 0, 0, 0, 4, 1, 0, 0);
				break;
			case 4:
				myMath.setOptions(false, false, false, true, 2, 0, 0, 0, 0, 0, 0, 4, 1); //6
				break;
			case 5:
				myMath.setOptions(true, true, true, true, 2, 15, 10, 15, 10, 4, 2, 4, 2); //16
				break;
			}
			break;
		case 2:
			System.out.println("World 2 stage number in " + stageNumber);
			switch(stageNumber){ //Rush
			case 1:
				myMath.setOptions(true, false, false, false, 2, 35, 15, 0, 0, 0, 0, 0, 0);
				break;
			case 2:
				myMath.setOptions(false, true, false, false, 2, 0, 0, 30, 15, 0, 0, 0, 0);
				break;
			case 3:
				myMath.setOptions(false, false, true, false, 2, 0, 0, 0, 0, 8, 3, 0, 0);
				break;
			case 4:
				myMath.setOptions(false, false, false, true, 2, 0, 0, 0, 0, 0, 0, 5, 3); //25
				break;
			case 5:
				myMath.setOptions(true, true, true, true, 2, 50, 25, 45, 20, 10, 6, 7, 4);
				break;
			}
			break;
		case 3:
			System.out.println("World 3 stage number in " + stageNumber);
			switch(stageNumber){ //Survival
			
			case 1:
				myMath.setOptions(true, true, false, false, 2, 50, 25, 50, 25, 0, 0, 0, 0);
				break;
			case 2:
				myMath.setOptions(false, false, true, true, 2, 0, 0, 0, 0, 13, 6, 11, 5);//121
				break;
			case 3:
				myMath.setOptions(true, true, true, true, 2, 60, 40, 80, 55, 13, 8, 11, 8);//121
				break;
			case 4:
				myMath.setOptions(true, true, true, true, 2, 100, 60, 120, 70, 15, 6, 15, 10); //Jakolaskut testattava
				break;
			case 5:
				myMath.setOptions(true, true, true, true, 3, 30, 15, 30, 15, 4, 2, 4, 2); //Kolmessa numerossa pit‰s jotenki muistuttaa laskuj‰rjestyksest
				break;
			}
			break;
		case 4:
			switch(stageNumber){ //Combos
			case 1:
				myMath.setOptions(true, true, true, true, 3, 20, 10, 30, 20, 6, 2, 4, 2);
				break;
			case 2:
				myMath.setOptions(true, true, true, true, 3, 60, 40, 60, 40, 6, 4, 6, 3);
				break;
			case 3:
				myMath.setOptions(true, true, true, true, 3, 50, 30, 50, 35, 8, 4, 6, 3);
				break;
			case 4:
				myMath.setOptions(true, true, true, true, 3, 50, 30, 50, 35, 8, 6, 7, 4);
				break;
			case 5:
				myMath.setOptions(true, true, true, true, 3, 70, 50, 70, 50, 8, 6, 7, 5);
				break;
			}
			break;
		case 5:
			switch(stageNumber){ //Jackpot
			case 1:
				myMath.setOptions(true, true, true, true, 3, 70, 50, 70, 50, 8, 6, 7, 5);
				break;
			case 2:
				myMath.setOptions(true, true, true, true, 3, 80, 60, 80, 60, 10, 5, 7, 5);
				break;
			case 3:
				myMath.setOptions(true, true, true, true, 3, 100, 70, 100, 70, 10, 8, 9, 6);
				break;
			case 4:
				myMath.setOptions(true, true, true, true, 3, 150, 100, 150, 100, 12, 8, 10, 8);
				break;
			case 5:
				myMath.setOptions(true, true, true, true, 3, 250, 150, 250, 150, 14, 8, 12, 8);
				break;
			}
			break;
		}
	}
	
	public void setFirstArenaQuestion(int difficulty){
		
		
//		arenaStartNumbers = new int[] {10, 2, 10, 2, 4, 2, 3, 1};
		
		switch(difficulty){
		case 1:
			myMath.setOptions(true, true, false, false, 2, 10, 4, 10, 4, 4, 2, 4, 2);
			break;
		case 2:
			myMath.setOptions(true, true, true, false, 2, 10, 4, 10, 4, 4, 2, 4, 2);
			break;
		case 3:
			myMath.setOptions(true, true, true, true, 3, 10, 4, 10, 4, 4, 2, 4, 2);
			break;
		case 4:
			myMath.setOptions(true, true, true, true, 3, 10, 4, 10, 4, 4, 2, 4, 2);
			break;
		}
		
//		testi.setOptions(true, true, true, true, 2, numberSize[0], numberSize[1], numberSize[2], numberSize[3], numberSize[4], numberSize[5], numberSize[6], numberSize[7]);
	}
	
	
	
	
	public void nextArenaStage(int difficulty, int questionNumber, int[] arenaAdditions){
		
		
		
		switch(difficulty){
		case 1:
			this.arenaAdditions[0] = arenaAdditions[0] + 1;
			this.arenaAdditions[2] = arenaAdditions[2] + 1;
			
			if(questionNumber%2 == 0){
				this.arenaAdditions[1] = arenaAdditions[1] + 1;
				this.arenaAdditions[3] = arenaAdditions[3] + 1;
			}
			break;
		case 2:
			this.arenaAdditions[0] = arenaAdditions[0] + 2;
			this.arenaAdditions[1] = arenaAdditions[1] + 1;
			this.arenaAdditions[2] = arenaAdditions[2] + 2;
			this.arenaAdditions[3] = arenaAdditions[3] + 1;
			
			if(questionNumber%8 == 0){
				this.arenaAdditions[4] = arenaAdditions[4] + 1;
			}
			if(questionNumber%12 == 0){
				this.arenaAdditions[5] = arenaAdditions[5] + 1;
			}
			break;
		case 3:
			this.arenaAdditions[0] = arenaAdditions[0] + 4;
			this.arenaAdditions[1] = arenaAdditions[1] + 1;
			this.arenaAdditions[2] = arenaAdditions[2] + 4;
			this.arenaAdditions[3] = arenaAdditions[3] + 1;
			if(questionNumber%5 == 0){
				this.arenaAdditions[4] = arenaAdditions[4] + 1;
			}
			if(questionNumber%8 == 0){
				this.arenaAdditions[5] = arenaAdditions[5] + 1;
				this.arenaAdditions[6] = arenaAdditions[6] + 1;
			}
			if(questionNumber%12 == 0){
				this.arenaAdditions[7] = arenaAdditions[7] + 1;
			}
			break;
		case 4:
			this.arenaAdditions[0] = arenaAdditions[0] + 8;
			this.arenaAdditions[1] = arenaAdditions[1] + 2;
			this.arenaAdditions[2] = arenaAdditions[2] + 8;
			this.arenaAdditions[3] = arenaAdditions[3] + 2;
			if(questionNumber%6 == 0){
				this.arenaAdditions[4] = arenaAdditions[4] + 1;
				this.arenaAdditions[6] = arenaAdditions[6] + 1;
			}
			if(questionNumber%10 == 0){
				this.arenaAdditions[5] = arenaAdditions[5] + 1;
				this.arenaAdditions[7] = arenaAdditions[7] + 1;
			}
			break;
		}
		
	}
	
	public void giveNumbersAndAdditions(int[] numbers ,int[] arenaAdditions){
		this.numbers = numbers;
		this.arenaAdditions = arenaAdditions;
	}
	
	public int[] getNewNumbers(){
		int[] newNumbers = new int[] {numbers[0], numbers[1], numbers[2], numbers[3], numbers[4], numbers[5], numbers[6], numbers[7]};
		
		return newNumbers;
	}
	
	public void nextArenaQuestion(int difficulty){
		
		
		switch(difficulty){
		case 1:
			
			myMath.setOptions(true, true, false, false, 2, numbers[0] + arenaAdditions[0], 
					numbers[1] + arenaAdditions[1], numbers[2] + arenaAdditions[2], numbers[3] + arenaAdditions[3],
					numbers[4] + arenaAdditions[4], numbers[5] + arenaAdditions[5], numbers[6] + arenaAdditions[6], 
					numbers[7] + arenaAdditions[7]);
			break;
		case 2:
			myMath.setOptions(true, true, true, false, 2, numbers[0] + arenaAdditions[0], 
					numbers[1] + arenaAdditions[1], numbers[2] + arenaAdditions[2], numbers[3] + arenaAdditions[3],
					numbers[4] + arenaAdditions[4], numbers[5] + arenaAdditions[5], numbers[6] + arenaAdditions[6], 
					numbers[7] + arenaAdditions[7]);
			break;
		case 3:
			myMath.setOptions(true, true, true, true, 3, numbers[0] + arenaAdditions[0], 
					numbers[1] + arenaAdditions[1], numbers[2] + arenaAdditions[2], numbers[3] + arenaAdditions[3],
					numbers[4] + arenaAdditions[4], numbers[5] + arenaAdditions[5], numbers[6] + arenaAdditions[6], 
					numbers[7] + arenaAdditions[7]);
			break;
		case 4:
			myMath.setOptions(true, true, true, true, 3, numbers[0] + arenaAdditions[0], 
					numbers[1] + arenaAdditions[1], numbers[2] + arenaAdditions[2], numbers[3] + arenaAdditions[3],
					numbers[4] + arenaAdditions[4], numbers[5] + arenaAdditions[5], numbers[6] + arenaAdditions[6], 
					numbers[7] + arenaAdditions[7]);
			break;
		}
		
		
		
		for(int i = 0; i < numbers.length; i++){
			numbers[i] = numbers[i] + arenaAdditions[i];
		}
		
	}
	/**
	 * 
	 * @param x
	 * @param rightAnswer
	 * @return
	 */
	public int[] randomWrongAnswer(int rightAnswer){
        
		int[] answersArray = new int[2];
        int answersRandomizer = new Random(rndSeed.nextInt()).nextInt(3);
        
//        int numero = 1; //////////////////////////////////////
         
        if(rightAnswer <= minDistance){
        	answersRandomizer = 1;
        }
        
        boolean tryAgain = true;
        
		do{
			if(rightAnswer < 20){
				switch(answersRandomizer){
				case 0:
					System.out.println("Alussa 1");
					answersArray[0] = rightAnswer + (new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					
					answersArray[1] = answersArray[0] + (new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					break;
				case 1:
					System.out.println("Alussa 2");
					answersArray[0] = rightAnswer - (new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					answersArray[1] = rightAnswer + (new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					break;
				case 2:
					System.out.println("Alussa 3");
					answersArray[0] = rightAnswer - (new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					answersArray[1] = answersArray[0] - (new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					break;
				}
			}else{
				switch(answersRandomizer){
				case 0:
					System.out.println("Alussa 1");
					answersArray[0] = rightAnswer + 10;//(new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					
					answersArray[1] = answersArray[0] + 10;//(new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					break;
				case 1:
					System.out.println("Alussa 2");
					answersArray[0] = rightAnswer - 10; //(new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					answersArray[1] = rightAnswer + 10; //(new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					break;
				case 2:
					System.out.println("Alussa 3");
					answersArray[0] = rightAnswer - 10; //(new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					answersArray[1] = answersArray[0] - 10; //(new Random(rndSeed.nextInt()).nextInt(maxDistance - minDistance) + minDistance);
					break;
				}
			}
			
			tryAgain = false;
			
			if(removeNegatives && numberAmount < 3){
				System.out.println("Poistetaan negatiivisia");
				System.out.println("Vastaus = " + rightAnswer);
				if(rightAnswer <= minDistance && !(answersRandomizer == 0)){
	//				uudestaan looppiin
					tryAgain = true;
					answersRandomizer = 0;
				}else if(answersArray[0] < 0 && answersRandomizer == 2){
	//				uudestaan looppiin
					tryAgain = true;
					answersRandomizer = new Random(rndSeed.nextInt()).nextInt(2);
				}else if(answersArray[0] < 0 && rightAnswer > minDistance){
					answersArray[0] = 0;
				}else if(answersArray[1] < 0 && answersArray[0] != 0){
					answersArray[1] = 0;
				}else if(answersArray[0] == 0){
					answersArray[1] = rightAnswer + 3;
				}
				System.out.println("Vastaukset: " + answersArray[0] + " ja " + answersArray[1]);
			}
		}while(tryAgain);
		
		
		return answersArray;
        
    }
	
	/*checking if two int are closer to each other than the "checkDistance". If they are closer this will return true*/
    /**
     * 
     * @param random1
     * @param random2
     * @return
     */
    public boolean checkRandomSimilarity(int random1, int random2){
        int checkDistance = 3;
        
        //System.out.println("meni tanne luvuilla " + random1 + " ja " + random2);
        
        if(random1 == random2){
            return true;
        }else if(random1 > random2 && random1 < random2 + checkDistance){
            return true;
        }else if(random1 < random2 && random1 > random2 - checkDistance){
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @param answer1
     * @param answer2
     * @param correctAnswer
     */
    /*this will be where all the answers are put in a single array in a random order*/
    public void arrayAnswers(int answer1, int answer2, int correctAnswer){
    	
        answerArray = new int[4]; //{answer1, answer2, rightAnswer, rightAnswer};
        
        int random = new Random(rndSeed.nextInt()).nextInt(3);
        int random2 = new Random(rndSeed.nextInt()).nextInt(2);
        
        switch(random){
        case 0:
        	answerArray[0] = answer1;

            if(random2 == 0){
            	answerArray[1] = answer2;
            	answerArray[2] = correctAnswer;
            }else{
            	answerArray[1] = correctAnswer;
            	answerArray[2] = answer2;
            }
            break;
        case 1:
        	answerArray[0] = answer2;
        	
            if(random2 == 0){
            	answerArray[1] = answer1;
            	answerArray[2] = correctAnswer;
            }else{
            	answerArray[1] = correctAnswer;
            	answerArray[2] = answer1;
            }
            break;
        case 2:
        	answerArray[0] = correctAnswer;
        	
        	if(random2 == 0){
        		answerArray[1] = answer1;
        		answerArray[2] = answer2;
            }else{
            	answerArray[1] = answer2;
            	answerArray[2] = answer1;
            }
            break;
        }
        
        answerArray[3] = correctAnswer;
        
    }
    
    /**
     * breaks the question into chars and creates a char array out of them.
     * 
     *
     */
    public void questionToArray(){
    	
    	
    	String[] numbersAsStrings = new String[numberAmount];
    	charArrayLength = 0;
    	
    	for(int i = 0;i < numberAmount; i++){
        	numbersAsStrings[i] = Integer.toString(myMath.getNumbersArray()[i]);
        	charArrayLength = charArrayLength + numbersAsStrings[i].length();
        }
    	
        
    	charArrayLength++;
    	
    	questionChars = new char[charArrayLength + myMath.getCalcTypesArray().length + 2]; //numeroiden m‰‰r‰ + lukujen m‰‰r‰ - 1 v‰limerkkej‰ + =-merkki + vastauksen paikalle viiva
    	
    	int position = 0;
    	
    	for (int k = 0;k < numberAmount - 1;k++){
    		
    		
    		//this loop transfers numbers as Strings from a String array to a char array.
	    	for (int i = 0;i < numbersAsStrings[k].length();i++){
	    		questionChars[position] = numbersAsStrings[k].charAt(i);
	    		position++;
	    	}
	    	//After every number this switch will fill the next position in the char array with a calculation type.
	    	switch(myMath.getCalcTypesArray()[k]){
	        case '+':
	            questionChars[position] = '+';
	            break;
	        case '-':
	        	questionChars[position] = '-';
	            break;
	        case '*':
	        	questionChars[position] = '*';
	            break;
	        case '/':
	        	questionChars[position] = '/';
	            break;
	        case '^':
	        	questionChars[position] = '^';
	            break;
	        case '4':
	        	questionChars[position] = 'v';
	            break;
	        }
	    	position++;
    	}
    	//This will fill the last number to the char array
    	for (int i = 0;i < numbersAsStrings[numberAmount - 1].length();i++){
    		questionChars[position] = numbersAsStrings[numberAmount - 1].charAt(i);
    		position++;
    	}
    	//These will fill the chars for a =-mark and the answer line to the end of the char array.
    	questionChars[position] = '=';
    	position++;
    	
    	questionChars[position] = '?';
    }
    
    /**
     * 
     * 
     * @return correct and wrong answers as an array.
     */
    public int[] getAnswerArray(){
    	return answerArray;
    }
    
    /**
     * returns an array which holds the question as single chars.
     * 
     * 
     * @return question as a char array.
     */
    public char[] getQuestionAsChars(){
		return questionChars;
    }
    
    public int getQuestionLength(){
    	return charArrayLength + numberAmount;
    }
    /**
     * returns the length of an answer.
     * 
     * 
     * @param choice from 1 to 3 the choice of answers.
     * @return the length of chosen answer.
     */
    public int getAnswerLength(int choice){
    	
    	int length = 0;
    	
    	switch(choice){
    	case 0:
    		length = Integer.toString(answerArray[choice]).length();
    	case 1:
    		length = Integer.toString(answerArray[choice]).length();
    	case 2:
    		length = Integer.toString(answerArray[choice]).length();
    	}
    	
    	return length;
    }
    
    public char[] getAnswerAsChars(int choice){
    	
    	char[] charArray = new char[Integer.toString(answerArray[choice]).length()];
    	
    	switch(choice){
    	case 0:
    		for (int i = 0;i < Integer.toString(answerArray[choice]).length();i++){
    			charArray[i] = Integer.toString(answerArray[choice]).charAt(i);
    		}
    	case 1:
    		for (int i = 0;i < Integer.toString(answerArray[choice]).length();i++){
    			charArray[i] = Integer.toString(answerArray[choice]).charAt(i);
    		}
    	case 2:
    		for (int i = 0;i < Integer.toString(answerArray[choice]).length();i++){
    			charArray[i] = Integer.toString(answerArray[choice]).charAt(i);
    		}
    	}
    	
    	return charArray;
    }
	
    
    public void nullMyMath(){
    	myMath = null;
    }
}
