package com.pix.http;

public class HttpRequestMode {

    public static final int DEFAULT_RETRY_NUMBER=0;
    public static final int DEFAULT_TIMEOUT=10000;

    HttpRequester mHttpRequester;
    ResultParser mParser;

    private int retryNumber;
    private int timeout;


    private HttpRequestMode(HttpRequester httpRequester, ResultParser iResultParser) {
        this(httpRequester,iResultParser,DEFAULT_RETRY_NUMBER,DEFAULT_TIMEOUT);
    }

    private HttpRequestMode(HttpRequester httpRequester, ResultParser iResultParser, int retryNumber) {
        this(httpRequester,iResultParser,retryNumber,DEFAULT_TIMEOUT);
    }
    private HttpRequestMode(HttpRequester httpRequester, ResultParser iResultParser, int retryNumber, int timeout) {
        this.mHttpRequester = httpRequester;
        this.mParser = iResultParser;
        this.mParser.setmHttpRequester(httpRequester);
        this.retryNumber = retryNumber;
        this.timeout=timeout;
    }

    public static HttpRequestMode build(HttpRequester httpRequester, ResultParser parser) {
        return new HttpRequestMode(httpRequester, parser);
    }
    public static HttpRequestMode build(HttpRequester httpRequester, ResultParser parser, int retryNumber) {
        return new HttpRequestMode(httpRequester, parser,retryNumber);
    }

    public int getRetryNumber() {
        return retryNumber;
    }

    public void setRetryNumber(int retryNumber) {
        this.retryNumber = retryNumber;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
