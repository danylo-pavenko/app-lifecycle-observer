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
    AppLifecycleObserver.init(this) {
      ...
    }
  }
}
```

#### End
