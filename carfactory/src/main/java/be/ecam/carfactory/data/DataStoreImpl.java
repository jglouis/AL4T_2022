package be.ecam.carfactory.data;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataStoreImpl implements DataStore{
    @Inject
    public DataStoreImpl() {

    }

    @Override
    public String read() {
        return null;
    }
}
