package com.supets.pet.lrc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.supets.pet.ledview.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LrcMainActivity extends Activity { //播放状态 暂定或正在播放
    private boolean isPlay = false; //播放按钮 屏幕下方控制栏中
    private Button mPlayBtn; //播放进度 屏幕下方控制栏中
    private SeekBar mPlaySeekBar; //点击事件
    private MyOnClickListener myOnClickListener = new MyOnClickListener(); //MediaPlayer
    private MediaPlayer mMediaPlayer; //mp3链接 修改成可以播放的就行
    private String mP3Url = "http://yinyueshiting.baidu.com/data2/music/134380372/30299672000128.mp3?xcode=666f07de58e929ce29676627b54cb918";
    private Uri uri = null; //定时器 由于功能中有付费播放功能，需要定时器来判断
    private Timer mTimer = null;
    private TimerTask mTimerTask = null; //互斥变量，防止定时器与SeekBar拖动时进度冲突
    private boolean isChanging = false; //当前播放时间与总时间
    private TextView mNowText, mTotalText; //下方控制栏
    private RelativeLayout mControlLayout; //上方标题栏 显示mp3标题
    private RelativeLayout mTitleLayout; //试看结束提示
    private TextView mWarnText; //刚开始显示播放按钮 屏幕中间的播放按钮
    private ImageView mPlayImg; //整个屏幕
    private FrameLayout mFrame; //控制栏与标题栏是否显示 默认不显示
    private boolean isShow = false; //试看时间 三分钟
    private static int TRY_TIME = 3 * 60 * 1000; //Handler用来控制时间与播放
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: //如果大于试看时间 停止播放，控制栏与标题栏隐藏，屏幕不让点击（防止弹出控制栏与标题栏）提示试看结束。
                    if (mMediaPlayer.getCurrentPosition() >= TRY_TIME) {
                        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                            mMediaPlayer.stop();
                            mFrame.setEnabled(false);
                            isShow = false;
                            mControlLayout.setVisibility(View.GONE);
                            mTitleLayout.setVisibility(View.GONE);
                            mWarnText.setVisibility(View.VISIBLE);
                        }
                    } //设置当前播放进度
                    mNowText.setText(generateTime(mMediaPlayer.getCurrentPosition()) + "/");
                    break;
                case 1: //当用户点击屏幕时 标题栏与控制栏显示，并在无操作的三秒后消失。
                    mControlLayout.setVisibility(View.GONE);
                    mTitleLayout.setVisibility(View.GONE);
                    isShow = false;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrc);
        mMediaPlayer = new MediaPlayer();
        mPlayBtn = (Button) findViewById(R.id.player_button);
        mPlayBtn.setOnClickListener(myOnClickListener);
        mPlaySeekBar = (SeekBar) findViewById(R.id.player_seek);
        mPlaySeekBar.setOnSeekBarChangeListener(new MySeekbar());
        mNowText = (TextView) findViewById(R.id.player_left);
        mTotalText = (TextView) findViewById(R.id.player_right);
        mWarnText = (TextView) findViewById(R.id.player_warn);
        mControlLayout = (RelativeLayout) findViewById(R.id.player_control);
        mTitleLayout = (RelativeLayout) findViewById(R.id.player_title);
        mPlayImg = (ImageView) findViewById(R.id.player_img2);
        mPlayImg.setOnClickListener(myOnClickListener); //点击屏幕 显示底部
        mFrame = (FrameLayout) findViewById(R.id.player_frame);
        mFrame.setOnClickListener(myOnClickListener);
        mPlayImg.setEnabled(false);
        uri = Uri.parse(mP3Url); //准备播放
        play(uri); //播放完成事件
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) { //进度归零
                mediaPlayer.seekTo(0); //进度条归零 mPlaySeekBar.setProgress(0); //控制栏中的播放按钮显示暂停状态
                mPlayBtn.setBackground(getResources().getDrawable(R.mipmap.simple_player_center_play));
                isPlay = false; //重置并准备重新播放
                mediaPlayer.reset();
                play(uri);
            }
        }); //异步准备（准备完成），准备到准备完成期间可以显示进度条之类的东西。
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mPlaySeekBar.setProgress(0);
                mPlayImg.setEnabled(true);
                mNowText.setText("00:00/");
                mTotalText.setText(generateTime(mMediaPlayer.getDuration() + 1000));
                mPlaySeekBar.setMax(mMediaPlayer.getDuration() + 1000);//设置进度条
            }
        });
    }

    public void play(Uri uri) {
        try {
            mMediaPlayer.setDataSource(LrcMainActivity.this, uri); //采用异步准备，使用prepare方法时，用户进入该界面需要等待几秒，如同死机一般，，，
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //进度条的进度变化事件

    class MySeekbar implements SeekBar.OnSeekBarChangeListener { //当进度条变化时触发
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        } //开始拖拽进度条

        public void onStartTrackingTouch(SeekBar seekBar) {
            isChanging = true;
        } //停止拖拽进度条

        public void onStopTrackingTouch(SeekBar seekBar) {
            mMediaPlayer.seekTo(mPlaySeekBar.getProgress());
            mNowText.setText(generateTime(mMediaPlayer.getCurrentPosition()) + "/");
            isChanging = false;
            if (mMediaPlayer.getCurrentPosition() >= TRY_TIME) { //大于试看时间 停止播放
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mFrame.setEnabled(false);
                    isShow = false;
                    mControlLayout.setVisibility(View.GONE);
                    mTitleLayout.setVisibility(View.GONE);
                    mWarnText.setVisibility(View.VISIBLE);
                }
            } else {
                mWarnText.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag", "暂停");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tag", "停止");
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) { //当点击屏幕中间的播放按钮时
                case R.id.player_button:
                    if (isPlay) { //如果当前正在播放 停止播放 更改控制栏播放状态 mPlayImg.setVisibility(View.VISIBLE);
                        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                            mMediaPlayer.pause();
                            mPlayBtn.setBackground(getResources().getDrawable(R.mipmap.simple_player_center_play));
                        }
                    } else {
                        //如果当前停止播放 继续播放 更改控制栏状态
                        if (mMediaPlayer != null) {
                            mPlayImg.setVisibility(View.GONE);
                            mPlayBtn.setBackground(getResources().getDrawable(R.mipmap.simple_player_center_pause));
                            mMediaPlayer.start(); //定时器 更新进度
                            if (mTimer == null) {
                                mTimer = new Timer();
                                mTimerTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        if (isChanging == true) {
                                            return;
                                        }
                                        if (isPlay) {
                                            mHandler.sendEmptyMessage(0);
                                            mPlaySeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                                        }
                                    }
                                };
                                mTimer.schedule(mTimerTask, 0, 1000);
                            }
                        }
                    }
                    isPlay = !isPlay;
                    break;
                case R.id.player_img2:
                    if (mMediaPlayer != null) {
                        mPlayBtn.setBackground(getResources().getDrawable(R.mipmap.simple_player_center_pause));
                        mMediaPlayer.start();
                        if (mTimer == null) {
                            mTimer = new Timer();
                            mTimerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    if (isChanging == true) {
                                        return;
                                    }
                                    if (isPlay) {
                                        mHandler.sendEmptyMessage(0);
                                        mPlaySeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                                    }
                                }
                            };
                            mTimer.schedule(mTimerTask, 0, 1000);
                        }
                        isPlay = true;
                        mPlayImg.setVisibility(View.GONE);
                    }
                    break;
                case R.id.player_frame: //点击屏幕时 控制 控制栏的显示与隐藏
                    if (!isShow) {
                        mControlLayout.setVisibility(View.VISIBLE);
                        mTitleLayout.setVisibility(View.VISIBLE);
                        mHandler.sendEmptyMessageDelayed(1, 3000);
                    } else {
                        mHandler.removeMessages(1);
                        mControlLayout.setVisibility(View.GONE);
                        mTitleLayout.setVisibility(View.GONE);
                    }
                    isShow = !isShow;
                    break;
            }
        }
    } //界面销毁时，释放mediaplayer与定时器

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tag", "onDestroy");
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            mTimerTask = null;
        }
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
        }
    }

    /**
     * 格式化显示的时间
     */
    private String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }
}