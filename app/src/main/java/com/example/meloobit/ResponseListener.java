package com.example.meloobit;

import com.example.meloobit.models.MelobitResponse;

public interface ResponseListener {
    void didFetch(MelobitResponse response, String status);
    void didError(String status);
}
