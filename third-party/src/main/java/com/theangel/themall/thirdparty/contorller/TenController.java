package com.theangel.themall.thirdparty.contorller;

import com.theangel.common.utils.R;
import com.theangel.themall.thirdparty.pojo.TencentUpload;
import com.theangel.themall.thirdparty.utils.TencentUploadUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("third-party/ten")
public class TenController {

    @Resource
    private TencentUpload tencentUpload;
    @Resource
    private TencentUploadUtil tencentUploadUtil;

    @GetMapping("/getTempKey")
    public R test() {
        JSONObject tempKey = tencentUploadUtil.getTempKey();
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("data", tempKey.toString());
        return R.ok(objectObjectHashMap);
    }

    @GetMapping("/getkey")
    public R t() {
        String s = tencentUploadUtil.uploadFile("/t/t.jpg", new File("C:\\Users\\一个小小卒\\Pictures\\Saved Pictures\\theangel.top.png"));
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("data", s);
        return R.ok(objectObjectHashMap);
    }

}
