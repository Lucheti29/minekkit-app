package ar.com.overflowdt.minekkit.models;

/**
 * Created by Fede on 23/03/14.
 */
public class Job {
    String name;
    long xp;
    int level;

    public static int contJobs = 1;

    public long getXPMax() {
        return Math.round(100 * (Math.pow(1.115, (level - 1))));
    }

    public int getProgessPercent() {
        return Math.round(xp * 100 / getXPMax());
    }
}
