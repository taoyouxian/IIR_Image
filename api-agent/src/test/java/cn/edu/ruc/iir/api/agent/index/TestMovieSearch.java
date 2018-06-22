package cn.edu.ruc.iir.api.agent.index;

import cn.edu.ruc.iir.api.agent.domain.Content;
import cn.edu.ruc.iir.api.agent.domain.Movie;
import cn.edu.ruc.iir.api.agent.runner.IndexMovieRunner;
import cn.edu.ruc.iir.api.agent.service.StarServiceI;
import cn.edu.ruc.iir.api.agent.service.StarServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.api.agent.index
 * @ClassName: TestFileSearch
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-20 11:17
 **/
@SpringBootTest(classes = IndexMovieRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMovieSearch {

    @Autowired
    IndexMovieRunner indexMovieRunner;

    StarServiceI starServiceI = new StarServiceImpl();

    String indexPath = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Index/Movie/";
    String scanPath = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Movie/";

//    String indexPath = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Index/Text/";
//    String scanPath = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Text/";

    @Test
    public void testGetFile() {
        indexMovieRunner.run(indexPath, scanPath);
        // index is exist
        IndexEntry indexEntry = new IndexEntry(indexPath, scanPath);
        FileSearch fileSearch = IndexFactory.Instance().getIndex(indexEntry);
        String index = "张梓琳 曹云金 丁春诚";
        List<String> documentPath = getContent(index, fileSearch);
        System.out.println(documentPath.toString());
    }

    @Test
    public void testGetFileByIndex() {
        TermSearch index = new TermSearch(indexPath, scanPath);
        index.creatIndex();
        String index1 = "赵丽颖";
        index.search(index1);
        List<String> documentPath = index.search(index1);
        System.out.println(documentPath.toString());
    }

    private List<String> getContent(String index, FileSearch fileSearch) {
        List<String> documentPath = fileSearch.search(index);
        List<Movie> tvList = new ArrayList<>();
        for (String path : documentPath) {
            File file = new File(path);
            String filename = file.getName();
            Movie tv = null;
            try {
                tv = starServiceI.getContent(filename, path);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            tvList.add(tv);
        }
        return documentPath;
    }


}
