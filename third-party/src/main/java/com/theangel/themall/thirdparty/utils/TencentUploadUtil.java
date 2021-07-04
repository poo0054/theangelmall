package com.theangel.themall.thirdparty.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import com.theangel.themall.thirdparty.pojo.TencentUpload;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.TreeMap;


@Configuration
@AutoConfigureBefore(TencentUpload.class)
@Log4j
public class TencentUploadUtil {

    private final TencentUpload tencentUpload;

    public TencentUploadUtil(TencentUpload tencentUpload) {
        this.tencentUpload = tencentUpload;
    }

    /**
     * 上传文件
     *
     * @param path 文件服务器下的根路径,即key,如: test/test.jpg
     * @param file
     * @return 成功返回文件路径, 失败返回null
     */
    public String uploadFile(String path, File file) throws JSONException {
        //获取临时密钥
        JSONObject temp = getTempKey();
        // 用户基本信息:解析临时密钥中的相关信息
        String tmpSecretId = temp.getJSONObject("credentials").getString("tmpSecretId");
        String tmpSecretKey = temp.getJSONObject("credentials").getString("tmpSecretKey");
        String sessionToken = temp.getJSONObject("credentials").getString("sessionToken");

        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(tmpSecretId, tmpSecretKey);
        // 2 设置 bucket 区域
        ClientConfig clientConfig = new ClientConfig(new Region(tencentUpload.getRegion()));
        // 3 生成 cos 客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = tencentUpload.getBucket();
        // 上传 object, 建议 20M 以下的文件使用该接口
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, file);
        // 设置 x-cos-security-token header 字段
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setSecurityToken(sessionToken);
        putObjectRequest.setMetadata(objectMetadata);
        String rtValue = null;
        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            // 成功：putobjectResult 会返回文件的 etag
            String etag = putObjectResult.getETag();
            rtValue = tencentUpload.getBaseUrl() + path;
            log.info("图片创建地址为==================>" + rtValue);
        } catch (CosServiceException e) {
            //失败，抛出 CosServiceException
            e.printStackTrace();
            throw new CosServiceException(e.getMessage());
        } catch (CosClientException e) {
            //失败，抛出 CosClientException
            e.printStackTrace();
            throw new CosClientException(e.getMessage());
        } finally {
            // 关闭客户端
            if (cosclient != null) {
                cosclient.shutdown();
            }
        }
        return rtValue;
    }

    /**
     * 生成临时密钥
     *
     * @return
     */
    public JSONObject getTempKey() {
        TreeMap<String, Object> config = new TreeMap<>();
        try {//使用永久密钥生成临时密钥
            config.put("SecretId", tencentUpload.getSecretId());
            config.put("SecretKey", tencentUpload.getSecretKey());
            config.put("durationSeconds", tencentUpload.getDurationSeconds());
            config.put("bucket", tencentUpload.getBucket());
            config.put("region", tencentUpload.getRegion());
            config.put("allowPrefix", tencentUpload.getAllowPrefix());
            //密钥的权限列表，其他权限列表请看
            //https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[]{
                    // 简单上传
                    "name/cos:PutObject",
                    "name/cos:PostObject",
                    // 分片上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload"
            };
            config.put("allowActions", allowActions);
            JSONObject credential = CosStsClient.getCredential(config);
            System.out.println(credential.toString(4));
            System.out.println(credential);
            return credential;
        } catch (Exception e) {
            //失败抛出异常
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
