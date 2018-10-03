## App lifecycle observer
-

Lib for add handlers of Lifecycle your app. Can add logic for method of pause app, resume app, start or close your app.

### Quick start
1. Implement dependency:

```
dependencies {
    implementation "com.gitub.Daniil-Pavenko:app-lifecycle-observer:<latest version>"
}
```
actual version: [version link]

2. Call init method of `AppLifecycleObserver.init()` in your `Application` class.

```
class MyApp : Application {

  override fun onCreate(){
    super.onCreate()
    AppLifecycleObserver.instance?.init(this)
  }
}
```

3. Add listener as Adapter, use method `AppLifecycleObserver.instance?.addAppLifecycleListener`.

```
fun setupListeners() {
        AppLifecycleObserver.instance?.addAppLifecycleListener {
            onAppStart { Log.d(TAG, "onAppStart") }
            onAppResumed { Log.d(TAG, "onAppResumed") }
            onAppPaused { Log.d(TAG, "onAppPaused") }
            onAppClose { Log.d(TAG, "onAppClose") }
        }
    }
```

#### End
