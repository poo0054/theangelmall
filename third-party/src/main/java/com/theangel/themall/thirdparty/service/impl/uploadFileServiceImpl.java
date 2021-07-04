package com.theangel.themall.thirdparty.service.impl;

import com.theangel.themall.thirdparty.service.uploadFileService;
import com.theangel.themall.thirdparty.utils.TencentUploadUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

@Service
public class uploadFileServiceImpl implements uploadFileService {
    @Resource
    private TencentUploadUtil tencentUploadUtil;

    /**
     * 上传文件到腾讯云
     *
     * @return
     */
    @Override
    public String uploadFile(String pathName, File file) {
        return tencentUploadUtil.uploadFile(pathName, file);
    }
}
