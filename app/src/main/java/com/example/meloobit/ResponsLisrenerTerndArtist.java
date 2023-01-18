package com.example.meloobit;

import com.example.meloobit.models.MelobitData;

import java.util.List;

public interface ResponsLisrenerTerndArtist {
    void didFetch(List<MelobitData> list, String status);
    void didError(String status);
}