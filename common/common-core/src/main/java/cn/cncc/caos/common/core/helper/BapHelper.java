package cn.cncc.caos.common.core.helper;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BapHelper {

    @Resource
    private Environment env;

    public String getBapEnv() {
        if (StringUtils.isNotEmpty(System.getenv("caosEnv")))
            return System.getenv("caosEnv");
        return env.getProperty("caos.env");
    }
}