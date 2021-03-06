package com.linguancheng.gdeiassistant.Pojo.HttpClient;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * 广东二师助手团队 林冠成 版权所有
 * All rights reserved © 2016 - 2018
 * Author:林冠成
 * Date:2018/11/13
 */
public class HttpClientSession {

    private CloseableHttpClient closeableHttpClient;

    private CookieStore cookieStore;

    public HttpClientSession() {

    }

    public HttpClientSession(CloseableHttpClient httpClient, CookieStore cookieStore) {
        this.closeableHttpClient = httpClient;
        this.cookieStore = cookieStore;
    }

    public CloseableHttpClient getCloseableHttpClient() {
        return closeableHttpClient;
    }

    public void setCloseableHttpClient(CloseableHttpClient closeableHttpClient) {
        this.closeableHttpClient = closeableHttpClient;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }
}
