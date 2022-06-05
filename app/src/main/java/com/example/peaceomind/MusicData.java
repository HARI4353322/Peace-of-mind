package com.example.peaceomind;

public class MusicData {


    String musicName,musicImage,musicLink;

    public MusicData() {
    }

    public MusicData(String musicName, String musicImage, String musicLink) {
        this.musicName = musicName;
        this.musicImage = musicImage;
        this.musicLink=musicLink;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicImage() {
        return musicImage;
    }

    public void setMusicImage(String musicImage) {
        this.musicImage = musicImage;
    }

    public String getMusicLink() {
        return musicLink;
    }

    public void setMusicLink(String musicLink) {
        this.musicLink = musicLink;
    }
}
