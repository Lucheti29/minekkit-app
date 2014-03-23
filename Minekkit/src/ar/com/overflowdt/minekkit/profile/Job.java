package ar.com.overflowdt.minekkit.profile;

/**
 * Created by Fede on 23/03/14.
 */
public class Job {
    String name;
    long xp;
    int level;

    public static int contJobs = 1;

    public long getXPMax(){
        return 100 * ((long)(1.125 + (0.01 * (contJobs - 1))) ^ (level - 1));
    }

    public int getProgessPercent(){
        return Math.round(xp*100/getXPMax());
    }
}
