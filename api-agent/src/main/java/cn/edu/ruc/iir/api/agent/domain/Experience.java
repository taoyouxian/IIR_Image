package cn.edu.ruc.iir.api.agent.domain;

public class Experience extends Content {
    private String content;

    public Experience() {
        this.content = "";
    }

    public Experience(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Intro{" +
                "content='" + content + '\'' +
                '}';
    }
}
