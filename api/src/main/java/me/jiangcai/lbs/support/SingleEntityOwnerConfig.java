package me.jiangcai.lbs.support;

import me.jiangcai.lbs.api.BaiduAuthorization;
import me.jiangcai.lbs.api.EntityOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 单商户配置
 * <ul>
 * <li>me.jiangcai.lbs.baidu.serverKey 百度的服务端key配置</li>
 * </ul>
 *
 * @author CJ
 */
@Configuration
public class SingleEntityOwnerConfig {

    @Autowired
    private Environment environment;

    @Bean
    public EntityOwner entityOwner() {
        return new EntityOwner() {
            @Override
            public BaiduAuthorization getBaiduAuthorization() {
                return new BaiduAuthorization(environment.getRequiredProperty("me.jiangcai.lbs.baidu.serverKey"));
            }
        };
    }

}
