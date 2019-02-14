package com.kgluszczyk.destinations.di.module;

import android.content.Context;
import com.kgluszczyk.destinations.Application;
import com.kgluszczyk.destinations.di.annotation.qualifiers.ApplicationContext;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @ApplicationContext
    @Provides
    Context provideApplicationContext(Application application) {
        return application.getApplicationContext();
    }
}
