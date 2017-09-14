package me.gzj.utils.http;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Map;

public class HttpUtil {
    private final static OkHttpClient client = new OkHttpClient
            .Builder()
            .addInterceptor(new HeaderInterceptor())
//            .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost", 1086)))
            .build();

    public static URL getUrl(String url, Map<String, String> queryParameter) throws Exception {
        URI uri = new URI(url);
        HttpUrl.Builder httpUrlBuilder = new HttpUrl.Builder()
                .scheme(uri.getScheme())
                .host(uri.getHost());

        if (uri.getPort() != -1) {
            httpUrlBuilder.port(uri.getPort());
        }

        String path = uri.getPath();
        if (path.length() > 0 && path.startsWith("/")) {
            httpUrlBuilder.addPathSegments(path.substring(1, path.length()));
        }

        httpUrlBuilder.fragment(uri.getFragment());

        httpUrlBuilder.query(uri.getQuery());
        if (queryParameter != null && queryParameter.size() > 0) {
            for (Map.Entry<String, String> entry : queryParameter.entrySet()) {
                httpUrlBuilder.setQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        return httpUrlBuilder.build().url();
    }

    public static Response get(URL url) throws IOException {
        return get(url.toString());
    }

    public static Response get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        return client.newCall(request).execute();
    }

    public static long download(String url, String filename) throws IOException {
        long byteCount = 0;
        try (Response response = get(url)) {
            if (response.isSuccessful()) {
                try (InputStream input = response.body().byteStream()) {
                    try (ReadableByteChannel channel = Channels.newChannel(input)) {
                        try (FileOutputStream output = new FileOutputStream(filename)) {
                            byteCount = output.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
                        }
                    }
                }
            }
        }
        return byteCount;
    }
}
