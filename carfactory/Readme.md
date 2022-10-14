# Dependency Injection with dagger 2

Dagger 2 uses `@Inject`, `@Component` and `@Module` annotated interfaces/classes.

## `@Inject` annotation

Annotates Constructor (or field) to be injected by dagger.

## `@Component` annotation

`@Component`s serve as root for the dependency graph and are the base for gradle to build its factories and builders.

`@Component`s need `@Module`(s) to satisfy their dependencies.

```
@Singleton
@Component(modules = {WheelModule.class, EngineModule.class, DataStoreModule.class})
public interface CarFactory {
Car car(); // Dagger will generate Car objects
DataStore dataStore(); // will generate a DataStore

    @Component.Builder
    interface Builder {
        CarFactory build();
        // Here we can inject some dependency know at execution,
        // like this flavor of car.
        @BindsInstance Builder carFlavor(CarFlavor carFlavor);
    }
}
```
## `@Module` annotation

`@Module` describes how dagger needs to build the dependencies.
You either use `@Provides` annotated methods or `@Binds` annotated abstract methods.
Like so:

```
@Module
public abstract class EngineModule {
    // Provides different implementation of the Engine interface
    // each concrete Implementation of Engine has an associated
    // Provider<> dependency ready to use and automatically generated.
    @Provides
    static Engine engine(CarFlavor flavor, Provider<ElectricEngine> electricEngineProvider, Provider<V8Engine> v8EngineProvider, Provider<DieselEngine> dieselEngineProvider) {
        Engine engine;
        switch (flavor) {
            case ELECTRIC_CAR -> {
                engine = electricEngineProvider.get();
            }
            case SPORT_CAR -> {
                engine = v8EngineProvider.get();
            }
            case DIESEL_CAR -> {
                engine = dieselEngineProvider.get();
            }
            default -> throw new RuntimeException("Should not happen");
        }
        return engine;
    }
}
```

```
@Module
public interface DataStoreModule {
    // Will tell Dagger that all DataStore dependencies
    // will be provided as DatastoreImpl    
    @Binds
    DataStore dataStoreImpl(DataStoreImpl impl);
}
```

## `@Singleton` and scopes

Sometimes you need to have a dependency only instantiated once, or for a specific lifetime.
For this you can use scope annotation like the `@Singleton`. Dagger will ensure that the
scope is respected. Note: the types annotated by a scope need to match the scopes annotated
on the `@Component`. You can also define your own scope annotations.

```
@Singleton
public class DataStoreImpl implements DataStore{
...
}
```