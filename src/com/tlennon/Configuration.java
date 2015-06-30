package com.tlennon;

/**
 * Created by tlennon on 6/30/2015.
 */
public class Configuration {
    private String[] dirs;
    private int time;

    public String[] getDirs() {
        return dirs;
    }

    public void setDirs(String[] dirs) {
        this.dirs = dirs;
    }

    public int getTimeToLive() {
        return time;
    }

    public void setTimeToLive(int timeToLive) {
        this.time = timeToLive;
    }

    public Configuration() {

    }
}
