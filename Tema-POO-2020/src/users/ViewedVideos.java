package users;

import entertainment.*;

public class ViewedVideos {
    private video video;
    private int numberOfViews;

    public ViewedVideos(video video) {
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
    public video getVideo() {
        return video;
    }

    public void setVideo(video video) {
        this.video = video;
    }
}
