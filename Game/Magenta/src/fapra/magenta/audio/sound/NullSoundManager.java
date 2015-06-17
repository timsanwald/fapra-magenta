package fapra.magenta.audio.sound;

import android.app.Activity;

public class NullSoundManager implements ISoundManager {

    public NullSoundManager(Activity activity) {
    }

    @Override
    public void playFinishedLineSound() {

    }

    @Override
    public void playMissedTargetSound() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void playStartLineSound() {
        
    }

}
