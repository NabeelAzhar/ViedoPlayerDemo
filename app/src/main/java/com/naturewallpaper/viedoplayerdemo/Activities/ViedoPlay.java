package com.naturewallpaper.viedoplayerdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import com.naturewallpaper.viedoplayerdemo.R;
import java.util.logging.Handler;
import static com.naturewallpaper.viedoplayerdemo.Activities.ViedoActivity.viedomodelArrayList;
public class ViedoPlay extends AppCompatActivity {
    VideoView videoView;
    ImageView prev, next, pause;
    SeekBar seekBar;
    TextView current;
    TextView total;
    int video_index = 0;
    Handler mHandler,handler;
    double current_pos, total_duration;
    LinearLayout showProgress;
    boolean isVisible = true;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viedo_play);
        videoView = (VideoView) findViewById(R.id.videoview);
        prev = (ImageView) findViewById(R.id.prev);
        next = (ImageView) findViewById(R.id.next);
        pause = (ImageView) findViewById(R.id.pause);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        current = (TextView) findViewById(R.id.current);
        total = (TextView) findViewById(R.id.total);
        video_index = getIntent().getIntExtra("pos" , 0);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video_index++;
                if (video_index< (viedomodelArrayList.size())){
                    playVideo(video_index);
                } else {
                    video_index =0;
                    playVideo(video_index);
                }
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setVideoProgress();
            }
        });
        playVideo(video_index);
        prevVideo();
        nextVideo();
        setPause();
//        hideLayout();


    }
    public void playVideo(int pos) {
        try  {
            videoView.setVideoURI(viedomodelArrayList.get(pos).getVideoUri());
            videoView.start();
            pause.setImageResource(R.drawable.ic_baseline_pause_circle);
            video_index=pos;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setVideoProgress() {
        //get the video duration
        current_pos  = videoView.getCurrentPosition();
        total_duration = videoView.getDuration();

        //display video duration
        total.setText(timeConversion((long) total_duration));
        current.setText(timeConversion((long) current_pos ));
        seekBar.setMax((int) total_duration);
//        final Handler handler;
//        handler = new Handler();
//
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    current_pos = videoView.getCurrentPosition();
//                    current.setText(timeConversion((long) current_pos));
//                    seekBar.setProgress((int) current_pos);
//                    handler.wait(this, 1000);
//                } catch (IllegalStateException ed){
//                    ed.printStackTrace();
//                }
//            }
//        };
//        handler.wait(runnable, 1000);




}
// seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            current_pos = seekBar.getProgress();
//            videoView.seekTo((int) current_pos);
//        }
//    });
    public void prevVideo() {
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_index > 0) {
                    video_index--;
                    playVideo(video_index);
                } else {
                    video_index = viedomodelArrayList.size() - 1;
                    playVideo(video_index);
                }
            }
        });
    }
    public void setPause() {
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    pause.setImageResource(R.drawable.ic_baseline_play_circle);
                } else {
                    videoView.start();
                    pause.setImageResource(R.drawable.ic_baseline_pause_circle);
                }
            }
        });
    }
    public void nextVideo() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_index < (viedomodelArrayList.size()-1)) {
                    video_index++;
                    playVideo(video_index);
                } else {
                    video_index = 0;
                    playVideo(video_index);
                }
            }
        });
    }
    public String timeConversion(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }

    // hide progress when the video is playing
//    public void hideLayout() {
//
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                showProgress.setVisibility(View.GONE);
//                isVisible = false;
//            }
//        };
//        handler.postDelayed(runnable, 5000);
//
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mHandler.removeCallbacks(runnable);
//                if (isVisible) {
//                    showProgress.setVisibility(View.GONE);
//                    isVisible = false;
//                } else {
//                    showProgress.setVisibility(View.VISIBLE);
//                    mHandler.postDelayed(runnable, 5000);
//                    isVisible = true;
//                }
//            }
//        });
//
//    }
}
