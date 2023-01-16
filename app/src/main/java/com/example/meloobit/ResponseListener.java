package com.example.meloobit;

import com.example.meloobit.models.MelobitData;
import com.example.meloobit.models.MelobitResponse;

import java.util.List;

public interface ResponseListener {
    void didFetch(List<MelobitData> list, String status);
    void didError(String status);
    void didFetch1(List<MelobitData> list, String status);
    void didError1(String status);

}
