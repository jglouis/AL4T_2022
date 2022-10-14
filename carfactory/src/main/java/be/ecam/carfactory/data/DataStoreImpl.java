package be.ecam.carfactory.data;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataStoreImpl implements DataStore{

    private static DataStoreImpl INSTANCE;

    public static DataStoreImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataStoreImpl();
        }
        return INSTANCE;
    }


    @Inject
    public DataStoreImpl() {

    }

    @Override
    public String read() {
        return null;
    }
}
