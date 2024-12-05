package be.ecam.trafficsim.dagger;

import be.ecam.trafficsim.ISoundManager;
import be.ecam.trafficsim.Simulation;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {SoundManagerModule.class})
public interface TrafficFactory {
    Simulation simulation();
    ISoundManager soundManager();
}
