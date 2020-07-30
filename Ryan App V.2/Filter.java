import javax.swing.JOptionPane;
import java.util.ArrayList;
/**
 * Write a description of class GUI2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Filter
{
    private Execute run;
    private boolean error;
    private String s;
    private int x;
    private int y;
    private int i;
    private int j;
    public Filter () {
        run = new Execute();
    }
    public ArrayList<Player> getPlayers () {
        return run.getPlayers();
    }
    public ArrayList<ArrayList<Player>> getTeam () {
        return run.getTeam();
    }
    public ArrayList<Player> getNeutralPlayers () {
        return run.getNeutralPlayers();
    }
    public int getNumTeam () {
        return run.getNumTeam();
    }
    public void setPlayersPerTeam () {
        error=true;
        x=run.getPlayers().size();
        String[] options = {"Change the number of players per team",
                            "Add more players"};
        while (error) {
            s=JOptionPane.showInputDialog(null, "How many players would you like on each team?");
            try { 
                Integer.parseInt(s);
                y=Integer.parseInt(s);
                if (y<=0) {
                    JOptionPane.showMessageDialog(null,"Please enter a positive, non-zero integer");
                } else {
                    if (x/y<2) {
                        s=(String)JOptionPane.showInputDialog(null,
                            "You have an impossible number of teams","",
                            JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                        if (s!=null) {
                            if (s.equals(options[1])) {
                                addPlayers();
                                error=false;
                            }
                        }
                    }
                    else {
                        run.setNumTeam(y);
                        error=false;
                    }
                }
            }
            catch(NumberFormatException e) { 
                JOptionPane.showMessageDialog(null,"Please enter a positive integer");
            } catch(NullPointerException e) {}
        }
    }
    public void addPlayers () {
        error=true;
        while (error) {
            s=JOptionPane.showInputDialog(null, "How many players would you like to add?");
            try { 
                Integer.parseInt(s); 
                if (Integer.parseInt(s)<0) {
                    JOptionPane.showMessageDialog(null,"Please enter a positive integer");
                } else {error=false;}
            } catch(NumberFormatException e) { 
                JOptionPane.showMessageDialog(null,"Please enter a positive integer");
            } catch(NullPointerException e) {}
        }
        x=Integer.parseInt(s);
        if (x!=0) {
            String[] names = new String[x];
            for (i=0;i<x;i++) {
                names[i]=JOptionPane.showInputDialog(null, "Enter the name of player "+(i+1));
            }
            run.addPlayers(x,names);
        }
    }
    public void removePlayers() {
        String[] names = new String[run.getPlayers().size()];
        for (i=0;i<names.length;i++) {
            names[i]=run.getPlayers().get(i).getName();
        }
        s=(String)JOptionPane.showInputDialog(null,"Who would you like to remove?","",
            JOptionPane.QUESTION_MESSAGE,null,names,names[0]);
        if (s != null) {
            run.removePlayers(s);
        }
    }
    public void generateTeams () {
        int numPlayers = run.getPlayers().size();
        int playersPerTeam = run.getNumTeam();
        int numTeams = numPlayers/playersPerTeam;
        int remainder = numPlayers-(playersPerTeam*numTeams);
        boolean out = false;
        int[] nums = new int[3];
        while (2*(numTeams/2) != numTeams && out==false) {
            nums[0]=remainder+playersPerTeam;
            nums[1]=0;
            nums[2]=0;
            String[] options = {
            "Add more players",
            "Change the number of players per team",
            "Create "+nums[0]+" neutral player(s)",
            "Add "+nums[1]+" player(s) to the last "+nums[2]+" teams, make others neutral" };
            s=(String)JOptionPane.showInputDialog(null,"You have an odd number of teams","",
                JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if (s==null) {
                out=true;
                break;
            }
            if (s.equals(options[0])) {
                addPlayers();
            }
            if (s.equals(options[1])) {
                setPlayersPerTeam();
            }
            if (s.equals(options[2])) {
                run.generateTeams(false);
                out=true;
                break;
            }
            if (s.equals(options[3])) {
                run.generateTeams(true);
                out=true;
                break;
            }
            numPlayers = run.getPlayers().size();
            playersPerTeam = run.getNumTeam();
            numTeams = numPlayers/playersPerTeam;
            remainder = numPlayers-(playersPerTeam*numTeams);
        }
        if (remainder==0&& out==false) {
            run.generateTeams(false);
            out=true;
        }
        while (remainder > 0 && out==false) {
            nums[0]=remainder;
            nums[1]=0;
            nums[2]=0;
            String[] options2 = {
            "Add more players",
            "Change the number of players per team",
            "Create "+nums[0]+" neutral player(s)",
            "Add "+nums[1]+" player(s) to the last "+nums[2]+" teams, make others neutral"};
            if (remainder==1) {
                options2[3]="";
            }
            else {
                options2[3]="Add "+nums[1]+" players to the last "+nums[2]+" teams, make others neutral";
            }
            s=(String)JOptionPane.showInputDialog(null,"You have "+remainder+" remaining players","",
                JOptionPane.QUESTION_MESSAGE,null,options2,options2[0]);
            if (s==null||s.equals("")) {
                out=true;
                break;
            }
            if (s.equals(options2[0])) {
                addPlayers();
            }
            if (s.equals(options2[1])) {
                setPlayersPerTeam();
            }
            if (s.equals(options2[2])) {
                run.generateTeams(false);
                out=true;
                break;
            }
            if (s.equals(options2[3])) {
                run.generateTeams(true);
                out=true;
                break;
            }
            numPlayers = run.getPlayers().size();
            playersPerTeam = run.getNumTeam();
            numTeams = numPlayers/playersPerTeam;
            remainder = numPlayers-(playersPerTeam*numTeams);
        }
    }
    public void swap () {
        String[] names = new String[run.getPlayers().size()];
        int[] p1Loc = new int[3];
        int[] p2Loc = new int[3];
        for (i=0;i<names.length;i++) {
            names[i]=run.getPlayers().get(i).getName();
        }
        s=(String)JOptionPane.showInputDialog(null,"Who would you like to swap?","",
            JOptionPane.QUESTION_MESSAGE,null,names,names[0]);
        if (s != null) {
            for (i=0;i<names.length;i++) {
                if (names[i].equals(s)) {
                    p1Loc[0]=i;
                }
            }
            for (i=0;i<run.getTeam().size();i++) {
                for (j=0;j<run.getTeam().get(i).size();j++) {
                    if (run.getTeam().get(i).get(j).getName().equals(s)) {
                        p1Loc[1]=i;
                        p1Loc[2]=j;
                    }
                }
            }
            for (i=0;i<run.getNeutralPlayers().size();i++) {
                if (run.getNeutralPlayers().get(i).getName().equals(s)) {
                    p1Loc[1]=run.getTeam().size();
                    p1Loc[2]=i;
                }
            }
            s=(String)JOptionPane.showInputDialog(null,"Who would you like to swap with?","",
            JOptionPane.QUESTION_MESSAGE,null,names,names[0]);
            if (s != null) {
                for (i=0;i<names.length;i++) {
                    if (names[i].equals(s)) {
                        p2Loc[0]=i;
                    }
                }
                for (i=0;i<run.getTeam().size();i++) {
                    for (j=0;j<run.getTeam().get(i).size();j++) {
                        if (run.getTeam().get(i).get(j).getName().equals(s)) {
                            p2Loc[1]=i;
                            p2Loc[2]=j;
                        }
                    }
                }
                for (i=0;i<run.getNeutralPlayers().size();i++) {
                    if (run.getNeutralPlayers().get(i).getName().equals(s)) {
                        p2Loc[1]=run.getTeam().size();
                        p2Loc[2]=i;
                    }
                }
                run.swap(p1Loc,p2Loc);
            }
        }
    }
    public void addPoints (boolean team, int which) {
        error=true;
        while (error) {
            s=JOptionPane.showInputDialog(null, "How many points would you like to add?");
            try { 
                Integer.parseInt(s); 
                run.addPoints(team, which, Integer.parseInt(s));
                error=false;
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null,"Please enter an integer");
            } catch(NullPointerException e) {
                error=false;
            }
        }
    }
}