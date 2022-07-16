package com.aladin.project.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class CoinAutoTradeController {

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
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(acntListUrl);

            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            log.info("entity to String : " + EntityUtils.toString(entity, "UTF-8"));

        } catch (IOException e) {
            log.error("" + e.getStackTrace());
        }
    }

}
