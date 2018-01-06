package me.jiangcai.lbs.api;

import me.jiangcai.lbs.LBSApiConfig;
import me.jiangcai.lbs.support.SingleEntityOwnerConfig;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author CJ
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SingleEntityOwnerConfig.class, LBSApiConfig.class, AbstractApiTest.Config.class})
public abstract class AbstractApiTest {

    @Configuration
    @PropertySource("classpath:/lbs.properties")
    public static class Config {

    }

}
