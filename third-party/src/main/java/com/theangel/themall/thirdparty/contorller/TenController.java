package com.theangel.themall.thirdparty.contorller;

import com.theangel.common.utils.R;
import com.theangel.common.utils.fileutils.FileUtils;
import com.theangel.themall.thirdparty.service.uploadFileService;
import com.theangel.themall.thirdparty.utils.TencentUploadUtil;
import com.theangel.common.utils.fileutils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("third-party/ten")
@Slf4j
public class TenController {

    @Resource
    private TencentUploadUtil tencentUploadUtil;

    @Resource
    private uploadFileService uploadFileService;

    @GetMapping("/getTempKey")
    public R getTempKey() {
        JSONObject tempKey = tencentUploadUtil.getTempKey();
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("data", tempKey.toString());
        return R.ok(objectObjectHashMap);
    }

    @PostMapping("/cosGoodsLogo")
    public R cosTenGoodsLogo(MultipartFile file) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            File file1 = FileUtils.multipartFileToFile(file);
            if (null != file1) {
                String fileName = file1.getName();
                String suffix = UUIDUtils.getUUID() + fileName.substring(fileName.lastIndexOf("."));
                String path = "/goodsLogo/" + suffix;
                String s = uploadFileService.uploadFile(path, file1);
                if (StringUtils.isNotEmpty(s)) {
                    map.put(file.getName(), s);
                }
                FileUtils.delteTempFile(file1);
            }
        } catch (IOException e) {
            log.error(e.getClass().getSimpleName() + "类错误--------->" + e.toString());
            return R.ok(map);
        }
        return R.ok(map);
    }


    @GetMapping("/getkey")
    public R t() {
        String s = tencentUploadUtil.uploadFile("/t/t.jpg", new File("C:\\Users\\一个小小卒\\Pictures\\Saved Pictures\\theangel.top.png"));
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("data", s);
        return R.ok(objectObjectHashMap);
    }

}
