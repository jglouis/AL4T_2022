package be.ecam.carfactory.dagger;

import be.ecam.carfactory.data.DataStore;
import be.ecam.carfactory.data.DataStoreImpl;
import dagger.Binds;
import dagger.Module;

@Module
public interface DataStoreModule {
    @Binds
    DataStore dataStoreImpl(DataStoreImpl impl);
}
