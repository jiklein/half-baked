import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 * Write a description of class Group here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Execute
{
    private ArrayList<Player> Players;
    private ArrayList<ArrayList<Player>> Team;
    private ArrayList<Player> NeutralPlayers;
    private int numTeam;
    private Random rGen;
    private int random;
    private int dontChange;
    private int i;
    private int j;
    public Execute () {
        Players = new ArrayList<Player> ();
        Team = new ArrayList<ArrayList<Player>> ();
        NeutralPlayers = new ArrayList<Player> ();
        rGen = new Random();
    }
    public ArrayList<Player> getPlayers () {
        Collections.sort(Players, (p1, p2) -> p2.getScore() - p1.getScore());
        return Players;
    }
    public ArrayList<ArrayList<Player>> getTeam () {
        return Team;
    }
    public ArrayList<Player> getNeutralPlayers () {
        return NeutralPlayers;
    }
    public int getNumTeam () {
        return numTeam;
    }
    public void setNumTeam (int numTeam) {
        this.numTeam = numTeam;
    }
    public void addPlayers(int numPlayers, String[]names) {
        for (i=0;i<numPlayers;i++) {
            Players.add(new Player(names[i]));
        }
    }
    public void removePlayers(String name) {
        if (Players.size()!=0) {
            for (i=0;i<Players.size();i++) {
                if (name.equals(Players.get(i).getName())) {
                    Players.remove(Players.get(i));
                    break;
                }
            }
        }
    }
    public void generateTeams (boolean add) {
        Team.clear();
        NeutralPlayers.clear();
        ArrayList<Integer> nums = new ArrayList<Integer> ();
        int remove;
        for (i=0;i<Players.size();i++) {
            nums.add(i);
        }
        if ((Players.size()/numTeam)%2>0) {
            remove=1;
        }else {remove=0;}
        for (i=0;i<(Players.size()/numTeam)-remove;i++) {
            ArrayList<Player> team = new ArrayList<Player> ();
            for (j=0;j<numTeam;j++) {
                random = rGen.nextInt(nums.size());
                team.add(Players.get(nums.get(random)));
                nums.remove(random);
            }
            Team.add(team);
        }
        if (nums.size()>0) {
            if (add) {
                dontChange = 2*(nums.size()/2);
                j=0;
                for (i=0;i<dontChange;i++) {
                    j++;
                    random = rGen.nextInt(nums.size());
                    Team.get(Team.size()-j).add(Players.get(nums.get(random)));
                    nums.remove(random);
                    if (j==Team.size()) {
                        j=0;
                    }
                }
            }
            dontChange = nums.size();
            for (i=0;i<dontChange;i++) {
                random = rGen.nextInt(nums.size());
                NeutralPlayers.add(Players.get(nums.get(random)));
                nums.remove(random);
            }
        }
    }
    public void swap (int[]p1Loc, int[]p2Loc) {
        if (p1Loc[1]==Team.size()) {
            NeutralPlayers.remove(p1Loc[2]);
            NeutralPlayers.add(Players.get(p2Loc[0]));
        }
        else {
            Team.get(p1Loc[1]).remove(p1Loc[2]);
            Team.get(p1Loc[1]).add(Players.get(p2Loc[0]));
        }
        if (p2Loc[1]==Team.size()) {
            NeutralPlayers.remove(p2Loc[2]);
            NeutralPlayers.add(Players.get(p1Loc[0]));
        }
        else {
            Team.get(p2Loc[1]).remove(p2Loc[2]);
            Team.get(p2Loc[1]).add(Players.get(p1Loc[0]));
        }
    }
    public void addPoints (boolean team, int which, int amount) {
        if (team) {
            for (i=0;i<Team.get(which).size();i++) {
                Team.get(which).get(i).setScore(amount);
            }
        }
        else {
            NeutralPlayers.get(which).setScore(amount);
        }
    }
}
