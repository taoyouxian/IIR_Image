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
public class IndexMovieRunner implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(IndexMainRunner.class);

    @Value("${Index.Movie.Path}")
    private String indexPath;

    @Value("${Scan.Movie.Path}")
    private String scanPath;

    @Override
    public void run(ApplicationArguments applicationArguments) {
        System.out.println("IndexMovieRunner ： " + DateUtil.formatTime(System.currentTimeMillis()));
        IndexEntry indexEntry = new IndexEntry(indexPath, scanPath);
        TermSearch index = new TermSearch(indexPath, scanPath);
        index.creatIndex();
        IndexFactory.Instance().cacheTermIndex(indexEntry, index);
        System.out.println("IndexMovie Created: " + DateUtil.formatTime(System.currentTimeMillis()));
    }

    public void run(String indexPath, String scanPath) {
        System.out.println("IndexMovieRunner ： " + DateUtil.formatTime(System.currentTimeMillis()));
        IndexEntry indexEntry = new IndexEntry(indexPath, scanPath);
        TermSearch index;
        index = new TermSearch(indexPath, scanPath);
        index.creatIndex();
        IndexFactory.Instance().cacheTermIndex(indexEntry, index);
        System.out.println("IndexMovie Created: " + DateUtil.formatTime(System.currentTimeMillis()));
    }
}
