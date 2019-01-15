package com.lznbys.android.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.lznbys.android.base.BaseResponseEntity;
import com.lznbys.android.base.ResponseCode;
import com.lznbys.android.entity.OssResponseEntity;
import com.lznbys.android.oss.config.Config;
import com.lznbys.android.oss.config.ConfigJson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oss")

/**
 * 阿里云OSS存储STS服务动态口令接口 TODO 不符合MVC规范到时候改
 */
public class OssTokenController {

    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    public static final String STS_API_VERSION = "2015-04-01";

    @RequestMapping(method = RequestMethod.GET, value = "sts")
    public BaseResponseEntity<OssResponseEntity> ossSts() {

        // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
        String accessKeyId = Config.AccessKeyID;
        String accessKeySecret = Config.AccessKeySecret;

        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = Config.RoleArn;
        long durationSeconds = Long.valueOf(Config.TokenExpireTime);
        String policy = ConfigJson.AllPolicy.json;
        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = "alice-001";

        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;

        try {
            final AssumeRoleResponse stsResponse = assumeRole(accessKeyId, accessKeySecret, roleArn, roleSessionName,
                    policy, protocolType, durationSeconds);
            OssResponseEntity ossResponseEntity = new OssResponseEntity();
            ossResponseEntity.setStatusCode(200);
            ossResponseEntity.setAccessKeySecret(stsResponse.getCredentials().getAccessKeySecret());
            ossResponseEntity.setAccessKeyId(stsResponse.getCredentials().getAccessKeyId());
            ossResponseEntity.setExpiration(stsResponse.getCredentials().getExpiration());
            ossResponseEntity.setSecurityToken(stsResponse.getCredentials().getSecurityToken());
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS, ossResponseEntity);
        } catch (ClientException e) {
            return new BaseResponseEntity<>(ResponseCode.REQUEST_FAIL_MSG, ResponseCode.FAIL);
        }
    }

    private AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret, String roleArn,
                                          String roleSessionName, String policy, ProtocolType protocolType, long durationSeconds)
            throws ClientException {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);

            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            request.setDurationSeconds(durationSeconds);

            // 发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);

            return response;
        } catch (ClientException e) {
            throw e;
        }
    }
}
