package cn.edu.ruc.iir.api.agent.domain;

import java.util.List;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.api.agent.domain
 * @ClassName: Star
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-18 21:28
 **/
public class Star {

    private StarInfo starInfo;
    private List<Prize> prize;
    private List<Relation> relation;
    private List<Work> film;
    private List<Work> tv;


    public Star() {
    }

    public Star(StarInfo starInfo, List<Prize> prize, List<Relation> relation, List<Work> film, List<Work> tv) {
        this.starInfo = starInfo;
        this.prize = prize;
        this.relation = relation;
        this.film = film;
        this.tv = tv;
    }

    public List<Work> getFilm() {
        return film;
    }

    public void setFilm(List<Work> film) {
        this.film = film;
    }

    public List<Work> getTv() {
        return tv;
    }

    public void setTv(List<Work> tv) {
        this.tv = tv;
    }

    public StarInfo getStarInfo() {
        return starInfo;
    }

    public void setStarInfo(StarInfo starInfo) {
        this.starInfo = starInfo;
    }

    public List<Prize> getPrize() {
        return prize;
    }

    public void setPrize(List<Prize> prize) {
        this.prize = prize;
    }

    public List<Relation> getRelation() {
        return relation;
    }

    public void setRelation(List<Relation> relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "Star{" +
                "starInfo=" + starInfo +
                ", prize=" + prize +
                ", relation=" + relation +
                '}';
    }
}
