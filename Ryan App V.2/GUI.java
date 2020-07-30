import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.Graphics;
/**
 * Write a description of class GUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUI
{
    private Filter filt;
    private JFrame frame;
    private JPanel panel;
    private JButton[] resize;
    private JLabel tournoment;
    private JLabel[] preferences;
    private JLabel teams;
    private JLabel[] scores;
    private JButton[] mainButtons;
    private ArrayList<JButton> pointButtons;
    private Dimension screenSize;
    private JButton change;
    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 800;
    private final int[] RESIZE_BOUNDS = {15,43,5};
    private final int[] TOURN_BOUNDS = {140,320,45,55};
    private final int[] PREF_BOUNDS = {100,55,200,20};
    private final int[] CHANGE_BOUNDS = {495,57,77,16};
    private final int[] TEAM_BOUNDS = {80,80,210,600,25,18};
    private final int[] SCORE_BOUNDS = {300,510,80,210,50,600,25,18}; //(x1,x2,y,w1,w2,h,f,idealNum)
    private final int[] MAIN_BOUNDS = {5,685,110,30,20};
    private int numOfComponents;
    private int teamFont;
    private int scoreFont;
    private int round;
    private int i;
    private int j;
    public GUI () {
        filt = new Filter();
        /*filt.addPlayers();
        while (filt.getNumTeam()==0) {
            filt.setPlayersPerTeam();
        }*/
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        round = 1;
        frame = new JFrame();
        panel = new JPanel();
        resize = new JButton[2];
        preferences = new JLabel[2];
        scores = new JLabel[2];
        mainButtons = new JButton[4];
        pointButtons = new ArrayList<JButton> ();
        resize[0] = new JButton("+");
        resize[1] = new JButton("-");
        mainButtons[0] = new JButton("Add");
        mainButtons[1] = new JButton("Remove");
        mainButtons[2] = new JButton("Teams");
        mainButtons[3] = new JButton("Swap");
        tournoment = new JLabel("Tournament");
        preferences[0] = new JLabel("Round: "+round);
        preferences[1] = new JLabel("PlayersPerTeam: "+filt.getNumTeam());
        change = new JButton("Change");
        teams = new JLabel("");
        scores[0] = new JLabel("");
        scores[1] = new JLabel("");
        
        frame.setBounds(0,0,FRAME_WIDTH,FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);
        
        initializeThisBtch();
        addSomeActionListenersUpInThisHeazy();
        
        
        displayScores();
        addPointButtons();
        
        
        panel.add(tournoment);
        panel.add(change);
        panel.add(teams);
        for (i=0;i<2;i++) {
            panel.add(resize[i]);
            panel.add(preferences[i]);
            panel.add(scores[i]);
        }
        for (i=0;i<4;i++) {
            panel.add(mainButtons[i]);
        }
        frame.add(panel);
        frame.setVisible(true);
        panel.setVisible(true);
    }
    
    
    
    
    
    
    private void orient () {
        tournoment.setBounds(scalar(TOURN_BOUNDS[0]),0,scalar(TOURN_BOUNDS[1]),scalar(TOURN_BOUNDS[2]));
        tournoment.setFont(new Font(null,1,scalar(TOURN_BOUNDS[3])));
        preferences[0].setText("Round: "+round);
        preferences[1].setText("PlayersPerTeam: "+filt.getNumTeam());
        for (i=0;i<preferences.length;i++) {
            preferences[i].setBounds(scalar(PREF_BOUNDS[0])*(2*i+1),scalar(PREF_BOUNDS[1]),scalar(PREF_BOUNDS[2]),scalar(PREF_BOUNDS[3]));
            preferences[i].setFont(new Font(null,1,scalar(PREF_BOUNDS[3])));
            scores[i].setBounds(scalar(SCORE_BOUNDS[i]),scalar(SCORE_BOUNDS[2]),scalar(SCORE_BOUNDS[i+3]),scalar(SCORE_BOUNDS[5]));
            scores[i].setFont(new Font(null,1,scalar(scoreFont)));
        }
        change.setBounds(scalar(CHANGE_BOUNDS[0]),scalar(CHANGE_BOUNDS[1]),CHANGE_BOUNDS[2],CHANGE_BOUNDS[3]);
        int spacing = (frame.getWidth()-mainButtons.length*MAIN_BOUNDS[2])/MAIN_BOUNDS[0];
        for (i=0;i<mainButtons.length;i++) {
            mainButtons[i].setBounds(spacing*(i+1)+MAIN_BOUNDS[2]*i,scalar(MAIN_BOUNDS[1]),MAIN_BOUNDS[2],MAIN_BOUNDS[3]);
        }
        teams.setBounds(scalar(TEAM_BOUNDS[0]),scalar(TEAM_BOUNDS[1]),scalar(TEAM_BOUNDS[2]),scalar(TEAM_BOUNDS[3]));
        teams.setFont(new Font(null,1,scalar(teamFont)));
    }
    
    
    
    private int scalar (int x) {
        return (frame.getWidth()*x)/FRAME_WIDTH;
    }
    
    
    
    
    private void enlarge () {
        int newWidth = (int)(1.1*frame.getWidth());
        int newHeight = (int)(1.1*frame.getHeight());
        if (newWidth<=screenSize.getWidth()&newHeight<=screenSize.getHeight()) {
            frame.setBounds(frame.getX(),frame.getY(),newWidth,newHeight);
            orient();
        }
    }
    
    
    
    
    private void reduce () {
        int newWidth = (int)(.9*frame.getWidth());
        int newHeight = (int)(.9*frame.getHeight());
        if (newWidth>440) {
            frame.setBounds(frame.getX(),frame.getY(),newWidth,newHeight);
            orient();
        }
    }
    
    private void displayTeams () {
        numOfComponents = filt.getTeam().size()+filt.getPlayers().size()+filt.getNeutralPlayers().size();
        teamFont = (TEAM_BOUNDS[4]*TEAM_BOUNDS[5])/Math.max(TEAM_BOUNDS[5],numOfComponents);
        String str = "<html>";
        for (i=0;i<filt.getTeam().size();i++) {
            str+= "Team "+(i+1)+":<br>";
            for (j=0;j<filt.getTeam().get(i).size();j++) {
                str+= filt.getTeam().get(i).get(j).getName()+"<br>";
            }
        }
        if (filt.getNeutralPlayers().size()>0) {
            str+= "NeutralPlayers:<br>";
        }
        for (i=0;i<filt.getNeutralPlayers().size();i++) {
            str+= filt.getNeutralPlayers().get(i).getName()+"<br>";
        }
        str+= "</html>";
        teams.setText(str);
        orient();
    }
    
    
    
    
    private void displayScores () {
        scoreFont = (SCORE_BOUNDS[6]*SCORE_BOUNDS[7])/Math.max(SCORE_BOUNDS[7],filt.getPlayers().size());
        String[] str = new String[2];
        str[0] = "<html>";
        str[1] = "<html><div align=right>";
        for (i=0;i<filt.getPlayers().size();i++) {
            str[0]+= filt.getPlayers().get(i).getName()+":"+"<br>";
            str[1]+= filt.getPlayers().get(i).getScore()+"<br>";
        }
        str[0]+="</html>";
        str[1]+="</div></html>";
        for (i=0;i<2;i++) {
            scores[i].setText(str[i]);
        }
        orient();
    }
    
    private void addPointButtons() {
        for (i=0;i<pointButtons.size();i++) {
            panel.remove(pointButtons.get(i));
        }
        pointButtons.clear();
        int numOfTeams = 4;//filt.getTeam().size();
        for (i=0;i<numOfTeams;i++) {
            pointButtons.add(new JButton("Add"));
            pointButtons.get(i).addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    for (i=0;i<filt.getTeam().size();i++) {
                        if (e.getSource() == pointButtons.get(i)) {
                            filt.addPoints(true,i);
                            displayScores();
                        }
                    }
                }
            });
            pointButtons.get(i).setBounds(30,80+i*40,57,15);
            panel.add(pointButtons.get(i));
        }
        
    
    }
    
    private void initializeThisBtch() {
        for (i=0;i<resize.length;i++) {
            resize[i].setBounds(0,i*RESIZE_BOUNDS[0],RESIZE_BOUNDS[1],RESIZE_BOUNDS[0]);
            resize[i].setFont(new Font(null,1,RESIZE_BOUNDS[0]+i*RESIZE_BOUNDS[2]));
        }
        change.setBounds(scalar(CHANGE_BOUNDS[0]),scalar(CHANGE_BOUNDS[1]),CHANGE_BOUNDS[2],CHANGE_BOUNDS[3]);
        int spacing = (frame.getWidth()-mainButtons.length*MAIN_BOUNDS[2])/MAIN_BOUNDS[0];
        for (i=0;i<mainButtons.length;i++) {
            mainButtons[i].setBounds(spacing*(i+1)+MAIN_BOUNDS[2]*i,scalar(MAIN_BOUNDS[1]),MAIN_BOUNDS[2],MAIN_BOUNDS[3]);
            mainButtons[i].setFont(new Font(null,1,MAIN_BOUNDS[4]));
        }
        for (i=0;i<resize.length;i++) {
            resize[i].setBounds(0,i*RESIZE_BOUNDS[0],RESIZE_BOUNDS[1],RESIZE_BOUNDS[0]);
            resize[i].setFont(new Font(null,1,RESIZE_BOUNDS[0]+i*RESIZE_BOUNDS[2]));
        }
    }
    
    private void addSomeActionListenersUpInThisHeazy () {
        resize[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                enlarge();
            }
        });
        resize[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                reduce();
            }
        });
        change.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                filt.setPlayersPerTeam();
                preferences[1].setText("PlayersPerTeam: "+filt.getNumTeam());
                displayScores();
            }
        });
        mainButtons[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                filt.addPlayers();
                displayScores();
            }
        });
        mainButtons[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                filt.removePlayers();
                displayScores();
            }
        });
        mainButtons[2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                filt.generateTeams();
                displayTeams();
            }
        });
        mainButtons[3].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                filt.swap();
                displayTeams();
            }
        });
        
        
    }
    
    
}