import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;

public class Schedule extends ArrayList<TimeSlot>
{
	public Schedule()
	{
		super();
	}
	
	public Schedule(Time currentTime, Time bedTime, ArrayList<Obligation> obligations, ArrayList<ArrayList<Activity>> activities) 
	{
		super();
		Collections.sort(obligations, (a, b) -> b.getTime().minutesUntil(a.getTime()));

		int timePeriod,i;
		Time time = currentTime.clone();
		ArrayList<Activity> weight;

		while (time.minutesUntil(bedTime)>0) 
		{
			if (obligations.size() != 0)
				timePeriod = time.minutesUntil(obligations.get(0).getTime());
			else
				timePeriod = time.minutesUntil(bedTime);

			if (timePeriod == 0) 
			{
				super.add(new TimeSlot(time,obligations.get(0)));
				time.addMinutes(obligations.get(0).getDuration());
				obligations.remove(0);
			} 
			else 
			{
				weight = new ArrayList<Activity>();

				i = -1;
				while (weight.size() <= 0 & ++i < activities.size())
					for (int j = 0; j < activities.get(i).size(); j++)
						if (activities.get(i).get(j).getDuration() <= timePeriod)
							weight.add(activities.get(i).get(j));

				if (weight.size() > 0) 
				{
					Collections.sort(weight, (a, b) -> b.getWeight() - a.getWeight());
					super.add(new TimeSlot(time,weight.get(0)));
					time.addMinutes(weight.get(0).getDuration());
					activities.get(i-1).remove(weight.get(0));
					if (activities.get(i-1).size()==0)
						activities.remove(i-1);
				} 
				else 
				{
					if (activities.size() != 0) 
					{
						weight = (ArrayList<Activity>) activities.get(0).clone();
						Collections.sort(weight, (a, b) -> b.getWeight() - a.getWeight());
						super.add(new TimeSlot(time,weight.get(0)));
						weight.get(0).execute(timePeriod);
					}
					time.addMinutes(timePeriod);
				}
			}
		}
	}
}
