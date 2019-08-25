# FeedRss 

This app showcase latest android architecture.

## Tools

### Networking with OkHttp + Retrofit

Networking is implemented via [Retrofit](https://square.github.io/retrofit/), with Http client provided by [OkHttp](http://square.github.io/okhttp/) that provides easy API communication and response parsing using [Gson](https://github.com/google/gson).

### Databinding + Android Architecture Components ViewModel

MVVM is implemented with help of [databinding](https://developer.android.com/topic/libraries/data-binding/) and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) of [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/).

### Reactive with RxKotlin

Project is implemented with FRP (Functional Reactive Programming) using [RxKotlin](https://github.com/ReactiveX/RxKotlin).

### Dependency injection with Dagger2

All dependencies in application are injected using [JSR-330](https://www.jcp.org/en/jsr/detail?id=330)(`@Inject`, `@Singleton`) annotations. [Dagger2](https://google.github.io/dagger/) is used to make all of it easier. 
