package be.ecam.trafficsim;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

public interface ISoundManager extends Closeable {
    void playContinuousLoop(@NotNull String clipName);
    void playOnce(@NotNull String clipName);
    void close();
}
