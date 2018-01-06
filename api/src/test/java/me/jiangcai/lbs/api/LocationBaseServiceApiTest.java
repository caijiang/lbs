package me.jiangcai.lbs.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author CJ
 */
public class LocationBaseServiceApiTest extends AbstractApiTest {

    @Autowired
    private LocationBaseServiceApi locationBaseServiceApi;

    @Test
    public void go() throws IOException {
        System.out.println(locationBaseServiceApi);

        RouteCost cost = locationBaseServiceApi.routeCost(null, RouteType.driving, getLocation1(), getLocation2());
        System.out.println(cost);

        RouteCost cost2 = locationBaseServiceApi.routeCost(null, RouteType.riding, getLocation1(), getLocation2());
        System.out.println(cost2);
    }

    private Location getLocation2() {
        return new Location() {
            @Override
            public String getProvince() {
                return "浙江省";
            }

            @Override
            public String getPrefecture() {
                return "杭州市";
            }

            @Override
            public String getCountry() {
                return null;
            }

            @Override
            public String getStreetName() {
                return null;
            }

            @Override
            public String getStreetNumber() {
                return null;
            }

            @Override
            public String getBuildingCell() {
                return null;
            }

            @Override
            public Coordinate getBaiduCoordinate() {
                return null;
            }
        };
    }

    private Location getLocation1() {
        return new Location() {
            @Override
            public String getProvince() {
                return "浙江省";
            }

            @Override
            public String getPrefecture() {
                return "温州市";
            }

            @Override
            public String getCountry() {
                return null;
            }

            @Override
            public String getStreetName() {
                return null;
            }

            @Override
            public String getStreetNumber() {
                return null;
            }

            @Override
            public String getBuildingCell() {
                return null;
            }

            @Override
            public Coordinate getBaiduCoordinate() {
                return null;
            }
        };
    }

}