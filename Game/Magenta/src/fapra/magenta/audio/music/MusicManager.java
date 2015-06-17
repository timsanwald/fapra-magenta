package fapra.magenta.audio.music;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

public class MusicManager implements IMusicManager {
    
    MediaPlayer mediaPlayer;
    
    public MusicManager(Activity activity) {
        mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor descriptor = activity.getAssets().openFd("backgroundMusic.mp3");
            mediaPlayer.setDataSource(descriptor.getFileDescriptor());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            startMusic();
            mediaPlayer.setLooping(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Sound Sample", "Couldn't load music");
        }
    }
    
    @Override
    public void startMusic() {
        mediaPlayer.start();
    }

    @Override
    public void dispose() {
        mediaPlayer.release();
    }

}
