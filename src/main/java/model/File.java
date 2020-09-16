package model;

import java.sql.Blob;

public class File {
    private Integer fileId;
    private String filename;
    private String contenttype;
    private String filesize;
    private Integer userid;
    private byte[] filedate;

    public File(Integer fileId, String filename, String contenttype, String filesize, Integer userid, byte[] filedate) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedate = filedate;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public byte[] getFiledate() {
        return filedate;
    }

    public void setFiledate(byte[] filedate) {
        this.filedate = filedate;
    }
}
