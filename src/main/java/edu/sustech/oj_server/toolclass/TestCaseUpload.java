package edu.sustech.oj_server.toolclass;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class TestCaseUpload implements Serializable {
    private boolean spj;
    private MultipartFile file;

    public boolean isSpj() {
        return spj;
    }

    public void setSpj(boolean spj) {
        this.spj = spj;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
