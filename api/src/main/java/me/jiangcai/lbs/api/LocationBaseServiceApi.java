package me.jiangcai.lbs.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 基于地址服务可提供的API
 *
 * @author CJ
 */
public interface LocationBaseServiceApi {

    ObjectMapper objectMapper = new ObjectMapper();

    RouteCost routeCost(EntityOwner owner, RouteType type, Location origin, Location destination) throws IOException;


}
