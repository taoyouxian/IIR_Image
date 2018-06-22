package cn.edu.ruc.iir.api.agent.runner;

import cn.edu.ruc.iir.api.agent.util.BasicUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.api.agent.runner
 * @ClassName: MyApplicationRunner
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-15 16:47
 **/
@Component
public class CloudRunner implements ApplicationRunner {
    @Value("${cloudPython}")
    private String cloudPython;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println(cloudPython + " was started!");
        BasicUtil.genCloud(cloudPython);
    }
}
