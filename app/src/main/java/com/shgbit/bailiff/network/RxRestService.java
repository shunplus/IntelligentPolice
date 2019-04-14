package com.shgbit.bailiff.network;

import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author:xushun on 2018/7/7y7
 * description :
 */

public interface RxRestService {
    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> parms);

    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> parms);

    @POST
    Observable<String> postRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap Map<String, Object> parms);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> parms);

    @Streaming
    @GET
    Observable<String> download(@Url String url, @QueryMap Map<String, Object> parms);

    @Multipart
    @GET
    Observable<String> upload(@Url String url, @Part MultipartBody.Part file);
}
