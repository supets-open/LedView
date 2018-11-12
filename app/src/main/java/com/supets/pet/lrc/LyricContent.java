package com.supets.pet.lrc;

public class LyricContent {


    private String mLyricContent;
    private int mLyricTime;

    public void setLyric(String lyricContent) {
        this.mLyricContent = lyricContent;
    }

    public String getLyricContent() {
        return mLyricContent;
    }

    public int getLyricTime() {
        return mLyricTime;
    }

    public void setLyricTime(int lyricTime) {
        this.mLyricTime = lyricTime;
    }
}
