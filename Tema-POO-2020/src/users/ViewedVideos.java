package users;

import entertainment.*;

public class ViewedVideos {
    private Video video;
    private int numberOfViews;

    public ViewedVideos(Video video) {
        this.video = video;
        this.numberOfViews = 0;
    }
    /**
     * getter and counter for numberOfView
     * */
    public int getNumberOfViews() {
        return numberOfViews;
    }
    public void counter(){
        this.numberOfViews++;
    }
    /**
     * getter and setter for a video
     * */
    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
