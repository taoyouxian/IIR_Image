package cn.edu.ruc.iir.api.agent.domain;

public class Content {
    private String content;

    public Content() {
        this.content = "";
    }

    public Content(String content) {
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
