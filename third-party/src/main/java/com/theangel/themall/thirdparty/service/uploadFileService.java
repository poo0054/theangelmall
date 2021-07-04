package com.theangel.themall.thirdparty.service;


import java.io.File;

public interface uploadFileService {
    /**
     * 文件上传到服务器
     *
     * @return
     */
    public String uploadFile(String pathName, File file);
}
