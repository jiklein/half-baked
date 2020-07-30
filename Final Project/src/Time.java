
public class Time
{
	private int hour;
	private int minute;
	
	public Time(int hour, int minute)
	{
		this.hour = hour;
		this.minute = minute;
	}
	
	public int minutesUntil(Time time)
	{
		return ( (time.getHour()-hour-1) *60) + time.getMinute() + 60 - minute;
	}
	
	public void addMinutes(int minutes)
	{
		int totalMinutes = minute + minutes;
		hour += totalMinutes/60;
		minute = totalMinutes%60;
	}
	
	public Time clone()
	{
		return new Time(hour,minute);
	}
	
	public String toString()
	{
		return (hour<10 ? " ":"")+hour+":"+ (minute<10 ? "0":"") +minute;
	}
	
	private int getMinute() 
	{
		return minute;
	}

	private int getHour() 
	{
		return hour;
	}
}
