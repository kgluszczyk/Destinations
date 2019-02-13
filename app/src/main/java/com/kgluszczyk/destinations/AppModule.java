package com.kgluszczyk.destinations;

import android.content.Context;
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
