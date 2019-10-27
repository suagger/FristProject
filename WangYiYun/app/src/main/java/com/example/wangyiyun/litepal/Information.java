package com.example.wangyiyun.litepal;

import org.litepal.crud.DataSupport;

public class Information extends DataSupport {
    public Information(){

    }

    private String mid;

    private String alumnId;

    public String getAlumnId() {
        return alumnId;
    }

    public void setAlumnId(String alumnId) {
        this.alumnId = alumnId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    private String singerName;
    private String songsName;

    public String getSongsName() {
        return songsName;
    }

    public void setSongsName(String  songsName) {
        this.songsName = songsName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

}
