package fapra.magenta.audio.sound;

public interface ISoundManager {

    public abstract void playFinishedLineSound();

    public abstract void playMissedTargetSound();
    
    public abstract void playStartLineSound();

    public abstract void dispose();
}