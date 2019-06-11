## App lifecycle observer

Lib adds lifecycle observer for your app. Can add logic for method of pause app, resume app, start or close your app.

### Features
- Handle of app states (onStartApp, onCloseApp, onPauseApp, onResumeApp, onAppConfigurationChanged).
- Add several listeners.
- Simple implementation.


### Quick start
1. Implement **gradle** dependency:

> add repositories:
```
buildscript {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

> add implementation
```
dependencies {
    implementation "com.gitub.Daniil-Pavenko:app-lifecycle-observer:<latest version>"
}
```
Latest version: [![](https://jitpack.io/v/Daniil-Pavenko/app-lifecycle-observer.svg)](https://jitpack.io/#Daniil-Pavenko/app-lifecycle-observer)

2. Call init method of `AppLifecycleObserver.init()` in your `Application` class.

```
class MyApp : Application {

  override fun onCreate(){
    super.onCreate()
    AppLifecycleObserver.instance?.init(this)
  }
}
```

3. Add listener as Adapter, use extension method `AppLifecycleObserver.instance.addAppLifecycleListener`.

```
fun setupListeners() {
        AppLifecycleObserver.instance.addAppLifecycleListener("tag_listener") {
            onAppStart { Log.d(TAG, "onAppStart") }
            onAppResumed { activity, byUnlocked -> Log.d(TAG, "onAppResumed") }
            onAppPaused { activity, byLocked -> Log.d(TAG, "onAppPaused") }
            onAppClose { Log.d(TAG, "onAppClose") }
            onAppConfigurationChanged { configuration -> Log.d(TAG, "onAppConfigurationChanged") }
        }
    }
```

#### End

Remove listeners
```
AppLifecycleObserver.instance.removeAllListeners() // remove all registered listeners
AppLifecycleObserver.instance.removeListener("tag_listener") // remove selected by tag of listener
```

##### Attention
If you will add for activity, need to call method `removeListener(tag)`.

// key words:
android, handle app states, handle app lifecycle, app close, app resume, app stop, app configuration changed
