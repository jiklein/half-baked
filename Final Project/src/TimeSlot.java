
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

public class TimeSlot 
{
	private Time time;
	private Activity activity;
	private JLabel label;
	private JButton button;
	
	public TimeSlot(Time time, Activity activity)
	{
		this.time = time;
		this.activity = activity;
		
		label = new JLabel(time+GUI.SPACE+activity.getName());
		button = new JButton("Remove");
		label.setBounds(GUI.LABEL_BOUNDS[0], 0, GUI.LABEL_BOUNDS[1], GUI.LABEL_BOUNDS[2]);
		button.setBounds(GUI.BUTTON_BOUNDS[0], 0, GUI.BUTTON_BOUNDS[1], GUI.BUTTON_BOUNDS[2]);
		label.setFont(new Font(null,1,GUI.LABEL_BOUNDS[3]));
		button.setFont(new Font(null,1,GUI.BUTTON_BOUNDS[3]));
	}
	
	public Time getTime()
	{
		return time;
	}

	public JLabel getLabel() 
	{
		return label;
	}

	public JButton getButton() 
	{
		return button;
	}

	public void setY(int y) 
	{
		label.setLocation(label.getX(), y);
		button.setLocation(button.getX(), y);
	}

	public Activity getActivity() 
	{
		return activity;
	}
}
