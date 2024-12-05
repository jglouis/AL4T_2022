package be.ecam.trafficsim.dagger;

import be.ecam.trafficsim.ISoundManager;
import be.ecam.trafficsim.Main;
import be.ecam.trafficsim.SoundManager;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Module
public abstract class SoundManagerModule {
    @Provides
    @Singleton
    public static ISoundManager soundManager() {
        Map<String, InputStream> sounds = null;
        try {
            sounds = Map.of(
                    "traffic", Objects.requireNonNull(Main.class.getResourceAsStream("/Traffic Sounds - Free Sound Effects - Traffic Sound Clips - Sound Bites.wav")),
                    "drift", Objects.requireNonNull(Main.class.getResourceAsStream("/drift.wav"))
            );
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return new SoundManager(sounds);
    }
}
