package com.aladin.project.CommonUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *  Http 통신 공통화
 * */

@Slf4j
public class HttpConnectModule {

    public String getEntity(String reqUrl) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(reqUrl);
        //oracle 자율 트랜잭션
        HttpResponse response = client.execute(request);

        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity, "UTF-8");
        log.info("result ::: " + result);

        return null;
    }
}
