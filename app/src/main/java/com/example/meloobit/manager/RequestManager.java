package com.example.meloobit.manager;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.meloobit.MainActivity;
import com.example.meloobit.R;
import com.example.meloobit.ResponseListener;
import com.example.meloobit.models.MelobitResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RequestManager {

    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api-beta.melobit.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Callmelobit callmelobit = retrofit.create(Callmelobit.class);
    public RequestManager(Context context) {
        this.context = context;
    }
    public void getFixture(ResponseListener listener) {
        Call<MelobitResponse> call = callmelobit.callmelobit();
        try {
            call.enqueue(new Callback<MelobitResponse>() {
                @Override
                public void onResponse(Call<MelobitResponse> call, Response<MelobitResponse> response) {
                    if (!response.isSuccessful()){
                        listener.didError(response.message());
                        return;
                    }
                    listener.didFetch(response.body().getResults(), response.message());
                }

                @Override
                public void onFailure(@NonNull Call<MelobitResponse> call, @NonNull Throwable t) {
                    listener.didError(t.getMessage());
                }
            });
        }catch (Exception e) {
            Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
        }

    }

    private interface Callmelobit{
        @GET("song/new/0/11")
        Call<MelobitResponse> callmelobit();
    }


}
