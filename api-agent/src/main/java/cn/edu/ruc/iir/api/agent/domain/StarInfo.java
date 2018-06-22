package cn.edu.ruc.iir.api.agent.domain;

import java.util.List;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.api.agent.domain
 * @ClassName: StarInfo
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-18 20:28
 **/
public class StarInfo {

    private BasicInfo basicInfo;
    private Intro intro;
    private List<Experience> experience;


    public StarInfo() {
    }

    public StarInfo(BasicInfo basicInfo, Intro intro, List<Experience> experience) {
        this.basicInfo = basicInfo;
        this.intro = intro;
        this.experience = experience;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public Intro getIntro() {
        return intro;
    }

    public void setIntro(Intro intro) {
        this.intro = intro;
    }

    public List<Experience> getExperience() {
        return experience;
    }

    public void setExperience(List<Experience> experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "StarInfo{" +
                "basicInfo=" + basicInfo +
                ", intro=" + intro +
                ", experience=" + experience +
                '}';
    }
}
