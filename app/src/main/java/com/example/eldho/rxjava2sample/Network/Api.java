package com.example.eldho.rxjava2sample.Network;


import com.example.eldho.rxjava2sample.Model.PostResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {

    @GET("posts")
    Observable<List<PostResponse>> getPosts(); /**Observable class emits data's*/
}
