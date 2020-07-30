
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class GUI 
{
	private JFrame frame;
	private JPanel panel;
	private Schedule schedule;
	private ArrayList<Obligation> obligations;
	private ArrayList<ArrayList<Activity>> activities;
	private Time currentTime;
	private Time bedTime;
	
	
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int FRAME_WIDTH = (int)screenSize.getWidth()/2;
    public static final int FRAME_HEIGHT = (int)screenSize.getHeight()*3/4;
    public static final int[] LABEL_BOUNDS = {20,450,30,25}; //  {x,width,height,font}
    public static final int[] BUTTON_BOUNDS = {(500*FRAME_WIDTH)/FRAME_WIDTH,80,20,10};
	private static final int[] TITLE_BOUNDS = {(FRAME_WIDTH/2) - (FRAME_WIDTH/6),FRAME_HEIGHT/3,50,40};
	private static final int[] ADD_BOUNDS = {(FRAME_WIDTH/2) - (FRAME_WIDTH/6),FRAME_HEIGHT*5/6,FRAME_WIDTH/3,50,40};
	public static final String SPACE = "     ";
	private static final String[] ACTIVITIES = {"Obligation","Homework","Recreation"};
	private static final String[] YES_NO = {"Yes","No"};
	
	
    
	public GUI()
	{
		frame = new JFrame();
		panel = new JPanel();
		schedule = new Schedule();
		JLabel title = new JLabel("Schedule");
		JButton add = new JButton("Add");
		
		frame.setBounds(0,0,FRAME_WIDTH,FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);
        
        title.setBounds(TITLE_BOUNDS[0],0,TITLE_BOUNDS[1],TITLE_BOUNDS[2]);
        title.setFont(new Font(null,1,TITLE_BOUNDS[3]));
        
        add.setBounds(ADD_BOUNDS[0],ADD_BOUNDS[1],ADD_BOUNDS[2],ADD_BOUNDS[3]);
        add.setFont(new Font(null,1,ADD_BOUNDS[4]));
        add.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                add();
                display();
            }
        });
          
        panel.add(title);
        panel.add(add);
        frame.add(panel);
        frame.setVisible(true);
        panel.setVisible(true);
        
        activities = new ArrayList<ArrayList<Activity>>();
		obligations = new ArrayList<Obligation>();
		currentTime = getTime("Enter current time:");
		bedTime = getTime("Enter bed time:");
		
		doStuff();
	}
	
	private void display()
	{
		for (int i=0;i<schedule.size();i++)
		{
			panel.remove(schedule.get(i).getLabel());
			panel.remove(schedule.get(i).getButton());
		}
		
		schedule = new Schedule(currentTime,bedTime,cloneOb(),cloneAct());
		
		for (int i=0;i<schedule.size();i++)
		{
			schedule.get(i).setY(60+i*(LABEL_BOUNDS[2]));
			schedule.get(i).getButton().addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent e) {
	                remove((JButton)e.getSource());
	                display();
	            }
	        });
			panel.add(schedule.get(i).getLabel());
			panel.add(schedule.get(i).getButton());
		}
		panel.repaint();
	}

	private ArrayList<ArrayList<Activity>> cloneAct() 
	{
		ArrayList<ArrayList<Activity>> act = new ArrayList<ArrayList<Activity>>();
		for (int i=0;i<activities.size();i++)
		{
			if (activities.get(i).size()>0)
			{
				ArrayList<Activity> sub = new ArrayList<Activity>();
				for (int j=0;j<activities.get(i).size();j++)
					sub.add(activities.get(i).get(j).clone());
				act.add(sub);
			}
		}
		return act;
	}

	private ArrayList<Obligation> cloneOb() 
	{
		ArrayList<Obligation> ob = new ArrayList<Obligation>();
		for (int i=0;i<obligations.size();i++)
			ob.add(obligations.get(i).clone());
		return ob;
	}
	
	private void remove(JButton button)
	{
		int i=-1;
		while (schedule.get(++i).getButton() != button);
    		if (schedule.get(i).getActivity() instanceof Obligation)
    			obligations.remove(schedule.get(i).getActivity());
    		else
    		{
    			int j=-1;
    			while (!(activities.get(++j).remove(schedule.get(i).getActivity())));
    			if (activities.get(j).size()==0)
    				activities.remove(j);
    		}
	}
	
	private void add() {
		int n = getInt("How many activities would you like to add?");
		for (int i=0;i<n;i++)
		{
			int type=getOption("Select activity type:",ACTIVITIES);
			String name=JOptionPane.showInputDialog(null, "Enter name:");
			int minutes=getInt("Enter estimated duration (minutes):");
			int weight = getInt("Enter estimated weight (1-10):");
			int time=1;
			if (type==0)
				time = getOption("Does your obligation have a speacific time?",YES_NO);
			if (time==0)
				obligations.add(new Obligation(name, getTime("Enter time:"), minutes));
			else
			{
				while (type>=activities.size())
					activities.add(new ArrayList<Activity>());
				activities.get(type).add(new Activity(name,weight,minutes));
			}
		}
	}

	private int homeworkHelp() {
		return 0;
	}
	
	private int getInt(String message)
	{
		int x;
		String s;
		while (true) 
		{
            s=JOptionPane.showInputDialog(null, message);
            try 
            { 
                Integer.parseInt(s);
                x=Integer.parseInt(s);
                if (x<=0)
                    JOptionPane.showMessageDialog(null,"Please enter a positive, non-zero integer");
                else
                    return x;
            }
            catch(NumberFormatException e) 
            {
                JOptionPane.showMessageDialog(null,"Please enter a positive integer, non-zero integer");
            }
            catch(NullPointerException e) {}
        }
	}
	
	private int getOption(String message,String[]options)
	{
		String s=(String)JOptionPane.showInputDialog(null,message,"",
                JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
		int i=-1;
		while (!s.equals(options[++i]));
		return i;
	}
	
	public Time getTime(String message)
	{
		String[] hour = new String[12]; 
		String[] minute = new String[60];
		for (int i=0;i<hour.length;i++)
			hour[i] = ""+(1+i);
		for (int i=0;i<minute.length;i++)
			minute[i] = (i<10?"0":"")+i;
		
		JLabel colin = new JLabel(" : ");
		
		JComboBox hourBox = new JComboBox(hour);
		JComboBox minuteBox = new JComboBox(minute);
		
		JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        
        pane.setWantsInput(false);
        pane.setIcon(null);
		pane.add(hourBox);
		pane.add(colin);
		pane.add(minuteBox);
            
        
        JDialog dialog = pane.createDialog("");
        dialog.setModal(true);
        
        dialog.show();
        dialog.dispose();
        
        return new Time(hourBox.getSelectedIndex()+1,minuteBox.getSelectedIndex());
	}
	private void doStuff()
	{
		for (int i=0;i<3;i++)
			activities.add(new ArrayList<Activity>());
		
		activities.get(0).add(new Activity("Finish College App",9,50));
		activities.get(1).add(new Activity("Math textbook",2,30));
		activities.get(1).add(new Activity("Chiese project",9,120));
		activities.get(1).add(new Activity("History study guide",6,35));
		activities.get(1).add(new Activity("English prep",8,60));
		
		activities.get(1).add(new Activity("time waste 3",1,500));
		activities.get(1).add(new Activity("time waste 2",1,500));
		
		activities.get(1).add(new Activity("time waste 4",1,500));
		activities.get(1).add(new Activity("time waste 5",1,500));
		
		
		activities.get(2).add(new Activity("pet dog",4,200));
		activities.get(2).add(new Activity("Watch youtube",9,20));
		
		
		obligations.add(new Obligation("Tennis",new Time(4,0),60));
		obligations.add(new Obligation("chinese practice",new Time(9,15),5));
		obligations.add(new Obligation("dinner",new Time(6,30),20));
		
		display();
	}
	
	public static void main(String[] args)
	{
		new GUI();
	}
	
	
}
