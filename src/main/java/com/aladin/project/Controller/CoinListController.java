package com.aladin.project.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public ModelAndView acntList() {

        ModelAndView mv = null;

        Algorithm algorithm = Algorithm.HMAC256(sKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", aKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;

        try {
            //Http ?????? ?????? ??????
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(acntListUrl);

            //?????? ?????? ??????
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            //????????? ??????
            HttpResponse response = client.execute(request);
            StringBuilder content = new StringBuilder();

            // inputStream?????? ???????????????.
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            //?????? ???????????? ???????????????.
            while (null != (line = br.readLine())) {
                content.append(line);
            }
            //JSONArray??? ?????????
            Object obj = JSONValue.parse(content.toString());
            JSONArray jsonArray = (JSONArray) obj;

            log.info("jsonData : " + jsonArray);

            // jsonArray cast to map By GSON
            Gson gson = new Gson();
            List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

            Type listType = TypeToken.get(listMap.getClass()).getType();
            listMap = gson.fromJson(jsonArray.toJSONString(), new TypeToken<List<Map<String, Object>>>() {
            }.getType());

            //???????????? json
            String redirectUrl = "minChartList.do?ownCoins=";
            String coinListStr = null;
            JSONArray ownCoinArr = new JSONArray();


            int idx = 1;
            for (Map map : listMap) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("ownCoin"+(idx++), map.get("unit_currency") + "-" + map.get("currency"));
                ownCoinArr.add(jsonObj);
            }
            coinListStr = ownCoinArr.toJSONString();

            //JSON ?????? URL?????????
            redirectUrl += URLEncoder.encode(coinListStr, "UTF-8");

            RedirectView rv = new RedirectView(redirectUrl);
            mv = new ModelAndView(rv);

        } catch (IOException e) {
            log.error("" + e.getStackTrace());
        }
        return mv;
    }

    @GetMapping("minChartList.do") /*????????????*/
    public String minChartList(HttpServletRequest req) throws IOException {

        String ownCoins = URLDecoder.decode(req.getParameter("ownCoins"), "UTF-8");
        log.info("ownCoins :: " + ownCoins);

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://api.upbit.com/v1/candles/minutes/1?market=KRW-GRS&count=1");
        //oracle ?????? ????????????
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity, "UTF-8");
        log.info("result ::: " + result);

        return result;
    }
    //???????????? ?????? ??????
    @Bean
    public void connectHttp() {

    }
}
