package me.jiangcai.lbs.api;

/**
 * 地点，可以很笼统也可以很精细；即所有选项都是可以返回null
 *
 * @author CJ
 */
public interface Location {

    /**
     * @return 地址描述
     */
    default String getAddress() {
        StringBuilder addressBuilder = new StringBuilder();
        if (getProvince() != null) {
            addressBuilder.append(getProvince());
        }
        if (getPrefecture() != null) {
            addressBuilder.append(getPrefecture());
        }
        if (getCountry() != null) {
            addressBuilder.append(getCountry());
        }
        if (getStreetName() != null) {
            addressBuilder.append(getStreetName());
        }
        if (getStreetNumber() != null) {
            addressBuilder.append(getStreetNumber());
        }
        if (getBuildingCell() != null) {
            addressBuilder.append(getBuildingCell());
        }
        return addressBuilder.toString();
    }

    /**
     * @return 省(province)/直辖市(municipality)/自治区(autonomous region)/特别行政区(special administrative region/SAR)
     */
    String getProvince();

    /**
     * @return 地级市(prefecture_level city)/地区(prefecture)/自治州(autonomous prefecture)/盟(league)
     */
    String getPrefecture();

    /**
     * @return 县(county)/自治县(autonomous county)/县级市(county-level  city)/市辖区(district)/旗(banner)
     * /自治旗(autonomous banner)/林区(forestry area)/特区(special district)
     */
    String getCountry();

    /**
     * @return 街道名称
     */
    String getStreetName();

    /**
     * @return 街道
     */
    String getStreetNumber();

    /**
     * @return 楼宇及单元号
     */
    String getBuildingCell();

    /**
     * @return 百度坐标系坐标
     */
    Coordinate getBaiduCoordinate();


}
