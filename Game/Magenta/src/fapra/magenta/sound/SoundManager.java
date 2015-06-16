package fapra.magenta.sound;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager implements ISoundManager {
    SoundPool soundPool;
    AudioManager audioManager;
    MediaPlayer mediaPlayer;
    int finishedLineID;
    int missedTargetID;
    int startLineID;
    
    public SoundManager(Activity activity) {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        try {
            AssetFileDescriptor descriptor = activity.getAssets().openFd("touchedTarget.wav");
            finishedLineID = soundPool.load(descriptor, 1);
            descriptor = activity.getAssets().openFd("missedTarget.wav");
            missedTargetID = soundPool.load(descriptor, 1);
            descriptor = activity.getAssets().openFd("startLine.wav");
            startLineID = soundPool.load(descriptor, 1);
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
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Sound Sample", "Couldn't loaf music");
        }
    }
    
    /* (non-Javadoc)
     * @see fapra.magenta.sound.ISoundManager#startMusic()
     */
    @Override
    public void startMusic() {
        mediaPlayer.start();
    }
    
    /* (non-Javadoc)
     * @see fapra.magenta.sound.ISoundManager#stopMusic()
     */
    @Override
    public void stopMusic() {
        mediaPlayer.stop();
    }
    
    /* (non-Javadoc)
     * @see fapra.magenta.sound.ISoundManager#playTouchedTargetSound()
     */
    @Override
    public void playFinishedLineSound() {
        int volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        soundPool.play(finishedLineID, volume, volume, 1, 0, 1);
    }
    
    /* (non-Javadoc)
     * @see fapra.magenta.sound.ISoundManager#playMissedTargetSound()
     */
    @Override
    public void playMissedTargetSound() {
        int volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        soundPool.play(missedTargetID, volume, volume, 1, 0, 1);
    }
    
    /* (non-Javadoc)
     * @see fapra.magenta.sound.ISoundManager#dispose()
     */
    @Override
    public void dispose() {
        soundPool.release();
        stopMusic();
        mediaPlayer.release();
    }

    @Override
    public void playStartLineSound() {
        int volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        soundPool.play(startLineID, volume, volume, 1, 0, 1);
    }
}
