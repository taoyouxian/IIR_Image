package cn.edu.ruc.iir.api.agent.domain;

import java.util.List;

public class Movie {
    private String name;
    private List<Content> content;

    public Movie() {
    }

    public Movie(String name, List<Content> content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", content=" + content +
                '}';
    }
}
