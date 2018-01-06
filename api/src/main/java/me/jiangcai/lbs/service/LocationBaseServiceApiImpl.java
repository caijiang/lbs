package me.jiangcai.lbs.service;

import com.fasterxml.jackson.databind.JsonNode;
import me.jiangcai.lbs.api.BaiduAuthorization;
import me.jiangcai.lbs.api.Coordinate;
import me.jiangcai.lbs.api.EntityOwner;
import me.jiangcai.lbs.api.Location;
import me.jiangcai.lbs.api.LocationBaseServiceApi;
import me.jiangcai.lbs.api.RouteCost;
import me.jiangcai.lbs.api.RouteType;
import me.jiangcai.lbs.service.http.JsonHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author CJ
 */
@Service
public class LocationBaseServiceApiImpl implements LocationBaseServiceApi {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public RouteCost routeCost(EntityOwner owner, RouteType type, Location origin, Location destination) throws IOException {
        BaiduAuthorization authorization = fetchAuthorization(owner);
        Coordinate originCoordinate = fetchCoordinate(authorization, origin);
        Coordinate destinationCoordinate = fetchCoordinate(authorization, destination);

        try (CloseableHttpClient client = requestClient()) {
            StringBuilder urlBuilder = new StringBuilder("http://api.map.baidu.com/routematrix/v2/")
                    .append(type.name());
            urlBuilder.append("?");
            urlBuilder.append("origins=").append(URLEncoder.encode(toHttpParams(originCoordinate), "UTF-8"));
            urlBuilder.append("&destinations=").append(URLEncoder.encode(toHttpParams(destinationCoordinate), "UTF-8"));
            urlBuilder.append("&output=json&ak=").append(authorization.getServerKey());

//            System.out.println(urlBuilder);
            JsonNode node = client.execute(new HttpGet(urlBuilder.toString()), new JsonHandler());
            int status = node.get("status").intValue();
            if (status != 0) {
                // http://lbsyun.baidu.com/index.php?title=webapi/guide/webservice-geocoding#.E8.BF.94.E5.9B.9E.E7.A0.81.E7.8A.B6.E6.80.81.E8.A1.A8
                throw new IllegalStateException("不可识别的响应码：" + status);
            }

            JsonNode jsonResult = node.get("result");
            return RouteCost.ofBaidu(jsonResult.get(0));
        }
    }

    private String toHttpParams(Coordinate... coordinate) {
        return Stream.of(coordinate)
                .map(coordinate1 -> BigDecimal.valueOf(coordinate1.getLatitude()) + "," + BigDecimal.valueOf(coordinate1.getLongitude()))
                .collect(Collectors.joining("|"));
    }

    private Coordinate fetchCoordinate(BaiduAuthorization authorization, Location location) throws IOException {
        if (location.getBaiduCoordinate() != null)
            return location.getBaiduCoordinate();
        try (CloseableHttpClient client = requestClient()) {
            StringBuilder urlBuilder = new StringBuilder("http://api.map.baidu.com/geocoder/v2/?");
            urlBuilder.append("address=").append(URLEncoder.encode(location.getAddress(), "UTF-8"));
            urlBuilder.append("&output=json&ak=")
                    .append(authorization.getServerKey());

            JsonNode node = client.execute(new HttpGet(urlBuilder.toString()), new JsonHandler());
            int status = node.get("status").intValue();
            if (status != 0) {
                // http://lbsyun.baidu.com/index.php?title=webapi/guide/webservice-geocoding#.E8.BF.94.E5.9B.9E.E7.A0.81.E7.8A.B6.E6.80.81.E8.A1.A8
                throw new IllegalStateException("不可识别的响应码：" + status);
            }
            JsonNode jsonResult = node.get("result");
            JsonNode jsonLocation = jsonResult.get("location");
            return new Coordinate(jsonLocation.get("lng").asDouble(), jsonLocation.get("lat").asDouble());
        }
    }


    private BaiduAuthorization fetchAuthorization(EntityOwner owner) {
        if (owner != null)
            return owner.getBaiduAuthorization();
        return applicationContext.getBean(EntityOwner.class).getBaiduAuthorization();
    }


    private CloseableHttpClient requestClient() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder = builder.setDefaultRequestConfig(RequestConfig.custom()
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(30000)
                .setSocketTimeout(300000)
                .build());
//        if (environment.acceptsProfiles("test")) {
//            builder.setSSLHostnameVerifier(new NoopHostnameVerifier());
//        }

        return builder.build();
    }
}
