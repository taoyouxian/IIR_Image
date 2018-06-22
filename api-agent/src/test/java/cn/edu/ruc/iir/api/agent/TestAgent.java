package cn.edu.ruc.iir.api.agent;

import cn.edu.ruc.iir.api.agent.domain.*;
import cn.edu.ruc.iir.api.agent.service.StarServiceI;
import cn.edu.ruc.iir.api.agent.service.StarServiceImpl;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest(classes = StarServiceImpl.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAgent {
    @Autowired
    protected StarServiceI starServiceI;

    @Test
    public void getScore() {
        System.out.println(12);
    }

    @Test
    public void getStarInfo() throws Exception {
        String starName = "赵丽颖";
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
        System.out.println("getStarInfo res: " + res);
    }


}
