/**
 * CRON表达式 含义(https://blog.csdn.net/YyCarry/article/details/78585919)
 * "0 0 12 * * ?" 每天中午十二点触发
 * "0 15 10 ? * *" 每天早上10：15触发
 * "0 15 10 * * ?" 每天早上10：15触发
 * "0 15 10 * * ? *" 每天早上10：15触发
 * "0 15 10 * * ? 2005" 2005年的每天早上10：15触发
 * "0 * 14 * * ?" 每天从下午2点开始到2点59分每分钟一次触发
 * "0 0/5 14 * * ?" 每天从下午2点开始到2：55分结束每5分钟一次触发
 * "0 0/5 14,18 * * ?" 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
 * "0 0-5 14 * * ?" 每天14:00至14:05每分钟一次触发
 * "0 10,44 14 ? 3 WED" 三月的每周三的14：10和14：44触发
 * "0 15 10 ? * MON-FRI" 每个周一、周二、周三、周四、周五的10：15触发
 */
package cn.edu.ruc.iir.api.agent.timer;

import cn.edu.ruc.iir.api.agent.util.BasicUtil;
import cn.edu.ruc.iir.api.agent.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.api.agent.timer
 * @ClassName: ClockTiming
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-15 16:55
 **/
@Component
public class ClockTiming {
    @Value("${cloudPython}")
    private String cloudPython;

    /**
     * 定时器
     */
    @Scheduled(cron = "0 0 0 * * ?")//每天0点开始
    public void insertClock() {
        //业务逻辑
        System.out.println("当前时间是： " + DateUtil.formatTime(System.currentTimeMillis()));
        // todo function
    }

    @Scheduled(cron = "0 0 20 * * ?")//每天1点开始
    public void indexClock() {
        //业务逻辑
        System.out.println("Index时间是： " + DateUtil.formatTime(System.currentTimeMillis()));
        // todo function
    }

    @Scheduled(cron = "0 0/1 14,18 * * ?") //每天的下午2点至2：59和6点至6点59分两个时间段内每1分钟一次触发
    public void testClock() {
        System.out.println("===TestClock begin");
        System.out.println("当前时间是： " + DateUtil.formatTime(System.currentTimeMillis()));
        System.out.println("===TestClock end");
    }

    //    @Scheduled(cron = "0 0/1 * * * ?") //每分钟执行一次,这是cron表达式
    public void statusCheck() {
        System.out.println("每分钟执行一次。开始……");
        //statusTask.healthCheck();
        System.out.println("Now is：" + DateUtil.formatTime(System.currentTimeMillis()));
        System.out.println("每分钟执行一次。结束。");
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + DateUtil.formatTime(System.currentTimeMillis()));
    }

    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void fixCloudPic() {
        BasicUtil.genCloud(cloudPython);
    }

}
