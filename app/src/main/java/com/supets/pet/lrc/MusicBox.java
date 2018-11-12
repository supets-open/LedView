//package com.supets.pet.lrc;
//
//import android.app.Activity;
//import android.content.res.AssetFileDescriptor;
//import android.content.res.AssetManager;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import com.supets.pet.ledview.R;
//
//import java.io.IOException;
//
//public class MusicBox extends Activity implements View.OnClickListener {
//    private Button openAssetMusic;
//    private AssetManager assetManager;
//    private MediaPlayer mediaPlayer;
//    private Button pause;
//    private Button openRawMusic;
//    private Button pauseRawMusic;
//    private MediaPlayer mediaPlayer1;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.musicbox);
//        initView();
//    }
//
//    private void initView() {
//        openAssetMusic = (Button) findViewById(R.id.openAssetMusic);
//
//        openAssetMusic.setOnClickListener(this);
//        pause = (Button) findViewById(R.id.pause);
//        pause.setOnClickListener(this);
//        openRawMusic = (Button) findViewById(R.id.openRawMusic);
//        openRawMusic.setOnClickListener(this);
//        pauseRawMusic = (Button) findViewById(R.id.pauseRawMusic);
//        pauseRawMusic.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.openAssetMusic: //开始播放
//                openAssetMusics();
//                break;
//            case R.id.pause: //暂停播放
//                mediaPlayer.pause();
//                break;
//            case R.id.openRawMusic: //开始播放
//                openRawMusicS();
//                break;
//            case R.id.pauseRawMusic: //暂停播放
//                mediaPlayer1.pause();
//                break;
//        }
//    }
//
//    /**
//     * 打开raw目录下的音乐mp3文件
//     */
//    private void openRawMusicS() {
//        mediaPlayer1 = MediaPlayer.create(this, R.raw.raw_mp3);
//        //用prepare方法，会报错误java.lang.IllegalStateExceptio
//        //mediaPlayer1.prepare();
//        mediaPlayer1.start();
//    }
//
//    /**
//     * 打开assets下的音乐mp3文件
//     */
//    private void openAssetMusics() { //打开Asset目录
//        assetManager = getAssets();
//        mediaPlayer = new MediaPlayer();
//        try { //打开音乐文件shot.mp3
//            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("shot.mp3");
//            mediaPlayer.reset();
//            //设置媒体播放器的数据资源
//            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.d("GsonUtils", "IOException" + e.toString());
//        }
//    }
//}