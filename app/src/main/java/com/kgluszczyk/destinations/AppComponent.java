package com.kgluszczyk.destinations;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

@Component(modules = {
        AppModule.class,
        ApiModule.class,
        StorageModule.class,
        GsonModule.class,
        BuildersModule.class,
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class
})

@Singleton
public interface AppComponent extends AndroidInjector<Application> {
    void inject(Application app);

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<Application> {
        @Override
        public abstract AppComponent build();

    }
}
