package di;

import dagger.Binds;
import dagger.Module;

import javax.inject.Singleton;

import manager.*;
import view.IImageLoader;
import view.ImageLoader;

@Module
public abstract class MarioModule {

    @Binds
    @Singleton
    abstract ICamera bindCamera(Camera impl);

    @Binds
    @Singleton
    abstract ISoundManager bindSoundManager(SoundManager impl);

    @Binds
    @Singleton
    abstract IImageLoader bindImageLoader(ImageLoader impl);  //here

    @Binds
    @Singleton
    abstract IMapCreator bindMapCreator(MapCreator impl);

    @Binds
    @Singleton
    abstract IMapManager bindMapManager(MapManager impl);

    @Binds
    @Singleton
    abstract IMarioEngineFacade bindMarioEngineFacade(GameEngine impl);

    @Binds
    @Singleton
    abstract IGameEngine bindGameEngine(GameEngine impl);
}