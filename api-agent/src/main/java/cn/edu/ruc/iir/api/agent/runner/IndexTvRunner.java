package cn.edu.ruc.iir.api.agent.runner;

import cn.edu.ruc.iir.api.agent.index.IndexEntry;
import cn.edu.ruc.iir.api.agent.index.IndexFactory;
import cn.edu.ruc.iir.api.agent.index.TermSearch;
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
 * @ClassName: MyApplicationRunner2
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-15 16:50
 **/
@Component
@Order(value = 2)
public class IndexTvRunner implements ApplicationRunner, Ordered {
    private Logger logger = LoggerFactory.getLogger(IndexMainRunner.class);

    @Value("${Index.Tv.Path}")
    private String indexPath;

    @Value("${Scan.Tv.Path}")
    private String scanPath;


    @Override
    public int getOrder() {
        System.out.println(2);
        return 2;//通过设置这里的数字来知道指定顺序
    }

    @Override
    public void run(ApplicationArguments var1) {
        System.out.println("IndexTv Created: " + DateUtil.formatTime(System.currentTimeMillis()));
        IndexEntry indexEntry = new IndexEntry(indexPath, scanPath);
        TermSearch index = new TermSearch(indexPath, scanPath);
        index.creatIndex();
        IndexFactory.Instance().cacheTermIndex(indexEntry, index);
        System.out.println("IndexMovie Created: " + DateUtil.formatTime(System.currentTimeMillis()));
    }

}
