package com.mirror.travel.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;

public class SendSmsUtil {
    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    /**
     * 使用STS鉴权方式初始化账号Client，推荐此方式。
     * @param accessKeyId
     * @param accessKeySecret
     * @param securityToken
     * @return Client
     * @throws Exception
     */
    public static Client createClientWithSTS(String accessKeyId, String accessKeySecret, String securityToken) throws Exception {
        Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret)
                // 必填，您的 Security Token
                .setSecurityToken(securityToken)
                // 必填，表明使用 STS 方式
                .setType("sts");
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    public static SendSmsResponse sendNoToken(String phone,String code) throws Exception {
        // 工程代码泄露可能会导致AccessKey泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        Client client = SendSmsUtil.createClient("LTAI5tJk79U36NHwVKwfM69v", "sMkZzklD8XBaT5GJ3F5cv5CxXlYre3");
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("普通项目验证码")
                .setTemplateCode("SMS_460870268")
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":\""+code+"\"}");
        RuntimeOptions runtime = new RuntimeOptions();
        return client.sendSmsWithOptions(sendSmsRequest, runtime);
//        com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(resp));
    }

    public static void main(String[] args) {
        try {
//            SendSmsResponse resp = SendSmsUtil.sendNoToken("13781877026", "696966");
            SendSmsResponse resp = SendSmsUtil.sendNoToken("15886792979", "778966");
            System.out.println(resp);
            System.out.println("------------------------------------");
            com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(resp));
            System.out.println("------------------------------------");
            System.out.println(com.aliyun.teautil.Common.toJSONString(resp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}