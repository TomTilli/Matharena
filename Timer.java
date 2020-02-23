package fi.tamk.project.heatmion;

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//AJASTA OIKEIN KESTÄMÄÄN SEKUNNIN!!!!!!!
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/**
 * 
 * 
 * @author Tom2
 *
 */
public class Timer implements Runnable{
	
	private boolean IS_TIMER_ON;
	private boolean isTimerPaused;
	private int startTime;
	private boolean direction;
	private Thread timerThread;
	private int minutes;
	private int seconds;
	private String timeAsString;
	private boolean TIMER_PAUSED;
	private int pauseTime;
	private int timeSpent;
	
	private int timedAnswering;
	
	/**
	 * 
	 * @param startTime
	 * @param direction
	 */
	public Timer(int startTime, boolean direction){
		this.startTime = startTime;  //360
		this.direction = direction;  //false
		
		System.out.println(startTime);
		
		seconds = 0;
		minutes = 0;
		timeAsString = "00:00";
		TIMER_PAUSED = false;
		pauseTime = 0;
		timeSpent = 0;
		
		timedAnswering = 5;
	}
	
	/**
	 * 
	 * @param startTime
	 * @param direction
	 */
	public void newTimer(int startTime, boolean direction){
		this.startTime = startTime + 1;
		this.direction = direction;
		
		isTimerPaused = false;
		
		System.out.println(startTime);
		
		seconds = 0;
		minutes = 0;
	}
	
	/**
	 * 
	 */
	public void startTimer(){
		IS_TIMER_ON = true;
		timerThread = new Thread(this);
		
//		if(direction == false){
			separateToSecondsAndMinutes();
//		}
		printTimer();
		
		timerThread.start();
	}
	
	/**
	 * 
	 */
	public void run(){
		
		
		
		
		
		while(IS_TIMER_ON){
			
			checkIfPaused();
			
			try{
				if(timedAnswering > 0){
					timedAnswering--;
				}
				timeSpent++;
				
				nextTime();
				printTimer();

				if(minutes == 0 && seconds == 0){
					stopTimer();
				}
				
				Thread.sleep(1000 + pauseTime);
			}catch(Exception e){
				System.out.println("hups kello");
			}
			
			if(TIMER_PAUSED){
				pauseTime = 0;
				TIMER_PAUSED = false;
			}
		}
	}
	
	/**
	 * Divides the time to minutes and seconds.
	 * 
	 */
	public void separateToSecondsAndMinutes(){
		
		while((seconds + 60) < startTime){ 
			seconds = seconds + 60;
			minutes++;
		}
		
		seconds = startTime - seconds;
		
	}
	/**
	 * 
	 * 
	 */
	public void nextTime(){
		if(direction){
			seconds++;
			if(seconds == 60){
				minutes++;
				seconds = 0;
			}
		}else{
			seconds--;
			if(seconds < 0){
				seconds = 59;
				minutes--;
			}
		}
	}
	
	/**
	 * 
	 * 
	 */
	public void printTimer(){
		timeAsString = "";
		
		if(minutes > 9 && seconds > 9){
		timeAsString = Integer.toString(minutes) + ":" + Integer.toString(seconds);
		}else{
			if(minutes < 10){
				timeAsString = timeAsString + "0" + Integer.toString(minutes);
			}else{
				timeAsString = timeAsString + Integer.toString(minutes);
			}
			timeAsString = timeAsString + ":";
			if(seconds < 10){
				timeAsString = timeAsString + "0" + Integer.toString(seconds);
			}else{
				timeAsString = timeAsString + Integer.toString(seconds);
			}
		}
		
		//System.out.println(timeAsString);
		
		//return timeAsString;
	}
	
	public String getTimer(){
		return timeAsString;
	}
	
	public int getSeconds(){
		return seconds;
	}
	
	public int getMinutes(){
		return minutes;
	}
	
	/**
	 * 
	 * 
	 */
	public void stopTimer(){
		//timerThread.interrupt();
		IS_TIMER_ON = false;
	}
	
	public void pauseTimer(int milSeconds){
		pauseTime = milSeconds;
		TIMER_PAUSED = true;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public boolean timerOn(){
		
		if(IS_TIMER_ON){
			return true;
		}
		
		return false;
	}
	
	public int getTimedAnswering(){
		return timedAnswering;
	}
	
	public void setTimedAnswering(int time){
		timedAnswering = time;
	}
	
	public boolean getIsTimedAnsweringOn(){
		if(timedAnswering <= 0){
			return false;
		}else{
			return true;
		}
	}
	
	public void setStartTime(int givenTime){
		startTime = givenTime;
	}
	
	public void setMoreTime(int timeAmount){
		seconds = seconds + timeAmount + 1;
		while(seconds > 59){
			if(seconds > 59){
				seconds = seconds - 60;
				minutes = minutes + 1;
			}
		}
	}
	
	
	//Timer pause feature 
	public void setTimerOnWait(){
		isTimerPaused = true;
	}
	
	public void resumeTimer(){
		isTimerPaused = false;
		synchronized(this) {
			notify();
		}
	}
	
	public void checkIfPaused(){
		synchronized(this) {
			while(isTimerPaused) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
			}
		}	
	}
	
	public int getTimeSpent(){
		return timeSpent;
	}
	
	public void nullTimer(){
		timerThread = null;
	}
}
