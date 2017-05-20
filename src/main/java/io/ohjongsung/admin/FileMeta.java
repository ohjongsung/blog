package io.ohjongsung.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ohjongsung on 2017-05-20.
 */
@JsonIgnoreProperties({"bytes"})
public class FileMeta {

    private String fileName;
    private String fileSize;
    private String fileType;
    private String fileUrl;

    private byte[] bytes;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

}
