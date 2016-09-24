package com.github.zeng1990java.retrofit2.adapter.bolts.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.github.zeng1990java.retrofit2.adapter.bolts.BoltsCallAdapterFactory;
import com.github.zeng1990java.retrofit2.adapter.bolts.sample.api.GankApi;
import com.github.zeng1990java.retrofit2.adapter.bolts.sample.model.Gank;
import com.github.zeng1990java.retrofit2.adapter.bolts.sample.model.GankList;

import java.util.List;

import bolts.Continuation;
import bolts.Task;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BoltsRetrofit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankApi.BASE_API)
                .addCallAdapterFactory(BoltsCallAdapterFactory.createWithExecutor(Task.BACKGROUND_EXECUTOR))
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
                    Log.d(TAG, "then: faulted");
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
                    Log.d(TAG, "then: faulted");
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

        gankApi.getGankList().onSuccess(new Continuation<GankList, List<Gank>>() {
            @Override
            public List<Gank> then(Task<GankList> task) throws Exception {
                Log.d(TAG, "then onSuccess Thread: "+Thread.currentThread().getName());
                return task.getResult().getResults();
            }
        }).onSuccess(new Continuation<List<Gank>, Void>() {
            @Override
            public Void then(Task<List<Gank>> task) throws Exception {
                Log.d(TAG, "then: "+Thread.currentThread().getName());
                List<Gank> result = task.getResult();
                for (Gank gank : result) {
                    Log.d(TAG, "then: "+gank.toString());
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }
}
