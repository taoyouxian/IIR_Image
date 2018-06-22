package cn.edu.ruc.iir.api.agent.domain;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.api.agent.domain
 * @ClassName: SimilarStar
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-17 21:15
 **/
public class SimilarStar {

    private String star_name;
    private String pic_path;

    public SimilarStar() {
        this.star_name = "";
        this.pic_path = "";
    }

    public SimilarStar(String star_name, String pic_path) {
        this.star_name = star_name;
        this.pic_path = pic_path;
    }

    public String getStar_name() {
        return star_name;
    }

    public void setStar_name(String star_name) {
        this.star_name = star_name;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }
}
