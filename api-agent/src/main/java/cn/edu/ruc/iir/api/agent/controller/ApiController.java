package cn.edu.ruc.iir.api.agent.controller;

import cn.edu.ruc.iir.api.agent.domain.*;
import cn.edu.ruc.iir.api.agent.index.FileSearch;
import cn.edu.ruc.iir.api.agent.index.IndexEntry;
import cn.edu.ruc.iir.api.agent.index.IndexFactory;
import cn.edu.ruc.iir.api.agent.index.TermSearch;
import cn.edu.ruc.iir.api.agent.python.ImageRetrival;
import cn.edu.ruc.iir.api.agent.service.StarServiceI;
import cn.edu.ruc.iir.api.agent.util.DateUtil;
import cn.edu.ruc.iir.api.agent.util.FileUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class ApiController {
    private Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Value("${upload}")
    private String uploadDir;

    @Value("${Index.Main.Path}")
    private String INDEX_MAIN_PATH;

    @Value("${Scan.Main.Path}")
    private String SCAN_MAIN_PATH;

    @Value("${Index.Tv.Path}")
    private String INDEX_TV_PATH;

    @Value("${Scan.Tv.Path}")
    private String SCAN_TV_PATH;

    @Value("${Index.Movie.Path}")
    private String INDEX_MOVIE_PATH;

    @Value("${Scan.Movie.Path}")
    private String SCAN_MOVIE_PATH;

    @Value("${file.length}")
    private String fileLength;

    @Autowired
    protected StarServiceI starServiceI;

    @Autowired
    protected ImageRetrival imageRetrival;

    @RequestMapping(value = "score")
    public String getScore(@RequestParam("no") String no) throws Exception {
        String res = "";
        if (no.length() > 0) {
            res = acGetScore(no);
        } else {
            Json j = new Json();
            j.setState(3);
            j.setMsg("No valid student no");
            res = JSON.toJSONString(j);
        }
        return res;
    }

    @RequestMapping(value = "advice")
    public String addAdvice(@RequestParam("no") String no, @RequestParam("content") String content) throws Exception {
        String res = "";
        if (no.length() > 0 && content.length() > 0) {
            res = acAddAdvice(no, content);
        } else {
            Json j = new Json();
            j.setState(3);
            j.setMsg("No valid student no or content");
            res = JSON.toJSONString(j);
        }
        return res;
    }

    // no use
    private String acAddAdvice(String no, String content) throws Exception {
        String advicePath = System.getProperty("advice.path");
        if (!advicePath.endsWith("/")) {
            advicePath += "/";
        }
        advicePath += no + ".txt";
        File f = new File(advicePath);
        if (!f.exists()) {
            f.mkdir();
        }
        Json j = new Json();
        try {
            try (BufferedWriter adviceWriter = new BufferedWriter(new FileWriter(advicePath, true))) {
                String uuid = UUID.randomUUID().toString();
                String time = DateUtil.formatTime(new Date());
                adviceWriter.write(uuid + "\t" + time + "\t" + content + "\n");
                adviceWriter.flush();
                j.setState(1);
            } catch (IOException e) {
                j.setMsg(e.getMessage());
                throw new Exception("Writing Advice File Path Error");
            }
        } catch (Exception er) {
            j.setMsg(er.getMessage());
            throw new Exception("Action acAddAdvice error");
        }
        return JSON.toJSONString(j);
    }

    // no use
    public String acGetScore(String no) throws Exception {
        String[] caption = {"第3周", "第4周",
                "第5周", "第6周", "第7周", "第一次课后作业", "第9周", "第10周", "第11周", "第12周", "第13周", "第14周", "第二次课后作业", "综合实践项目"};
        String scorePath = System.getProperty("score.path");
        Json j = new Json();
        boolean flag = false;
        try {
            try (BufferedReader scoreReader = new BufferedReader(new FileReader(scorePath))) {
                String line;
                String[] lines;
                String stuScore = "";
                StringBuffer sb = new StringBuffer();
                while ((line = scoreReader.readLine()) != null) {
                    lines = line.split("\t");
                    if (lines[1].equals(no)) {
                        flag = true;
                        stuScore = lines[2];
                        String[] score = stuScore.split(",");
                        for (int i = 0; i < score.length; i++) {
                            sb.append(caption[i] + ":" + score[i] + ",");
                        }
                        break;
                    }
                }
                if (flag) {
                    stuScore = sb.toString();
                    j.setDatas(stuScore.substring(0, stuScore.length() - 1));
                    j.setState(1);
                } else {
                    // no stuNo
                    j.setState(2);
                    j.setMsg("No such student no");
                }
            } catch (IOException e) {
                j.setMsg(e.getMessage());
                throw new Exception("Reading Score File Path Error");
            }
        } catch (Exception er) {
            j.setMsg(er.getMessage());
            throw new Exception("Action acGetScore error");
        }
        return JSON.toJSONString(j);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam(value = "file") MultipartFile file) throws RuntimeException {
        Json j = new Json();
        String res = "";
        if (file.isEmpty()) {
            j.setMsg("文件不能为空");
            res = JSON.toJSONString(j);
            return res;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = uploadDir;
        // 解决中文问题，liunx下中文路径，图片显示问题
        String prefix = UUID.randomUUID() + "";
        String targetName = prefix + suffixName;
        File dest = new File(filePath + targetName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            logger.info("上传成功后的文件路径为：" + fileName + "\t" + filePath + targetName);
            j.setDatas(fileName);
            j.setMsg(targetName);
            j.setState(1);
            res = JSON.toJSONString(j);
            System.out.println(DateUtil.formatTime(System.currentTimeMillis()));
            System.out.println("upload： " + file.getOriginalFilename());
            System.out.println();
            return res;
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        j.setMsg("文件上传失败");
        res = JSON.toJSONString(j);
        System.out.println(DateUtil.formatTime(System.currentTimeMillis()));
        System.out.println("upload error：");
        System.out.println();
        return res;
    }

    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public String uploadFiles(@RequestParam(value = "file") MultipartFile[] files) {
        Json j = new Json();
        String res = "";
        StringBuffer result = new StringBuffer();
        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i] != null) {
                    //调用上传方法
                    String fileName = executeUpload(files[i]);
                    result.append(fileName + ";");
                }
            }
            logger.info("上传成功后的文件名：" + result.toString());
            j.setDatas(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            j.setMsg("文件上传失败");
        }
        // todo post file path to python server
        res = JSON.toJSONString(j);
        return res;
    }

    /**
     * 提取上传方法为公共方法
     *
     * @param file
     * @return
     * @throws Exception
     */
    private String executeUpload(MultipartFile file) throws Exception {
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String fileName = UUID.randomUUID() + suffix;
        //服务端保存的文件对象
        File serverFile = new File(uploadDir + fileName);
        // 检测是否存在目录
        if (!serverFile.getParentFile().exists()) {
            serverFile.getParentFile().mkdirs();
        }
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);
        return fileName;
    }

    //多文件上传
    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();

                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => "
                            + e.getMessage();
                }
            } else {
                return "You failed to upload " + i
                        + " because the file was empty.";
            }
        }
        return "upload successful";
    }

    @RequestMapping(value = "pic")
    public String getSimilarStar(@RequestParam("param") String param) throws Exception {
        String res = "";
        Json j = new Json();
        List<SimilarStar> similarStarList = new ArrayList<>();
        if (param.length() == 0) {
            param = "1.jpg;save/";
            System.out.println("default: " + param);
        }
        if (param.length() > 0) {
            String similarRes = imageRetrival.getSimilarStar(param);
            if (similarRes.equalsIgnoreCase("false")) {
                j.setMsg("Search Error");
            } else if (similarRes.equalsIgnoreCase("exception")) {
                similarRes = "赵丽颖,ImageRetrieval/star/赵丽颖.jpg;杨洋,ImageRetrieval/star/杨洋.jpg";
                similarStarList = getSimilarStarList(similarRes);
                j.setMsg("Search Exception");
                j.setDatas(similarStarList);
            } else {
                j.setState(1);
                similarStarList = getSimilarStarList(similarRes);
                j.setDatas(similarStarList);
            }
            res = JSON.toJSONString(j);
        } else {
            j.setState(2);
            j.setMsg("No valid star param");
            res = JSON.toJSONString(j);
        }
        System.out.println(DateUtil.formatTime(System.currentTimeMillis()));
        System.out.println("pic： " + param);
        System.out.println();
        return res;
    }

    private List<SimilarStar> getSimilarStarList(String similarRes) {
        String[] stars = similarRes.split(";");
        List<SimilarStar> similarStarList = new ArrayList<>();
        for (String s : stars) {
            String[] star = s.split(",");
            SimilarStar similarStar = new SimilarStar(star[0], star[1]);
            similarStarList.add(similarStar);
        }
        return similarStarList;
    }

    @RequestMapping(value = "getHotStar")
    public String getHotStar() throws Exception {
        String res = "";
        Json j = new Json();
        List<HotStar> hotList = starServiceI.getHotStar();
        j.setDatas(hotList);
        j.setState(1);
        res = JSON.toJSONString(j);
        System.out.println(DateUtil.formatTime(System.currentTimeMillis()));
        System.out.println("getHotStar： " + hotList.size());
        System.out.println();
        return res;
    }

    @RequestMapping(value = "getStarInfo")
    public String getStarInfo(@RequestParam("starName") String starName) throws Exception {
        String res = "";
        Json j = new Json();
        BasicInfo basicInfo = starServiceI.getBasicinfo(starName);
        Intro intro = starServiceI.getIntro(starName);
        List<Experience> experience = starServiceI.getExperience(starName);
        List<Prize> prize = starServiceI.getPrize(starName);
        List<Relation> relation = starServiceI.getRelation(starName);
        List<Work> film = starServiceI.getFilm(starName);
        List<Work> tv = starServiceI.getTV(starName);
        StarInfo starInfo = new StarInfo(basicInfo, intro, experience);
        Star star = new Star(starInfo, prize, relation, film, tv);
        j.setDatas(star);
        j.setState(1);
        res = JSON.toJSONString(j);
        System.out.println(DateUtil.formatTime(System.currentTimeMillis()));
        System.out.println("getStarInfo： " + starName);
        System.out.println();
        return res;
    }

    @RequestMapping(value = "getMainIndex")
    public String getMainIndex(@RequestParam("index") String index) {
        String res = "";
        Json j = new Json();
        List<BasicInfo> basicInfoList = new ArrayList<>();
        if (index.length() > 0) {
            IndexEntry indexEntry = new IndexEntry(INDEX_MAIN_PATH, SCAN_MAIN_PATH);
            FileSearch fileSearch = IndexFactory.Instance().getIndex(indexEntry);
            List<String> documentPath = fileSearch.search(index);
            for (String path : documentPath) {
                File file = new File(path);
                String filename = file.getName();
                filename = filename.substring(0, filename.indexOf("_"));
                BasicInfo basicInfo = null;
                try {
                    basicInfo = starServiceI.getBasicinfo(filename, path);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                basicInfoList.add(basicInfo);
            }
        } else {
            List<File> files = FileUtil.listAllFiles(SCAN_MAIN_PATH);
            BasicInfo basicInfo = null;
            for (int i = 0; i < Integer.valueOf(fileLength); i++) {
                File file = files.get(i);
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
        }
        j.setDatas(basicInfoList);
        j.setState(1);
        res = JSON.toJSONString(j);
        System.out.println(DateUtil.formatTime(System.currentTimeMillis()));
        System.out.println("getIndexStar： " + index.length() + ", " + basicInfoList.size() + ", " + index);
        System.out.println();
        return res;
    }

    @RequestMapping(value = "getTvIndex")
    public String getTvIndex(@RequestParam("index") String index) {
        String res = "";
        Json j = new Json();
        List<Movie> tvPath = new ArrayList<>();
        if (index.length() > 0) {
            IndexEntry indexEntry = new IndexEntry(INDEX_TV_PATH, SCAN_TV_PATH);
            TermSearch fileSearch = IndexFactory.Instance().getTermIndex(indexEntry);
            tvPath = getMovie(index, fileSearch);
        } else {
            System.out.println("getTvIndex error!");
        }
        j.setDatas(tvPath);
        j.setState(1);
        res = JSON.toJSONString(j);
        System.out.println(DateUtil.formatTime(System.currentTimeMillis()));
        System.out.println("getIndexStar： " + index);
        System.out.println();
        return res;
    }

    @RequestMapping(value = "getMovieIndex")
    public String getMovieIndex(@RequestParam("index") String index) {
        String res = "";
        Json j = new Json();
        List<Movie> moviePath = new ArrayList<>();
        if (index.length() > 0) {
            IndexEntry indexEntry = new IndexEntry(INDEX_MOVIE_PATH, SCAN_MOVIE_PATH);
            TermSearch movieSearch = IndexFactory.Instance().getTermIndex(indexEntry);
            moviePath = getMovie(index, movieSearch);
        } else {
            System.out.println("getMovieIndex error!");
        }
        j.setDatas(moviePath);
        j.setState(1);
        res = JSON.toJSONString(j);
        System.out.println(DateUtil.formatTime(System.currentTimeMillis()));
        System.out.println("getIndexStar： " + index);
        System.out.println();
        return res;
    }

    private List<Movie> getMovie(String index, TermSearch fileSearch) {
        List<String> documentPath = fileSearch.search(index);
        List<Movie> tvList = new ArrayList<>();
        for (String path : documentPath) {
            File file = new File(path);
            String filename = file.getName();
            System.out.println("getContent: " + filename + ", " + path);
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

}
