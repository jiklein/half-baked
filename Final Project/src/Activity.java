
public class Activity 
{
	protected String name;
	protected int weight;
	protected int duration;
	protected int id;
	
	private static int identifier = 0;
	
	public Activity(String name, int weight, int duration)
	{
		this.name = name;
		this.weight = weight;
		this.duration = duration;
		this.id = identifier;
		identifier++;
	}
	
	public Activity(String name, int weight, int duration,int id)
	{
		this.name = name;
		this.weight = weight;
		this.duration = duration;
		this.id = id;
	}
	
	public int getWeight()
	{
		return weight;
	}

	public String getName() 
	{
		return name;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public int getID()
	{
		return id;
	}
	
	public void execute(int time)
	{
		duration = Math.max(0,duration-time);
	}
	
	public Activity clone()
	{
		return new Activity(name,weight,duration,id);
	}
	
	public boolean equals(Object o)
	{
		return id == ((Activity)o).getID();
	}
}
