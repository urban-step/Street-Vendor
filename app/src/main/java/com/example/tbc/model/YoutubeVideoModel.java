package com.example.tbc.model;


import java.io.Serializable;

public class YoutubeVideoModel implements Serializable {
    private String video_id, video_title;

    public String getVideoId() {
        return video_id;
    }

    public void setVideoId(String videoId) {
        this.video_id = videoId;
    }

    public String getTitle() {
        return video_title;
    }

    public void setTitle(String title) {
        this.video_title = title;
    }


    @Override
    public String toString() {
        return "YoutubeVideoModel{" +
                "videoId='" + video_id + '\'' +
                ", title='" + video_title + '\'' +
                '}';
    }
}
