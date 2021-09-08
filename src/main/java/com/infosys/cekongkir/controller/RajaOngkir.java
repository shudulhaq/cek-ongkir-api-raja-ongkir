package com.infosys.cekongkir.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RajaOngkir {
    private static final HttpClient httpclient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @GetMapping("/getCost")
    public String Cost(String key, String origin,String destination, String weight,String courier) throws IOException, InterruptedException{


        // form parameters
        Map<Object, Object> data = new HashMap<>();
        data.put("origin", origin);
        data.put("destination", destination);
        data.put("weight", weight);
        data.put("courier",courier);


        HttpRequest request = HttpRequest.newBuilder()
                .POST(ofFormData(data))
                .uri(URI.create("https://api.rajaongkir.com/starter/cost"))
                .setHeader("key",key)
                .header("Content-Type","application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpclient.send(request,HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

        return response.body();
    }
    public static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    @GetMapping("/getProvince")
    public String Province(String key, String id) throws IOException, InterruptedException{


        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.rajaongkir.com/starter/province?id="+id))
                .setHeader("key", key)
                .build();

        HttpResponse<String> response = httpclient.send(request,HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

        return response.body();
    }

    @GetMapping("/getCity")
    public String City(String key, String id, String province) throws IOException, InterruptedException{


        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.rajaongkir.com/starter/city?id="+ id +"&province="+province))
                .setHeader("key", key)
                .build();

        HttpResponse<String> response = httpclient.send(request,HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

        return response.body();
    }
}
