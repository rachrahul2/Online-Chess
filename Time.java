import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time {
	
	private long myTotalTime;
	private long myInitialTime;
	private long myEndTime;
	
	private long opponentToTalTime;
	private long opponentInitialTime;
	private long opponentEndTime;
	
	Time(){
		this.myTotalTime = 0;
		this.opponentToTalTime = 0;
	}

	public void startOpponentTime(){
		this.opponentInitialTime = System.currentTimeMillis();
	}
	
	public void startMyTime(){
		this.myInitialTime = System.currentTimeMillis();
	}
	
	public void endOpponentTime(){
		this.opponentEndTime = System.currentTimeMillis();
		this.opponentToTalTime += this.opponentEndTime - this.opponentInitialTime; 
	}
	
	public void endMyTime(){
		this.myEndTime = System.currentTimeMillis();
		this.myTotalTime += this.myEndTime - this.myInitialTime;
	}
	
	public String getOpponetTime(){
		long total = this.opponentEndTime - this.opponentInitialTime;
		return getStringTime(total);
	}
	
	public String getMyTime(){
		long total = this.myEndTime - this.myInitialTime;
		return getStringTime(total);
	}
	
	public String getOpponentTotalTime(){
		return getStringTime(this.opponentToTalTime);
	}
	
	public String getMyTotalTime(){
		return getStringTime(this.myTotalTime);
	}
	
	private String getStringTime(long time){
		long milisec = time % 1000;
		long aux = time/1000;
		
		long sec = aux % 60;
		aux = aux / 60;
		
		long min = aux % 60;
		long hours = aux / 60;
		
		return hours + ":" + min +":" + sec;
	}
}
