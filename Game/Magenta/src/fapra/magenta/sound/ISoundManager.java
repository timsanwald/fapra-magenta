package fapra.magenta.sound;

public interface ISoundManager {
    
    public abstract void startMusic();

    public abstract void stopMusic();

    public abstract void playFinishedLineSound();

    public abstract void playMissedTargetSound();
    
    public abstract void playStartLineSound();

    public abstract void dispose();

}