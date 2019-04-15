package com.shgbit.bailiff.network;


import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author:xushun on 2018/6/30
 * description : 创建client
 */

public class RxRestClient {
    private static final WeakHashMap<String, Object> PARAMS = RxRestCreator.getParams();
    private final String URL;
    private final RequestBody BODY;

    RxRestClient(String url,
                 Map<String, Object> parms,
                 RequestBody body) {
        this.URL = url;
        PARAMS.putAll(parms);

        this.BODY = body;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RxRestCreator.getRxRestService();
        Observable<String> observable = null;
        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
//            case PUT_RAW:
//                observable = service.putRaw(URL, BODY);
//                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
//            case UPLOAD:
//                final RequestBody requestBody =
//                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
//                final MultipartBody.Part body =
//                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
//                call = service.upload(URL, body);
//                break;
            default:
                break;
        }
        return observable;

    }

    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }


}
