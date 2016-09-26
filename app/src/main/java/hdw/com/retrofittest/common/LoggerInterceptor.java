//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hdw.com.retrofittest.common;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okio.Buffer;


/**
 * 自定义的一个日志过滤器
 *
 * @author zengyi
 *         create at 2016/9/19 16:08
 */

public class LoggerInterceptor implements Interceptor {

    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = "zengyi";
        }

        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        this.logForRequest(request);
        Response response = chain.proceed(request);
        return this.logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            LogUtils.e(this.tag, "========response\'log=======");
            Builder e = response.newBuilder();
            Response clone = e.build();
            LogUtils.e(this.tag, "url : " + clone.request().url());
            LogUtils.e(this.tag, "code : " + clone.code());
            LogUtils.e(this.tag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message())) {
                LogUtils.e(this.tag, "message : " + clone.message());
            }

            if (this.showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        LogUtils.e(this.tag, "responseBody\'s contentType : " + mediaType.toString());
                        if (this.isText(mediaType)) {
                            String resp = body.string();
                            LogUtils.e(this.tag, "responseBody\'s content : " + resp);
                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        }

                        LogUtils.e(this.tag, "responseBody\'s content :  maybe [file part] , too large too print , ignored!");
                    }
                }
            }

            LogUtils.e(this.tag, "========response\'log=======end");
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String e = request.url().toString();
            Headers headers = request.headers();
            LogUtils.e(this.tag, "========request\'log=======");
            LogUtils.e(this.tag, "method : " + request.method());
            LogUtils.e(this.tag, "url : " + e);
            if (headers != null && headers.size() > 0) {
                LogUtils.e(this.tag, "headers : " + headers.toString());
            }

            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    LogUtils.e(this.tag, "requestBody\'s contentType : " + mediaType.toString());
                    if (this.isText(mediaType)) {
                        LogUtils.e(this.tag, "requestBody\'s content : " + this.bodyToString(request));
                    } else {
                        LogUtils.e(this.tag, "requestBody\'s content :  maybe [file part] , too large too print , ignored!");
                    }
                }
            }

            LogUtils.e(this.tag, "========request\'log=======end");
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    private boolean isText(MediaType mediaType) {
        return mediaType.type() != null && mediaType.type().equals("text") || mediaType.subtype() != null && (mediaType.subtype().equals("json") || mediaType.subtype().equals("xml") || mediaType.subtype().equals("html") || mediaType.subtype().equals("webviewhtml"));
    }

    private String bodyToString(Request request) {
        try {
            Request e = request.newBuilder().build();
            Buffer buffer = new Buffer();
            e.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException var4) {
            return "something error when show requestBody.";
        }
    }
}
