package cn.edu.ruc.iir.api.agent.index;

import cn.edu.ruc.iir.api.agent.domain.BasicInfo;
import cn.edu.ruc.iir.api.agent.domain.Movie;
import cn.edu.ruc.iir.api.agent.runner.IndexMainRunner;
import cn.edu.ruc.iir.api.agent.service.StarServiceI;
import cn.edu.ruc.iir.api.agent.service.StarServiceImpl;
import cn.edu.ruc.iir.api.agent.util.FileUtil;
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
@SpringBootTest(classes = IndexMainRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestFileSearch {

    @Autowired
    IndexMainRunner indexMainRunner;

    StarServiceI starServiceI = new StarServiceImpl();

//    String indexPath = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Index/Text/";
//    String scanPath = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Text/";

    String indexPath = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Index/Main/";
    String scanPath = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Main/";

    @Test
    public void testGetFile() {
        indexMainRunner.run(indexPath, scanPath);
        // index is exist
        IndexEntry indexEntry = new IndexEntry(indexPath, scanPath);
        FileSearch fileSearch = IndexFactory.Instance().getIndex(indexEntry);
        String index = "天秤座 中国 A型 颖宝 演员";
        List<String> documentPath = getContent(index, fileSearch);
        System.out.println(documentPath.toString());
    }

    @Test
    public void testGetFileByIndex() {
        IndexEntry indexEntry = new IndexEntry(indexPath, scanPath);
        System.out.println(indexEntry.toString());
        FileSearch index;
        index = new FileSearch(indexPath, scanPath);
        index.creatIndex();

        String index1 = "赵丽颖 型号";
        List<Movie> content = getContentList(index1, index);
        System.out.println(content.toString());
    }

    private List<Movie> getContentList(String index, FileSearch fileSearch) {
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
        return tvList;
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

    @Test
    public void testGetStarInfo() {
        indexMainRunner.run(null);
        // index is exist
        String SCAN_MAIN_PATH = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Main/";
        List<BasicInfo> basicInfoList = new ArrayList<>();
        List<File> files = FileUtil.listAllFiles(SCAN_MAIN_PATH);
        BasicInfo basicInfo = null;
        for (File file : files) {
            String path = file.getAbsolutePath();
            String name = file.getName();
            int end = name.lastIndexOf("_");
            String filename = name.substring(0, end);
            try {
                basicInfo = starServiceI.getBasicinfo(filename, path);
            } catch (Exception e) {
                System.out.println("getIndexStar error: " + e.getMessage());
            }
            basicInfoList.add(basicInfo);
        }
        System.out.println(basicInfoList.size());
    }

}
