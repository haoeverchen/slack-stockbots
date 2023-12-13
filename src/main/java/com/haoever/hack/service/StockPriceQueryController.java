package com.haoever.hack.service;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.vt.hacks.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.slack.api.Slack;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;

import com.slack.api.methods.response.chat.ChatPostMessageResponse;

@RestController
public class StockPriceQueryController {

    @Autowired
    Environment env;
    private static final Logger log =
        LoggerFactory.getLogger(StockPriceQueryController.class);


    @RequestMapping(
        value = "/STOCK_PRICE_QUERY",
        consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE },
        produces = "text/plain")
    public String stockManager(
        @RequestParam MultiValueMap request) {
        try {
            return doPost(request);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return "Something went wrong!";
        }
    }


    public String doPost(MultiValueMap request)
        throws IOException, SlackApiException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        // set timeout
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(
            10000);
        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(
            10000);
        String API_KEY = env.getProperty("API_KEY");
        String url =
            "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol={symbol}&apikey=" + API_KEY;

        log.info("[StockPriceQueryController] request :{} ",
            JsonUtils.toJson(request));
        String stock = (String)((List)request.get("text")).get(0);
        String resp = restTemplate.getForObject(url, String.class, stock);
        log.info("resp: {}", resp);
        Map<String, Object> res = JsonUtils.toObject(resp, Map.class);
        String lastDate = ((Map<String, String>)res.get("Meta Data")).get(
            "3. Last Refreshed");
        Map<String, String> target =
            (Map<String, String>)((Map<String, Object>)res.get(
                "Time Series (Daily)")).get(lastDate);
        StringBuilder sb =
            new StringBuilder("`Open price`: ").append(target.get("1. open"))
                .append("\r").append("`Highest price`: ")
                .append(target.get("2. high")).append("\r")
                .append("`Lowest price`: ").append(target.get("3. low"))
                .append("\r").append("`Close price`: ")
                .append(target.get("4. close")).append("\r")
                .append("`Volume`: ").append(target.get("5. volume"));

        Slack slack = Slack.getInstance();
        String TOKEN = env.getProperty("TOKEN");
        String CHANNEL = env.getProperty("CHANNEL");

        MethodsClient methods = slack.methods(TOKEN);
        ChatPostMessageResponse response = slack.methods(TOKEN).chatPostMessage(
            req -> req.channel(CHANNEL).blocks(asBlocks(section(
                    section -> section.text(markdownText(
                        "*" + lastDate + " " + stock + "* :sammy:\r" + sb.toString()))),
                divider())));
        return "";
    }
}
