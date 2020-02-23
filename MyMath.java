package fi.tamk.project.heatmion;

import java.util.Random;

//Vastaanotettavat:
//	-lukujen suuruus
//	-laskutoimitus
//	-lukujen märä
//	-peitettävä luku
//Palautettavat:
//	-Laskutoimitus
//	-Vastaukset
//Hoidettava:
//	-Eri laskutja kolmen ja enemmän laskuissa
//	-Plus/Miinus/Kerto/Jako(Kerron kautta)/Potenssi/neljöjuuri(Potenssin kautta)

/**
 * 
 * @author Tom2
 *
 */
public class MyMath {
	
	//Booleans for checking if a calculation type is in use. 
	private boolean plusOn;
	private boolean minusOn;
	private boolean multiOn;
	private boolean diviOn;
	private boolean powerOn; //(Not yet usable)
	private boolean sqRootOn; //(Not yet usable)
	private boolean removeNegative = true;
	
	private int numberAmount; //The amount of numbers in the calculation
	private int plusSize; //The size of plus numbers
	private int minPlusSize; //The minimum size of plus numbers
	private int minusSize; // Multiplications
	private int minMinusSize; // minimum multi numbers
	private int diviSize; //The size of plus numbers
	private int minDiviSize; //The minimum size of plus numbers
	private int multiSize; // Multiplications
	private int minMultiSize; // minimum multi numbers
	
	private int hiddenNumber; //Possible choice of a hidden number
	
	private Random rndSeed; //Randomized number to be used as a seed to make other randoms different
	
	private int[] numbersArray; //The array holding the randomized numbers (Not yet done)
	private char[] calcsEndArray; //And array to hold the calculation types to be used in the final calculation
	private int[] endNumbers; //an array holding the final numbers to be used in the calculation (After swapping divisions and square roots)
	private int sqRootAnswer; //Square root answer to be used in the counting phase
	private int switchedDivi;
	private boolean minusDone;
	private int switchedMinus;
//	private int[] diviAnswerArray; //Division answers to be used in the counting phase
	
	private int answer;
	
	/**
	 * 
	 * @param amount
	 * @param size
	 */
	public MyMath(){
//		plusOn = false;
//		minusOn = false;
//		multiOn = false;
//		diviOn = false;
//		powerOn = false;
//		sqRootOn = false;
		
//		numberAmount = amount;
//		this.minusPlusSize = minusPlusSize - 1; //-1 for the +1 when randomizing numbers and taking zero out of the equation.
//		this.minMinusPlusSize = minMinusPlusSize - 1;
//		this.multiDiviSize = multiDiviSize - 1;
//		this.minMultiDiviSize = minMultiDiviSize - 1;
		
		
		
	}
	
	public void setOptions(boolean plus, boolean minus, boolean multi, 
			boolean divi, int numberAmount, int plusSize, 
			int minPlusSize, int minusSize, int minMinusSize, 
			int multiSize, int minMultiSize, int diviSize,
			int minDiviSize){
		plusOn = plus;
		minusOn = minus;
		multiOn = multi;
		diviOn = divi;
		this.numberAmount = numberAmount;
		this.plusSize = plusSize - 1;
		this.minPlusSize = minPlusSize - 1;
		this.minusSize = minusSize - 1;
		this.minMinusSize = minMinusSize - 1;
		this.multiSize = multiSize - 1;
		this.minMultiSize = minMultiSize - 1;
		this.diviSize = diviSize - 1;
		this.minDiviSize = minDiviSize - 1;
		
	}
	/**
	 * 
	 * @param onOrOff
	 */
	public void setPlus(boolean onOrOff){
		if(onOrOff){
			plusOn = true;
		}else{
			plusOn = false;
		}
	}
	
	/**
	 * 
	 * @param onOrOff
	 */
	public void setMinus(boolean onOrOff){
		if(onOrOff){
			minusOn = true;
		}else{
			minusOn = false;
		}
	}
	
	/**
	 * 
	 * @param onOrOff
	 */
	public void setMulti(boolean onOrOff){
		if(onOrOff){
			multiOn = true;
		}else{
			multiOn = false;
		}
	}
	
	/**
	 * 
	 * @param onOrOff
	 */
	public void setDivi(boolean onOrOff){
		if(onOrOff){
			diviOn = true;
		}else{
			diviOn = false;
		}
	}
	
	/**
	 * 
	 * @param onOrOff
	 */
	public void setPower(boolean onOrOff){
		if(onOrOff){
			powerOn = true;
		}else{
			powerOn = false;
		}
	}
	
	/**
	 * 
	 * @param onOrOff
	 */
	public void setSqRoot(boolean onOrOff){
		if(onOrOff){
			sqRootOn = true;
		}else{
			sqRootOn = false;
		}
	}
	/**
	 * Makes an array of calculation types to be used.
	 * <p>
	 * Uses the calculation types which have been put as true and makes an array of them randomly in a random order
	 * to be used in the end calculation.
	 */
	public void calculationTypes(){
		int calcChoices = 0;
		
		//Check which calculation types are used.
		if(plusOn){
			calcChoices++;
		}
		if(minusOn){
			calcChoices++;
		}
		if(multiOn){
			calcChoices++;
		}
		if(diviOn){
			calcChoices++;
		}
		if(powerOn){
			calcChoices++;
		}
		if(sqRootOn){
			calcChoices++;
		}
		
		//Make a char array with as many slots as there are used calculation types.
		char[] calcsArray = new char[calcChoices];
		
		calcChoices = -1;
		
		//put the chosen calculations in an array.
		if(plusOn){
			calcChoices++;
			calcsArray[calcChoices] = '+';
			
		}
		if(minusOn){
			calcChoices++;
			calcsArray[calcChoices] = '-';
			
		}
		if(multiOn){
			calcChoices++;
			calcsArray[calcChoices] = '*';
			
		}
		if(diviOn){
			calcChoices++;
			calcsArray[calcChoices] = '/';
			
		}
		if(powerOn){
			calcChoices++;
			calcsArray[calcChoices] = '^';
			
		}
		if(sqRootOn){
			calcChoices++;
			calcsArray[calcChoices] = '4';
			
		}
		
		//Make an array with as many slots minus 1 as there are numbers in the wanted calculation.
		int variableToUse = numberAmount - 1;
		calcsEndArray = new char[variableToUse + 1];
		
		rndSeed = new Random();
		
		int muuttuja = 0;
		
//		put a random calculation type from the chosen ones to every slot in calcsEndArray.
//		These will be the used calculation types in these random positions.
		for(int i = 0; i < variableToUse; i++){
			
			calcsEndArray[muuttuja] = calcsArray[ new Random(rndSeed.nextInt()).nextInt(calcsArray.length)];
			
			//Makes it so the calculation can only have a single multi or divi (Does not work with more than 3 number)
			if(calcsEndArray[1] == '*'){
				if(calcsEndArray[0] == '*'){
					calcsEndArray[1] = calcsArray[ new Random(rndSeed.nextInt()).nextInt(2)];
				}
				
			}else if(calcsEndArray[1] == '/'){
				if(calcsEndArray[0] == '/'){
					calcsEndArray[1] = calcsArray[ new Random(rndSeed.nextInt()).nextInt(3)];
				}
				
			}
			
			
			
			muuttuja++;
			
		}
	}
	
	
	/**
	 * Randomizes given amount of numbers in a given max range.
	 * 
	 */
	public void randomizeNumbers(){
		
		numbersArray = new int[numberAmount];
		
		rndSeed = new Random();
		
		if(numbersArray.length == 2 || (multiOn == false && diviOn == false)){
			
			if(calcsEndArray[0] == '*'){
				numbersArray[0] = new Random(rndSeed.nextInt()).nextInt(multiSize - minMultiSize) + 1 + minMultiSize;
			}else if(calcsEndArray[0] == '/'){
	    		numbersArray[0] = new Random(rndSeed.nextInt()).nextInt(diviSize - minDiviSize) + 1 + minDiviSize;
	    	}else if(calcsEndArray[0] == '+'){
				numbersArray[0] = new Random(rndSeed.nextInt()).nextInt(plusSize - minPlusSize) + 1 + minPlusSize;
			}else if(calcsEndArray[0] == '-'){
	    		numbersArray[0] = new Random(rndSeed.nextInt()).nextInt(minusSize - minMinusSize) + 1 + minMinusSize;
	    	}
			
	        for(int i = 0;i < numberAmount - 1; i++){
	        	if(calcsEndArray[i] == '*'){
	        		numbersArray[i + 1] = new Random(rndSeed.nextInt()).nextInt(multiSize - minMultiSize) + 1 + minMultiSize;
	        	}else if(calcsEndArray[i] == '/'){
	        		numbersArray[i + 1] = new Random(rndSeed.nextInt()).nextInt(diviSize - minDiviSize) + 1 + minDiviSize;
	        	}else if(calcsEndArray[i] == '+'){
	        		numbersArray[i + 1] = new Random(rndSeed.nextInt()).nextInt(plusSize - minPlusSize) + 1 + minPlusSize;
	        	}else if(calcsEndArray[0] == '-'){
	        		numbersArray[i + 1] = new Random(rndSeed.nextInt()).nextInt(minusSize - minMinusSize) + 1 + minMinusSize;
	        	}
	        }
		}else{
			boolean plus = false;
			boolean minus = false;
			boolean multi = false;
			boolean divi = false;
			int number = 0;
			
			for(int i = 0;i < numberAmount - 1; i++){
				if(calcsEndArray[i] == '/'){
					divi = true;
					number++;
				}else if(calcsEndArray[i] == '*'){
					multi = true;
					number++;
				}else if(calcsEndArray[i] == '-'){
					minus = true;
					number++;
				}else if(calcsEndArray[i] == '+'){
					plus = true;
					number++;
				}
			}
			
			System.out.println(calcsEndArray[0]);
			System.out.println(calcsEndArray[1]);
			System.out.println(numberAmount);
			System.out.println(calcsEndArray.length);
			System.out.println(numbersArray.length);
			
			for(int k = 0;k < number; k++){
				for(int i = 0;i < numberAmount - 1; i++){
					
					if(divi){
						
			        	if(calcsEndArray[i] == '/'){
			        		System.out.println("JAKAAAAAAAAAAAAA");
			        		numbersArray[i] = new Random(rndSeed.nextInt()).nextInt(diviSize) + 1 + minDiviSize;
			        		numbersArray[i + 1] = new Random(rndSeed.nextInt()).nextInt(diviSize) + 1 + minDiviSize;
			        	}
					}else if(multi){
						
						if(calcsEndArray[i] == '*'){
							System.out.println("KERTOOOOOOOOOO");
							if(i > 0 && calcsEndArray[i - 1] != '/'){
								numbersArray[i] = new Random(rndSeed.nextInt()).nextInt(multiSize) + 1 + minMultiSize;
							}else if(i == 0){
								numbersArray[i] = new Random(rndSeed.nextInt()).nextInt(multiSize) + 1 + minMultiSize;
							}
							if(calcsEndArray[i + 1] != '/'){
								numbersArray[i + 1] = new Random(rndSeed.nextInt()).nextInt(multiSize) + 1 + minMultiSize;
							}
			        	}
					}else if(minus){
						
						if(calcsEndArray[i] == '-'){
							System.out.println("VÄHENTÄÄÄÄÄÄ");
							if(i > 0 && calcsEndArray[i - 1] != '/' && calcsEndArray[i - 1] != '*'){
								numbersArray[i] = new Random(rndSeed.nextInt()).nextInt(minusSize) + 1 + minMinusSize;
								System.out.println("eka");
							}else if(i == 0){
								numbersArray[i] = new Random(rndSeed.nextInt()).nextInt(minusSize) + 1 + minMinusSize;
								System.out.println("toka");
							}
							if(i < numberAmount - 1 && calcsEndArray[i + 1] != '/' && calcsEndArray[i + 1] != '*'){
								numbersArray[i + 1] = new Random(rndSeed.nextInt()).nextInt(minusSize) + 1 + minMinusSize;
								System.out.println("kolmas");
							}
							else if(i == numberAmount - 1){
								numbersArray[i + 1] = new Random(rndSeed.nextInt()).nextInt(minusSize) + 1 + minMinusSize;
								System.out.println("neljäs");
							}
			        	}
						System.out.println("ulkona");
					}else if(plus){
						
						if(calcsEndArray[i] == '+'){
							System.out.println("LISÄÄÄÄÄÄÄÄÄÄÄ");
							if(i > 0 && calcsEndArray[i - 1] != '/' && calcsEndArray[i - 1] != '*' && calcsEndArray[i - 1] != '-'){
								System.out.println("eka");
								numbersArray[i] = new Random(rndSeed.nextInt()).nextInt(plusSize) + 1 + minPlusSize;
							}else if(i == 0){
								System.out.println("toka");
								numbersArray[i] = new Random(rndSeed.nextInt()).nextInt(plusSize) + 1 + minPlusSize;
							}
							if(i < numberAmount - 1 && calcsEndArray[i + 1] != '/' && calcsEndArray[i + 1] != '*' && calcsEndArray[i + 1] != '-'){
								System.out.println("kolmas");
								numbersArray[i + 1] = new Random(rndSeed.nextInt()).nextInt(plusSize) + 1 + minPlusSize;
							}else if(i == numberAmount - 1){
								numbersArray[i + 1] = new Random(rndSeed.nextInt()).nextInt(plusSize) + 1 + minPlusSize;
								System.out.println("neljäs");
							}
			        	}
						System.out.println("ulkona");
					}
		        }
				if(divi){
					divi = false;
				}else if(multi){
					multi = false;
				}else if(minus){
					minus = false;
				}else if(plus){
					plus = false;
				}
			}
			
			for(int i = 0; i < numbersArray.length; i++){
				System.out.println(numbersArray[i]);
			}
			
			System.out.println("Plus = " + plusSize + "-" + minPlusSize);
			System.out.println("Minus = " + minusSize + "-" + minMinusSize);
			System.out.println("Multi = " + multiSize + "-" + minMultiSize);
			System.out.println("Divi = " + diviSize + "-" + minDiviSize);
		}
	}
	
	/**
	 * 
	 */
	public void calculateResults(){
		
//		for(int i = 0; i < calcsEndArray.length; i++){
//			System.out.print(calcsEndArray[i] + " ");
//		}
//		System.out.println();
		
		endNumbers = new int[numberAmount];
		int[] helpArray = new int[numberAmount]; //A temporary array to hold the calculation as it is prosessed and the right answer will be on spot [0].

		sqRootAnswer = 0;
		

		
		
		if(removeNegative && numberAmount < 3){
			//Switch the numbers around if the first type is +, / or * and the second is -;
			//Only done with 3 numbers.
			//with 4 numbers if there is a - in the calculation then it will be switched with the type on the first slot.
			minusDone = false;
			if(numberAmount > 2 && ((minusOn && diviOn) || (minusOn && multiOn))){
				if(numberAmount == 3){
	//				for(int i = 0; i < numberAmount - 1; i++){
						switch(calcsEndArray[0]){	
						case '/':
							if(calcsEndArray[1] == '-'){
								numbersArray[0] = numbersArray[0] + calculatePlus(numbersArray[1], numbersArray[2]);
								numbersArray[1] = calculatePlus(numbersArray[1], numbersArray[2]);
							}
							minusDone = true;
						case '*':
							int largest = 0;
							int largestSpot = 0;
							for(int i = 0; i < numberAmount; i++){
								if(numbersArray[i] > largest){
									largest = numbersArray[i];
									largestSpot = i;
								}
							}
							numbersArray[largestSpot] = numbersArray[0];
							numbersArray[0] = largest;
							minusDone = true;
							break;
						case '+':
							if(calcsEndArray[1] == '-'){
								numbersArray[0] = numbersArray[0] + calculatePlus(numbersArray[1], numbersArray[2]);
							}
							minusDone = true;
							break;
						case '-':
	//						//nothing happens
							break;
						}
	//				}
				}else{
					char calc;
					for(int i = 0; i < numberAmount - 1; i++){
						if(calcsEndArray[i] == '-'){
							calc = calcsEndArray[0];
							calcsEndArray[0] = calcsEndArray[i];
							calcsEndArray[i] = calc;
							i = numberAmount - 1;
						}
					}
				}
			}
		}
			
		for(int i = 0; i < numberAmount; i++){
			endNumbers[i] = numbersArray[i];
			helpArray[i] = numbersArray[i];
		}


		//Turn around possible divisions and square roots.
		if(diviOn || sqRootOn){
			for(int i = 0; i < numberAmount - 1; i++){
				
				switch(calcsEndArray[i]){	
				case '/':
					//Kerrotaan kaikki ja vastaus ensimmäiseks
					
					int helpVariable = i;
					switchedDivi = calculateMulti(helpArray[i], helpArray[i + 1]);
					for(int k = i + 1; k < numberAmount - 1; k++){
						
						if(calcsEndArray[k] == '/'){
							switchedDivi = calculateMulti(switchedDivi, helpArray[k + 1]);
							i++;
							
							
						}else{
							k = numberAmount - 1;
						}
					}
					helpArray[helpVariable] = switchedDivi;
					endNumbers[helpVariable] = switchedDivi;
					break;
				case '4':
				
					helpArray[i] = sqRootAnswer;
					helpArray[i + 1] = sqRootAnswer;
					
					break;
				}
			}
		}
		
		
		
		//count power and square root. (Not yet usable)
//		if(powerOn || sqRootOn){
//			for(int i = 0; i < numberAmount - 1; i++){
//				switch(calcsEndArray[i]){
//				case '^':
//					
//					
//					helpArray[i] = calculatePower(helpArray[i], endNumbers[i + 1]);
//					helpArray[i + i] = calculatePower(helpArray[i], endNumbers[i + 1]);
//					
//					
//					break;
//				case '4':
//					
//					helpArray[i] = sqRootAnswer;
//					helpArray[i + i] = sqRootAnswer;
//					
//					break;
//				}
//			}
//		}
		
		//Count multiplications and divisions.
		if(multiOn || diviOn){
			for(int i = 0; i < numberAmount - 1; i++){

				switch(calcsEndArray[i]){
				case '*':
					
					if(calcsEndArray[i + 1] == '*' || calcsEndArray[i + 1] == '/'){
						helpArray[i] = calcMultiAndDivi(i, helpArray);
						
					}else{
						helpArray[i] = calculateMulti(helpArray[i], helpArray[i + 1]);
						helpArray[i + 1] = calculateMulti(helpArray[i], helpArray[i + 1]);
					}
					
					break;
				case '/':
					
					if(calcsEndArray[i + 1] == '*' || calcsEndArray[i + 1] == '/'){
						helpArray[i] = calcMultiAndDivi(i, helpArray);
					}else{
						helpArray[i] = calculateDivi(helpArray[i], helpArray[i + 1]);
						helpArray[i + 1] = calculateDivi(helpArray[i], helpArray[i + 1]);
					}
					
					
					break;
				
				}
//				System.out.println(helpArray[i] + " " + calcsEndArray[i] + " " + endNumbers[i + 1]);////////////////////////////////7

			}
		}
		
		//Turn around possible minuses to not go negative.
		if(removeNegative){
			
			if(calcsEndArray[0] == '-' && numberAmount < 3){
				if(helpArray[0] < helpArray[1]){
					switchedMinus = helpArray[0];
					helpArray[0] = helpArray[1];
					helpArray[1] = switchedMinus;
					endNumbers[0] = helpArray[0];
					endNumbers[1] = helpArray[1];
				}
			}
			
			else if(minusOn && minusDone == false && multiOn == false && diviOn == false){
				for(int i = 0; i < numberAmount - 1; i++){
					if(calcsEndArray[i] == '-'){
					
					//plussataan kaikki ja vastaus ensimmäiseks
					int helpVariable = i;
					switchedMinus = calculatePlus(helpArray[i], helpArray[i + 1]);
					for(int k = i + 1; k < numberAmount - 1; k++){
						
						if(calcsEndArray[k] == '-'){
							switchedMinus = calculatePlus(switchedMinus, helpArray[k + 1]);
							i++;
							
							
						}else{
							i++;
						}
					}
					helpArray[helpVariable] = switchedMinus;
					endNumbers[helpVariable] = switchedMinus;
					
					}
					
				}
			}
			minusDone = false;
		}
//		System.out.println("helpArray:");
//		for(int k = 0; k < helpArray.length; k++){//////////////////////////////7
//			System.out.print(helpArray[k] + " ");
//		}
//		System.out.println();
		
		//Count plus and minus.
		if(plusOn || minusOn){
			for(int i = 0; i < numberAmount - 1; i++){
	
					switch(calcsEndArray[i]){
					case '+':
						helpArray[0] = calculatePlus(helpArray[0], helpArray[i + 1]);
						helpArray[i + 1] = calculatePlus(helpArray[0], helpArray[i + 1]);
						break;
					case '-':
						helpArray[0] = calculateMinus(helpArray[0], helpArray[i + 1]);
						helpArray[i + 1] = calculateMinus(helpArray[0], helpArray[i + 1]);
						break;
					}
	//				System.out.println(helpArray[i] + " " + calcsEndArray[i] + " " + endNumbers[i + 1]);////////////////////////////////7
	
			}
		}
		
		answer = helpArray[0];
		
		
		//TESTAUSTA PRINTTAUSTA!!!!!!!
//		System.out.println("///////////////////////////");
//		System.out.println("numbersArray:");
//		for(int i = 0; i < numbersArray.length; i++){
//			System.out.print(numbersArray[i] + " ");
//		}
//		System.out.println();
//		System.out.println("calcsEndArray:");
//		for(int i = 0; i < calcsEndArray.length; i++){
//			System.out.print(calcsEndArray[i] + " ");
//		}
//		System.out.println();
//		System.out.println("endNumbers:");
//		for(int i = 0; i < endNumbers.length; i++){
//			System.out.print(endNumbers[i] + " ");
//		}
//		System.out.println();
//		System.out.println("answer:");
//		System.out.println(answer);
//		System.out.println("////////////////////////777");
		
		
	}
	
	public void organizeFromLargest(int[] array, int[] endArray, int startingSpot){
		int largest = 0;
		int helpVariable;
		int helpVariable2 = 0;
		
		for(int i = startingSpot; i < array.length + startingSpot; i++){
			largest = 0;
			helpVariable = helpVariable2;
			int muuttuja;
			for(int k = i; k < array.length + startingSpot; k++){
				if(array[helpVariable] > largest){
					muuttuja = largest;
					largest = array[helpVariable];
					array[helpVariable] = muuttuja;
				}
				helpVariable++;
			}
			helpVariable2++;
			
			endArray[i] = largest;
		}
	}
	
	/**
	 * 
	 * @param firstNumberSpot
	 */
	public int calcMultiAndDivi(int firstNumberSpot, int[] array){ ///////muutettavaa
		System.out.println("moi " + firstNumberSpot + " " + array[firstNumberSpot]);/////////////////////////
		
		int[] multiDiviArray = new int[array.length];
		for(int i = 0; i < array.length;i++){
			
			multiDiviArray[i] = array[i];
		}
		
		for(int i = firstNumberSpot; i < calcsEndArray.length; i++){
			if(calcsEndArray[i] == '*' || calcsEndArray[i] == '/'){
				switch(calcsEndArray[i]){
				case '*':
					
					multiDiviArray[firstNumberSpot] = calculateMulti(multiDiviArray[firstNumberSpot], multiDiviArray[i + 1]);
					multiDiviArray[i + 1] = calculateMulti(multiDiviArray[firstNumberSpot], multiDiviArray[i + 1]);
					
					break;
				case '/':
					
					multiDiviArray[firstNumberSpot] = calculateDivi(multiDiviArray[firstNumberSpot], multiDiviArray[i + 1]);
					multiDiviArray[i + 1] = calculateDivi(multiDiviArray[firstNumberSpot], multiDiviArray[i + 1]);
					
					
					break;
				}
			}else{
				i = calcsEndArray.length;
			}
		}
			
		System.out.println(multiDiviArray[firstNumberSpot]);////////////////////////
		return multiDiviArray[firstNumberSpot];
		
	}
	
	/**
	 * 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public int calculatePlus(int number1, int number2){
		return number1 + number2;
	}
	
	/**
	 * 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public int calculateMinus(int number1, int number2){
		return number1 - number2;
	}
	
	/**
	 * 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public int calculateMulti(int number1, int number2){
		return number1 * number2;
	}
	
	/**
	 * 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public int calculateDivi(int number1, int number2){
		return number1 / number2;
	}
	
	/**
	 * 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public int calculatePower(int number1, int number2){
		return number1 ^ number2; //^ merkki laskee jonkun takia plussana.///////////////////////////////////
	}
	
	/**
	 * 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public int calculateSqRoot(int number1, int number2){
		return number1 ^ number2;
	}
	
	public int[] getNumbersArray(){
		return endNumbers;
	}
	
	public char[] getCalcTypesArray(){
		return calcsEndArray;
	}
	
	public int getAnswer(){
		return answer;
	}
	
	public int getNumberAmount(){
		return numberAmount;
	}
	
	public boolean getNegatives(){
		return removeNegative;
	}
	
}




