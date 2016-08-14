package act.remote;

import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;
import org.osgl.http.H;
import org.osgl.util.C;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static act.remote.Protocol.*;
import static org.osgl.http.H.Method.*;

/**
 * Build the request to be sent out
 */
public class RequestBuilder extends H.Request<RequestBuilder> {

    protected H.Format contentType;
    protected UrlBuilder url;
    protected H.Method method;
    protected HeaderMap headers = new HeaderMap();
    protected Map<String, List<String>> params = C.newMap();
    protected Map<String, H.Cookie> cookies = C.newMap();
    protected byte[] body;

    /**
     * The encoding default value is {@link RequestPayloadEncoding#DEFAULT}
     */
    protected RequestPayloadEncoding encoding = RequestPayloadEncoding.DEFAULT;

    public RequestBuilder() {
        init();
    }

    public RequestBuilder(String url) {
        init();
        this.url = new UrlBuilder();
    }

    @Override
    protected Class<RequestBuilder> _impl() {
        return RequestBuilder.class;
    }

    @Override
    public H.Method method() {
        return method;
    }

    @Override
    public RequestBuilder method(H.Method method) {
        this.method = method;
        return this;
    }

    public RequestBuilder addHeader(String name, String val) {
        headers.add(httpString(name), val);
        return this;
    }

    public RequestBuilder addHeader(String name, long val) {
        headers.add(httpString(name), val);
        return this;
    }

    public RequestBuilder addHeader(String name, Collection<String> vals) {
        headers.addAll(httpString(name), vals);
        return this;
    }

    public RequestBuilder putHeader(String name, String val) {
        headers.put(httpString(name), val);
        return this;
    }

    public RequestBuilder putHeader(String name, long val) {
        headers.put(httpString(name), val);
        return this;
    }

    public RequestBuilder putHeader(String name, Collection<String> vals) {
        headers.putAll(httpString(name), vals);
        return this;
    }

    public RequestBuilder addCookie(H.Cookie cookie) {
        _setCookie(cookie.name(), cookie);
        return this;
    }

    @Override
    public String header(String s) {
        return headers.get(s, 0);
    }

    @Override
    public Iterable<String> headers(String s) {
        return headers.get(s);
    }

    @Override
    public String path() {
        return url.path();
    }

    @Override
    public String contextPath() {
        return "";
    }

    @Override
    public String query() {
        return null;
    }

    @Override
    public boolean secure() {
        Protocol p = url.protocol();
        return HTTPS == p || SFTP == p || WSS == p;
    }

    @Override
    protected String _ip() {
        return "127.0.0.1";
    }

    @Override
    protected void _initCookieMap() {
        // nothing need to be done here
    }

    @Override
    protected InputStream createInputStream() {
        if (null == body) {
            return null;
        }
        return new ByteArrayInputStream(body);
    }

    @Override
    public String paramVal(String s) {
        List<String> l = params.get(s);
        return null == l || l.isEmpty() ? null : l.get(0);
    }

    @Override
    public String[] paramVals(String s) {
        List<String> l = params.get(s);
        return null == l ? null : l.toArray(new String[l.size()]);
    }

    @Override
    public Iterable<String> paramNames() {
        return params.keySet();
    }

    public H.Format contentType() {
        return contentType;
    }

    public RequestBuilder contentType(H.Format format) {
        this.contentType = format;
        return this;
    }

    /**
     * Init a `RequestBuilder` instance with default values
     */
    private void init() {
        accept(H.Format.JSON);
        contentType(H.Format.JSON);
    }

    /**
     * Returns a `RequestBuilder` with specified URL and provisioned with {@link org.osgl.http.H.Method#GET get} method
     * @param url the remote service URL
     * @return a `RequestBuilder` instance
     */
    public static RequestBuilder get(String url) {
        return newBuilder(GET);
    }

    /**
     * Returns a `RequestBuilder` with specified URL and provisioned with {@link org.osgl.http.H.Method#POST post} method
     * @param url the remote service URL
     * @return a `RequestBuilder` instance
     */
    public static RequestBuilder post(String url) {
        return newBuilder(POST);
    }

    /**
     * Returns a `RequestBuilder` with specified URL and provisioned with {@link org.osgl.http.H.Method#PUT put} method
     * @param url the remote service URL
     * @return a `RequestBuilder` instance
     */
    public static RequestBuilder put(String url) {
        return newBuilder(PUT);
    }

    /**
     * Returns a `RequestBuilder` with specified URL and provisioned with {@link org.osgl.http.H.Method#DELETE delete} method
     * @param url the remote service URL
     * @return a `RequestBuilder` instance
     */
    public static RequestBuilder delete(String url) {
        return newBuilder(DELETE);
    }

    /**
     * Returns an new `RequestBuilder` instance
     * @return an new `RequestBuilder` instance
     */
    public static RequestBuilder newBuilder() {
        return new RequestBuilder();
    }

    /**
     * Create an new `RequestBuilder` instance with http method specified
     * @param method the http method to provision the request builder
     * @return the new request builder
     */
    public static RequestBuilder newBuilder(H.Method method) {
        return new RequestBuilder().method(method);
    }

    private static HttpString httpString(String s) {
        return new HttpString(s);
    }
}
