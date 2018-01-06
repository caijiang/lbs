package me.jiangcai.lbs.api;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.time.Duration;

/**
 * @author CJ
 */
@Data
public class RouteCost {

    private final String distanceAsText;
    private final int distanceAsMeter;
    private final String durationAsText;
    //    private int durationAsSecond;
    private final Duration duration;

    public int getDurationAsSecond() {
        return (int) duration.getSeconds();
    }

    public static RouteCost ofBaidu(JsonNode node) {
        JsonNode distance = node.get("distance");
        JsonNode duration = node.get("duration");

        RouteCost cost = new RouteCost(
                distance.get("text").asText()
                , distance.get("value").asInt()
                , duration.get("text").asText()
                , Duration.ofSeconds(duration.get("value").asLong())
        );
        return cost;
    }
}
