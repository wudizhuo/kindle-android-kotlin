package com.kindleassistant.di;


import com.kindleassistant.App;
import com.kindleassistant.sender.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, PresenterModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(MainActivity activity);
}