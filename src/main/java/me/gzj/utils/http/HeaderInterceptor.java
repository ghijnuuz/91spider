package me.gzj.utils.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .removeHeader("User-Agent").header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                .removeHeader("Accept-Language").header("Accept-Language", "zh-CN,zh;q=0.8")
                .build();
        return chain.proceed(request);
    }
}
