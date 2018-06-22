package cn.edu.ruc.iir.api.agent.domain;

public class Prize {
    private String time;
    private String content;

    public Prize() {
        this.time = "";
        this.content = "";
    }

    public Prize(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Prize{" +
                "time='" + time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
