package cn.edu.ruc.iir.api.agent.domain;

public class Work {
    private String name;
    private String charater;
    private String director;
    private String performer;

    public Work() {
        this.name = "";
        this.charater = "";
        this.director = "";
        this.performer = "";
    }

    public Work(String name, String charater, String director, String performer) {
        this.name = name;
        this.charater = charater;
        this.director = director;
        this.performer = performer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharater() {
        return charater;
    }

    public void setCharater(String charater) {
        this.charater = charater;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    @Override
    public String toString() {
        return "Work{" +
                "name='" + name + '\'' +
                ", charater='" + charater + '\'' +
                ", director='" + director + '\'' +
                ", performer='" + performer + '\'' +
                '}';
    }
}