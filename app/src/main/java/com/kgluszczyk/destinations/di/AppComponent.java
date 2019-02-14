package com.kgluszczyk.destinations.di;

import com.kgluszczyk.destinations.di.module.AppModule;
import com.kgluszczyk.destinations.Application;
import com.kgluszczyk.destinations.di.module.GsonModule;
import com.kgluszczyk.destinations.di.module.StorageModule;
import com.kgluszczyk.destinations.di.module.ApiModule;
import com.kgluszczyk.destinations.di.module.BuildersModule;
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
