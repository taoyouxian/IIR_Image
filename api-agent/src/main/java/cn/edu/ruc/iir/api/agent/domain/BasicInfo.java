package cn.edu.ruc.iir.api.agent.domain;

public class BasicInfo {
    String first_letter;
    String star_name;
    String birthdate;
    String nationality;
    String blood_type;
    String nick_name;
    String weight;
    String height;
    String constellation;

    public BasicInfo() {
        this.first_letter = "";
        this.star_name = "";
        this.birthdate = "";
        this.nationality = "";
        this.blood_type = "";
        this.constellation = "";
        this.nick_name = "";
        this.weight = "";
        this.height = "";
        this.constellation = "";
    }

    public void setInfo(String first_letter, String star_name, String birthdate, String nationality, String blood_type, String nick_name, String weight, String height, String constellation) {
        this.first_letter = "";
        this.star_name = "";
        this.birthdate = "";
        this.nationality = "";
        this.blood_type = "";
        this.constellation = "";
        this.nick_name = "";
        this.weight = "";
        this.height = "";
        this.constellation = "";
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public String getStar_name() {
        return star_name;
    }

    public void setStar_name(String star_name) {
        this.star_name = star_name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    @Override
    public String toString() {
        return "BasicInfo [first_letter=" + first_letter + ", star_name=" + star_name + ", birthdate=" + birthdate
                + ", nationality=" + nationality + ", blood_type=" + blood_type + ", nick_name=" + nick_name
                + ", weight=" + weight + ", height=" + height + ", constellation=" + constellation + "]";
    }

}
