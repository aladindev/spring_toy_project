package com.aladin.project.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import okhttp3.internal.http.HttpHeaders;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

@RestController
@Slf4j
public class CoinListController {

    @Value("${upbit.a.key}")
    private String aKey;

    @Value("${upbit.s.key}")
    private String sKey;

    @Value("${upbit.acntlist.url}")
    private String acntListUrl;

    @GetMapping("/acntList.do")
    public void acntList() {

        Algorithm algorithm = Algorithm.HMAC256(sKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", aKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;

        try {
            //Http 통신 객체 생성
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(acntListUrl);

            //요청 헤더 세팅
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            //응답값 가공
            HttpResponse response = client.execute(request);
            StringBuilder content = new StringBuilder();

            // inputStream으로 읽어들인다.
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            //버퍼 라인으로 읽어들인다.
            while(null != (line = br.readLine())) {
                content.append(line);
            }
            //JSONArray로 캐스팅
            Object obj = JSONValue.parse(content.toString());
            JSONArray jsonData = (JSONArray)obj;

            log.info("jsonData : " + jsonData);

        } catch (IOException e) {
            log.error("" + e.getStackTrace());
        }
    }

    @GetMapping("minChartList.do")
    public String minChartList() throws IOException {

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://api.upbit.com/v1/candles/minutes/1?market=KRW-GRS&count=1");

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity, "UTF-8");
            log.info("result ::: " + result);

        return result;
    }
}
