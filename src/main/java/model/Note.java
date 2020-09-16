package model;

public class Note {
    private Integer noteid;
    private String notetittle;
    private String notedescription;
    private Integer userid;

    public Note(Integer noteid, String notetittle, String notedescription, Integer userid) {
        this.noteid = noteid;
        this.notetittle = notetittle;
        this.notedescription = notedescription;
        this.userid = userid;
    }

    public Note(Integer noteid, String notetittle, String notedescription) {
        this.noteid = noteid;
        this.notetittle = notetittle;
        this.notedescription = notedescription;
    }

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public String getNotetittle() {
        return notetittle;
    }

    public void setNotetittle(String notetittle) {
        this.notetittle = notetittle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
