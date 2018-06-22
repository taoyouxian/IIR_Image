package cn.edu.ruc.iir.api.agent.runner;

import cn.edu.ruc.iir.api.agent.index.FileSearch;
import cn.edu.ruc.iir.api.agent.index.IndexEntry;
import cn.edu.ruc.iir.api.agent.index.IndexFactory;
import cn.edu.ruc.iir.api.agent.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.api.agent.runner
 * @ClassName: MyApplicationRunner1
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-15 16:48
 **/
@Component
@Order(value = 1)
public class IndexMainRunner implements ApplicationRunner, Ordered {
    private Logger logger = LoggerFactory.getLogger(IndexMainRunner.class);

    @Value("${Index.Main.Path}")
    private String indexPath;

    @Value("${Scan.Main.Path}")
    private String scanPath;


    @Override
    public int getOrder() {
        System.out.println(1);
        return 1;//通过设置这里的数字来知道指定顺序
    }

    @Override
    public void run(ApplicationArguments var1) {
        System.out.println("IndexMainRunner ： " + DateUtil.formatTime(System.currentTimeMillis()));
        IndexEntry indexEntry = new IndexEntry(indexPath, scanPath);
        System.out.println(indexEntry.toString());
        FileSearch index;
        index = new FileSearch(indexPath, scanPath);
        index.creatIndex();
        IndexFactory.Instance().cacheIndex(indexEntry, index);
        System.out.println("IndexMain Created: " + DateUtil.formatTime(System.currentTimeMillis()));
    }


    public void run(String indexPath, String scanPath) {
        System.out.println("IndexMainRunner ： " + DateUtil.formatTime(System.currentTimeMillis()));
        IndexEntry indexEntry = new IndexEntry(indexPath, scanPath);
        System.out.println(indexEntry.toString());
        FileSearch index;
        index = new FileSearch(indexPath, scanPath);
        index.creatIndex();
        IndexFactory.Instance().cacheIndex(indexEntry, index);
        System.out.println("IndexMain Created: " + DateUtil.formatTime(System.currentTimeMillis()));
    }

}
