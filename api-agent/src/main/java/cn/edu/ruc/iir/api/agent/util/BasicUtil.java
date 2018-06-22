package cn.edu.ruc.iir.api.agent.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.api.agent.util
 * @ClassName: BasicUtil
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-20 16:03
 **/
public class BasicUtil {


    public static void genCloud(String cloudPython) {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python " + cloudPython);// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println("Cloud Gen: " + line + DateUtil.formatTime(System.currentTimeMillis()));
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
