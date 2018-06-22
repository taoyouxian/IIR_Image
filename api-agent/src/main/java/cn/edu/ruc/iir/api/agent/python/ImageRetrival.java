package cn.edu.ruc.iir.api.agent.python;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.api.agent.python
 * @ClassName: ImageRetrival
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-16 16:43
 **/
@Component
public class ImageRetrival {
    @Value("${featureURL}")
    private String featureURL;

    public String getSimilarStar(String param) throws IOException {
        String result = "";
        BufferedReader in = null;
        try {
            String url = featureURL + param;
            System.out.println("URL: " + url);
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             if (result.equals("Yes"))
             return "Yes";
             else
             return "False";
             **/
            return result;
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！");
            result = "exception";
            System.out.println(e.getMessage());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                result = "exception";
                e2.printStackTrace();
            }
        }
        return result;

    }
}
