package fapra.magenta.sound;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager {
    SoundPool soundPool;
    AudioManager audioManager;
    MediaPlayer mediaPlayer;
    int touchedTargetID;
    int missedTargetID;
    
    public SoundManager(Activity activity) {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        try {
            AssetFileDescriptor descriptor = activity.getAssets().openFd("touchedTarget.wav");
            touchedTargetID = soundPool.load(descriptor, 1);
            descriptor = activity.getAssets().openFd("missedTarget.wav");
            missedTargetID = soundPool.load(descriptor, 1);
        } catch (Exception ex) {
            Log.d( "Sound Sample", "couldn't load sounds" );
            throw new RuntimeException( ex );
        }
        
        mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor descriptor = activity.getAssets().openFd("backgroundMusic.mp3");
            mediaPlayer.setDataSource(descriptor.getFileDescriptor());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Sound Sample", "Couldn't loaf music");
        }
    }
    
    public void playTouchedTargetSound() {
        int volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        soundPool.play(touchedTargetID, volume, volume, 1, 0, 1);
    }
    
    public void playMissedTargetSound() {
        int volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        soundPool.play(missedTargetID, volume, volume, 1, 0, 1);
    }
    
    public void dispose() {
        soundPool.release();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
