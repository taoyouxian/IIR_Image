package cn.edu.ruc.iir.api.agent.domain;

public class Relation {
    private String relation;
    private String star_name;
    private String career;

    public Relation() {
        this.relation = "";
        this.star_name = "";
        this.career = "";
    }

    public Relation(String relation, String star_name, String career) {
        this.relation = relation;
        this.star_name = star_name;
        this.career = career;
    }

    public String getStar_name() {
        return star_name;
    }

    public void setStar_name(String star_name) {
        this.star_name = star_name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "star_name='" + star_name + '\'' +
                ", relation='" + relation + '\'' +
                ", career='" + career + '\'' +
                '}';
    }
}
