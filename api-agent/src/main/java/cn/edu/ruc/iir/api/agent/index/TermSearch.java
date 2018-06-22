package cn.edu.ruc.iir.api.agent.index;

import cn.edu.ruc.iir.api.agent.util.FileUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Package: com.test.util
 * @ClassName: FileSearchDemo
 * @Description: 基于Lucene6.6.0的文件搜索demo
 * @author: taoyouxian
 * @date: Create in 2018-04-24 10:27
 **/
public class TermSearch {
    private Logger logger = LoggerFactory.getLogger(TermSearch.class);

    private String INDEX_PATH; // 存放Lucene索引文件的位置

    private String SCAN_PATH; // 需要被扫描的位置，测试的时候记得多在这下面放一些文件

    public TermSearch(String INDEX_PATH, String SCAN_PATH) {
        this.INDEX_PATH = INDEX_PATH;
        this.SCAN_PATH = SCAN_PATH;
    }

    /**
     * 创建索引
     */
    public void creatIndex() {
        IndexWriter indexWriter = null;
        try {
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));
            Analyzer analyzer = new IKAnalyzer(true);
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(directory, indexWriterConfig);
            indexWriter.deleteAll();// 清除以前的index
            // 获取被扫描目录下的所有文件，包括子目录
            List<File> files = FileUtil.listAllFiles(SCAN_PATH);
            logger.info("File num: " + files.size());
            for (int i = 0; i < files.size(); i++) {
                Document document = new Document();
                File file = files.get(i);
                document.add(new Field("content", FileUtil.readFile(file.getAbsolutePath()), TextField.TYPE_STORED));
                document.add(new Field("fileName", file.getName(), TextField.TYPE_STORED));
                document.add(new Field("filePath", file.getAbsolutePath(), TextField.TYPE_STORED));
                document.add(new Field("updateTime", file.lastModified() + "", TextField.TYPE_STORED));
                indexWriter.addDocument(document);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (indexWriter != null) indexWriter.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 搜索
     */
    public List<String> search(String keyWord) {
        DirectoryReader directoryReader = null;
        List<String> documentPath = new ArrayList<>();
        try {
            // 1、创建Directory
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));
            // 2、创建IndexReader
            directoryReader = DirectoryReader.open(directory);
            // 3、根据IndexReader创建IndexSearch
            IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
            // 4、创建搜索的Query
            Analyzer analyzer = new IKAnalyzer(true); // 使用IK分词

            // 简单的查询，创建Query表示搜索域为content包含keyWord的文档
            String[] fields = {"content"}; // 要搜索的字段，一般搜索时都不会只搜索一个字段
//            // 字段之间的与或非关系，MUST表示and，MUST_NOT表示not，SHOULD表示or，有几个fields就必须有几个clauses
            BooleanClause.Occur[] clauses = {BooleanClause.Occur.MUST};
            // MultiFieldQueryParser表示多个域解析， 同时可以解析含空格的字符串，如果我们搜索"上海 中国"
            Query multiFieldQuery = MultiFieldQueryParser.parse(keyWord, fields, clauses, analyzer);

            BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
//            queryBuilder.add(multiFieldQuery, BooleanClause.Occur.SHOULD);
            String[] keywords = keyWord.split(" ");
            for (String key : keywords) {
                Query termQuery = new TermQuery(new Term("content", key));// 词语搜索,完全匹配,搜索具体的域
                queryBuilder.add(termQuery, BooleanClause.Occur.MUST);
            }
            BooleanQuery query = queryBuilder.build(); // 这才是最终的query
            TopDocs topDocs = indexSearcher.search(query, 100); // 搜索前100条结果
            // 5、根据searcher搜索并且返回TopDocs
            System.out.println("共找到匹配处：" + topDocs.totalHits); // totalHits和scoreDocs.length的区别还没搞明白
            // 6、根据TopDocs获取ScoreDoc对象
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共找到匹配文档数：" + scoreDocs.length);
            QueryScorer scorer = new QueryScorer(multiFieldQuery, "content");
            // 自定义高亮代码
            SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span style=\"background:red\">", "</span>");
            Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
            highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 7、根据searcher和ScoreDoc对象获取具体的Document对象
                Document document = indexSearcher.doc(scoreDoc.doc);
//                TokenStream tokenStream = new SimpleAnalyzer().tokenStream("content", new StringReader(content));
//                TokenSources.getTokenStream("content", tvFields, content, analyzer, 100);
//                TokenStream tokenStream = TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(), scoreDoc.doc, "content", document, analyzer);
//                System.out.println(highlighter.getBestFragment(tokenStream, content));
//                System.out.println("-----------------------------------------");
//                System.out.println(document.get("fileName") + ":" + document.get("filePath"));
//                System.out.println(highlighter.getBestFragment(analyzer, "content", document.get("content")));
//                System.out.println("");
                documentPath.add(document.get("filePath"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (directoryReader != null) directoryReader.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return documentPath;
    }

}
