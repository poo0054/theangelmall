package com.themall.thirdparty.contorller;

import com.themall.common.utils.R;
import com.themall.common.utils.fileutils.FileUtils;
import com.themall.common.utils.fileutils.UUIDUtils;
import com.themall.thirdparty.utils.TencentUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ten")
@Slf4j
public class TenController {

    @Resource
    private TencentUploadUtil tencentUploadUtil;

    @Resource
    private com.themall.thirdparty.service.uploadFileService uploadFileService;

    @GetMapping("/getTempKey")
    public R getTempKey() {
        JSONObject tempKey = tencentUploadUtil.getTempKey();
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("data", tempKey.toString());
        return R.ok(objectObjectHashMap);
    }

//    @PostMapping("/cosGoodsLogo")
    public Map<String, String> cosTenGoodsLogo(MultipartFile file) {
        HashMap<String, String> map = new HashMap<>();
        try {
            File file1 = FileUtils.multipartFileToFile(file);
            if (null != file1) {
                String fileName = file1.getName();
                String suffix = UUIDUtils.getUUID() + fileName.substring(fileName.lastIndexOf("."));
                String path = "/goodsLogo/" + suffix;
                String s = uploadFileService.uploadFile(path, file1);
                if (StringUtils.isNotEmpty(s)) {
                    map.put("data", s);
                }
                FileUtils.delteTempFile(file1);
            }
        } catch (IOException e) {
            log.error(e.getClass().getSimpleName() + "类错误--------->" + e.toString());
            return map;
        }
        return map;
    }


//    @GetMapping("/getkey")
    public R t() {
        String s = tencentUploadUtil.uploadFile("/t/t.jpg", new File("C:\\Users\\一个小小卒\\Pictures\\Saved Pictures\\theangel.top.png"));
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("data", s);
        return R.ok(objectObjectHashMap);
    }

}
