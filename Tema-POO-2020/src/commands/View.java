package commands;

import entertainment.*;
import users.*;

public class View {
    public void view(video newVideo, User user){
        for (ViewedVideos i : user.getViewedVideos()){
            /**
             * verify if video has been viewed
             * */
            if (i.getVideo().getTitle().equals(newVideo.getTitle())){
                // if has been viewed, increment number of view
                i.counter();
            } else{
                user.addToViewed(newVideo);
            }
        }
    }
}
