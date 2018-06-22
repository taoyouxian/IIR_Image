package cn.edu.ruc.iir.api.agent.domain;

public class HotStar implements Comparable<HotStar> {
    private String star_name;
    private String pic_path;
    private String count;
    private String date;

    public HotStar() {
        this.star_name = "";
        this.pic_path = "";
        this.count = "";
        this.date = "";
    }

    public HotStar(String star_name, String pic_path, String count, String date) {
        this.star_name = star_name;
        this.pic_path = pic_path;
        this.count = count;
        this.date = date;
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

    public int getCount() {
        return Integer.valueOf(count);
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HotStar [star_name=" + star_name + ", pic_path=" + pic_path + ", count=" + count + ", date=" + date
                + "]";
    }


    @Override
    public int compareTo(HotStar o) {
        //首先比较count，如果count同，则比较date，逆序输出
        int count1 = Integer.valueOf(count);
        int count2 = o.getCount();
        if (count1 < count2) {
            return 1;
        }
        if (count1 > count2) {
            return -1;
        }
        if (count1 == count2) {
            return o.getDate().compareTo(date);
        }
        return 0;
    }
}
