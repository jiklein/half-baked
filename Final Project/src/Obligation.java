
public class Obligation extends Activity
{
	private Time time;
	
	public Obligation(String name, Time time, int duration)
	{
		super(name,0,duration);
		this.time = time;
	}
	
	public Obligation(String name, Time time, int duration,int id)
	{
		super(name,0,duration,id);
		this.time = time;
	}
	
	public Time getTime() 
	{
		return time;
	}
	
	public Obligation clone()
	{
		return new Obligation(name,time,duration,id);
	}
	
}
