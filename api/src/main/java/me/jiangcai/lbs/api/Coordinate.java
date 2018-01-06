package me.jiangcai.lbs.api;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 坐标
 *
 * @author CJ
 */
@Data
@Embeddable
public class Coordinate {

    /**
     * 经度
     */
    @Column(scale = 10, precision = 15)
    private final double longitude;
    /**
     * 维度
     */
    @Column(scale = 10, precision = 15)
    private final double latitude;

}
