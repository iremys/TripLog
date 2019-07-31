package com.iremys.triplog.core.vo;

public class FileVo {

    private int fileNo;
    private int inoutNo;
    private String saveFileName;
    private String filePath;

    public FileVo() {
    }

    public int getFileNo() {
        return fileNo;
    }

    public void setFileNo(int fileNo) {
        this.fileNo = fileNo;
    }

    public int getInoutNo() {
        return inoutNo;
    }

    public void setInoutNo(int inoutNo) {
        this.inoutNo = inoutNo;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "FileVo{" +
                "fileNo=" + fileNo +
                ", inoutNo=" + inoutNo +
                ", saveFileName='" + saveFileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
