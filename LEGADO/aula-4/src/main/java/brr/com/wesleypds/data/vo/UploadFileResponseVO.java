package brr.com.wesleypds.data.vo;

import java.io.Serializable;

public class UploadFileResponseVO implements Serializable {

    private String fileName;

    private String downloadUri;

    private String fileType;

    private long fileSize;

    public UploadFileResponseVO() {}

    public UploadFileResponseVO(String fileName, String downloadUri, String fileType, long fileSize) {
        this.fileName = fileName;
        this.downloadUri = downloadUri;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

}
