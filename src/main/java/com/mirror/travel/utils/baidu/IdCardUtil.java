package com.mirror.travel.utils.baidu;

import com.alibaba.fastjson.JSONObject;
import com.mirror.travel.utils.JedisUtil;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.net.URLEncoder;
import java.util.Map;

public class IdCardUtil {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String idCard(String filePath, String idCardSide) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            // 本地文件路径
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "id_card_side=" + idCardSide + "&image=" + imgParam + "&detect_risk=true";

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAccessToken();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //客户端自行缓存Token
    private static String getAccessToken() {
        Jedis jedis = JedisUtil.getJedis();
        String access_token = jedis.get("ACCESS_TOKEN");

        if (StringUtils.isEmpty(access_token)) {
            //如果redis中token不存在, 请求百度云的接口获取新的token 并存储到redis中
            access_token = AuthServiceUtil.getAuth();
            jedis.set("ACCESS_TOKEN", access_token);
            jedis.expire("ACCESS_TOKEN", 2562000);
        }

        //关闭redis里连接
        JedisUtil.close(jedis);
        return access_token;
    }


    public static void main(String[] args) {
        String front = IdCardUtil.idCard("D:\\img\\Snipaste20456.png", "front");
        Map map = JSONObject.parseObject(front, Map.class);

//        JSONObject words_result = (JSONObject) map.get("words_result");
//        String fullName = words_result.getJSONObject("姓名").getString("words");
//        String ethnic = words_result.getJSONObject("民族").getString("words");
//        String address = words_result.getJSONObject("住址").getString("words");
//        String idCardNum = words_result.getJSONObject("公民身份号码").getString("words");
//        String birthday = words_result.getJSONObject("出生").getString("words");
//        String sex = words_result.getJSONObject("性别").getString("words");
//
//        String frontImageStatus = (String) map.get("image_status");
//        String frontRiskType = (String) map.get("risk_type");
//        Integer idCardNumberType = (Integer) map.get("idcard_number_type");
        System.out.println(map);
    }

}
