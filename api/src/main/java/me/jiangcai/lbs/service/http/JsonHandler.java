package me.jiangcai.lbs.service.http;

import com.fasterxml.jackson.databind.JsonNode;
import me.jiangcai.lbs.api.LocationBaseServiceApi;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;

import java.io.IOException;

/**
 * @author CJ
 */
public class JsonHandler extends AbstractResponseHandler<JsonNode> {

    @Override
    public JsonNode handleEntity(HttpEntity httpEntity) throws IOException {

        return LocationBaseServiceApi.objectMapper.readTree(httpEntity.getContent());
    }

}
