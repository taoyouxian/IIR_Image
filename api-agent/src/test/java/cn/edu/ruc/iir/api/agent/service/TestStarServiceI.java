package cn.edu.ruc.iir.api.agent.service;

import cn.edu.ruc.iir.api.agent.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest(classes = StarServiceImpl.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestStarServiceI {
    @Autowired
    protected StarServiceI starServiceI;

    @Test
    public void testGetHotStar() throws Exception {
        List<HotStar> hotList = starServiceI.getHotStar();
        System.out.println(hotList.toString());
        for (HotStar hotStar : hotList) {
            System.out.println(hotStar.toString());
        }
    }

    @Test
    public void testGetBasicinfo() throws Exception {
        String starName = "赵丽颖";
        BasicInfo basicInfo = starServiceI.getBasicinfo(starName);
        System.out.println(basicInfo.toString());
    }

    @Test
    public void testGetIntro() throws Exception {
        String starName = "赵丽颖";
        Intro intro = starServiceI.getIntro(starName);
        System.out.println(intro.toString());
    }

    @Test
    public void testGetExperience() throws Exception {
        String starName = "赵丽颖";
        List<Experience> experienceList = starServiceI.getExperience(starName);
        System.out.println(experienceList.size());
        System.out.println(experienceList.toString());
    }

    @Test
    public void testGetPrize() throws Exception {
        String starName = "赵丽颖";
        List<Prize> prize = starServiceI.getPrize(starName);
        System.out.println(prize.size());
        System.out.println(prize.toString());
    }

    @Test
    public void testGetTv() throws Exception {
        String starName = "赵丽颖";
        List<Work> tv = starServiceI.getTV(starName);
        System.out.println(tv.size());
        System.out.println(tv.toString());
    }

    @Test
    public void testGetFilm() throws Exception {
        String starName = "赵丽颖";
        List<Work> film = starServiceI.getFilm(starName);
        System.out.println(film.size());
        System.out.println(film.toString());
    }

    @Test
    public void testGetFilmByPath() throws Exception {
        String path = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Data/赵丽颖/personal_tv.txt";
        List<Work> film = starServiceI.getFilmByPath(path);
        System.out.println(film.size());
        System.out.println(film.toString());
    }

    @Test
    public void testGetContentByPath() throws Exception {
        String filename = "personal_tv.txt";
        String path = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/恋爱行星.txt";
        Movie film = null;
        try {
            film = starServiceI.getContent(filename, path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(film.toString());
    }


}