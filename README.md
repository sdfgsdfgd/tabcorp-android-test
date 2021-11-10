# SpaceX - TabCorp Android Development Challenge
### by Kaan Osmanagaoglu, 10 November 2021 


# Architecture Overview
The architecture consists of;
  - Moshi - ( JSON Parsing )
  - Hilt - (Dependency Injection)
  - Coroutines, Coroutine Flows/StateFlows, LiveData, SingleLiveEvent
  - View Bindings, Data Bindings
  - Picasso - (Image retrieval)
  - OkHttp/Retrofit - ( Network Client/API )
  - Jetpack Components:   Navigation + SafeArgs, LiveData, KTX
  - Material Design, Constraintlayout
  - Single activity app with Fragments/VM

The 3 main layers are:
- `data`: All SpaceX API endpoints, request and response models
- `domain`: POJO Mappers, business logic / cache, Some cache filtering toggling logic resides here
- `ui`: UI

# Features & Assumptions
- SpaceX / Main Screen 
  - Fetches the list of all SpaceX launches from the "all launches" endpoint
  - Groups by;
    - mission name first letter
    - year
  - Includes 2 buttons: 
    - Top Button: Toggle between showing all or only successful launches
    - Bottom button: Toggle between sorting by date or mission name first letter
  - Badge images of the launch rockets are loaded by Picasso from the retrieved URLs
  - Assumed: Asset Fortune cat symbols are success indicators for successful rocket launches, they're also placeholders for loading images
  - Bonus: Grouping, sorting and filtering features work together
  - For RecyclerView implementation adapters are data-bound, with "assets" items residing in VM
      - `layoutProvider: (T) -> Int`: a closure that takes in a model object type and returns the equivalent layout resource
      - `itemDiff: DiffUtil.ItemCallback<T>`: a diff implementation that compares model object to determine changes to the list, the framework can automatically update the recycler view content based on the result of a diff check
      - `items: LiveData<List<T>>`: the list of model objects

- Launch Details Screen
  - 2 Endpoints: 
    - One launch details endpoint: Uses the same title field fetched in main screen, fetches the title content in Launch Details Screen.
    - One rocket details endpoint: Fetches the rocket name



## Other

### Highlights: A preview of Grouping, sorting and filtering algorithms
![highlights](https://i.ibb.co/44cqbh9/highlight.png)

### A Screenshot
![Screenshot](https://i.ibb.co/r4gkY0t/ss.png)


### List of Dependencies

```
dependencies {
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    kapt "com.android.databinding:compiler:3.1.4"

    // Versions
    def lifecycle_version = '2.4.0'

    // Jetpack Core
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-service:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"

    // Jetpack Navigation
    implementation 'androidx.navigation:navigation-fragment-ktx:2.3.5'
    implementation 'androidx.navigation:navigation-ui-ktx:2.3.5'

    // UI
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.1'
    implementation "androidx.cardview:cardview:1.0.0"

    // Dependency Injection - Dagger
    kapt "com.google.dagger:dagger-compiler:2.40"
    kapt "com.google.dagger:dagger-android-processor:2.28.3"

    // Dependency Injection - Hilt
    implementation "com.google.dagger:hilt-android:2.40"
    kapt "com.google.dagger:hilt-compiler:2.40"
    implementation "androidx.hilt:hilt-navigation-fragment:1.0.0"
    implementation "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"


    // Persistence - AndroidX / Data Store
    implementation "androidx.datastore:datastore-preferences:1.0.0"

    // Network - Retrofit
    implementation "com.squareup.okhttp3:okhttp:5.0.0-alpha.2"
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-moshi:2.9.0"
    implementation "com.squareup.moshi:moshi-kotlin:1.12.0"

    // Network Debugging - OkHttpInterceptor
    implementation "com.localebro:okhttpprofiler:1.0.8"

    // Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"

    // Picasso - Cached Media Retrieval
    implementation 'com.squareup.picasso:picasso:2.71828'
}
```
