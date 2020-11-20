package commands;

import entertainment.*;
import users.*;


public class Favorite {
    public void addToFavorite(video newVideo, User user){
        /**
         * verify if video is viewed
         * */
        for (ViewedVideos i : user.getViewedVideos()){
            /**
             * verify if a video from viewed list has same name
             * with new video
             * */
            if (i.getVideo().getTitle().equals(newVideo.getTitle())){
                    user.addToFav(newVideo);
            }
        }
    }

}
