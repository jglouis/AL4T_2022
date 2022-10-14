package be.ecam.carfactory.dagger;

import be.ecam.carfactory.model.Engine;
import be.ecam.carfactory.model.V8Engine;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class EngineModule {
    @Provides
    static Engine engine() {
        return new V8Engine();
    }
}
