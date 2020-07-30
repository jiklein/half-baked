
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    private String name;
    private int score;
    public Player(String a)
    {
        if (a.length()>10) {
            name=a.substring(0,10);
        }
        else {
            name=a;
        }
    }
    public void setScore (int x) {
        score=score+x;
    }
    public int getScore () {
        return score;
    }
    public String getName () {
        return name;
    }
}
