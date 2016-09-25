# retrofit-bolts-call-adapter


## Usage
```java
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}

compile 'com.github.zeng1990java:retrofit-bolts-call-adapter:1.0.0'

```

## Sample

### Step1
```java
public interface GankApi {

    String BASE_API = "http://gank.io/";

    @GET("api/data/Android/10/1")
    Task<GankList> getGankList();

    @GET("api/data/Android/15/1")
    Task<Response<GankList>> getGankListResponse();
}
```

### Step2
```java
Retrofit retrofit = new Retrofit.Builder()
              .baseUrl(GankApi.BASE_API)
              .addCallAdapterFactory(BoltsCallAdapterFactory.create())
              // or with custom Executor
              // .addCallAdapterFactory(BoltsCallAdapterFactory.createWithExecutor(Task.BACKGROUND_EXECUTOR))
              .addConverterFactory(GsonConverterFactory.create())
              .build();
      GankApi gankApi = retrofit.create(GankApi.class);

      gankApi.getGankList().continueWith(new Continuation<GankList, List<Gank>>() {
          @Override
          public List<Gank> then(Task<GankList> task) throws Exception {
              if (task.isCancelled()){
                  Log.d(TAG, "then: canceled");
                  return null;
              }else if (task.isFaulted()){
                  Log.d(TAG, "then: faulted: "+task.getError().getMessage());
                  return null;
              }else {
                  Log.d(TAG, "then: success");
                  GankList result = task.getResult();
                  Toast.makeText(getApplicationContext(),
                          "success: "+result.getResults().size(),
                          Toast.LENGTH_SHORT).show();
              }
              return task.getResult().getResults();
          }
      }, Task.UI_THREAD_EXECUTOR);

      gankApi.getGankListResponse().continueWith(new Continuation<Response<GankList>, Void>() {
          @Override
          public Void then(Task<Response<GankList>> task) throws Exception {
              if (task.isCancelled()){
                  Log.d(TAG, "then: canceled");
                  return null;
              }else if (task.isFaulted()){
                  Log.d(TAG, "then: faulted: "+task.getError().getMessage());
                  return null;
              }else {
                  Log.d(TAG, "then: success");
                  GankList result = task.getResult().body();
                  Toast.makeText(getApplicationContext(),
                          "response success: "+result.getResults().size(),
                          Toast.LENGTH_SHORT).show();
              }
              return null;

          }
      }, Task.UI_THREAD_EXECUTOR);
```


## License
Copyright (C) 2016 zeng1990java.
   https://github.com/zeng1990java

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
