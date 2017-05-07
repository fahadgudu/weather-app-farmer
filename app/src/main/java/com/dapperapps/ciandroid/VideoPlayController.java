package com.dapperapps.ciandroid;

/**
 * Created by usman on 5/7/17.
 */

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;


@SuppressWarnings("unused")
public class VideoPlayController implements OnErrorListener,
        OnPreparedListener, OnCompletionListener {

    private static String TAG = "VideoPlayController";
    private static VideoPlayController instance;

    private VideoView mVideoView;
    private Context mContext;

    private VideoPlayController() {

    }

    public static VideoPlayController getInstance() {
        if (instance == null) {
            instance = new VideoPlayController();

        }
        return instance;
    }

    public VideoView getmVideoView() {
        return mVideoView;
    }

    public void setmVideoView(VideoView mVideoView) {
        this.mVideoView = mVideoView;
    }

    public static boolean isAlive() {
        return instance != null;
    }

    public void destroyInstance() {
        if (instance != null) {
            instance = null;
            mVideoView = null;

        }
    }

    public void playVideo(Context context, final VideoView videoview, String url) {

        try {
            mContext = context;
            // Start the MediaController
            MediaController mediacontroller = new MediaController(context);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(url);
            videoview.setMediaController(null);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            // Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoview.start();
            }
        });
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {


    }

}