package com.mirror.travel.utils.baidu;

/**
 * 选择了ak或使用IP白名单校验：
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchHttpAK {

    public static String URL = "https://api.map.baidu.com/weather/v1/?";

    public static String AK = "自己的AK";


    public static void main(String[] args) throws Exception {

        SearchHttpAK snCal = new SearchHttpAK();

        Map params = new LinkedHashMap<String, String>();
        params.put("district_id", "430800");
        params.put("data_type", "all");
        params.put("ak", AK);

        snCal.requestGetAK(URL, params);
    }

    /**
     * 默认ak
     * 选择了ak，使用IP白名单校验：
     * 根据您选择的AK以为您生成调用代码
     * 检测到您当前的ak设置了IP白名单校验
     * 您的IP白名单中的IP非公网IP，请设置为公网IP，否则将请求失败
     * 请在IP地址为0.0.0.0/0 外网IP的计算发起请求，否则将请求失败
     */
    public static JSONArray requestGetAK(String strUrl, Map<String, String> param) throws Exception {
        if (strUrl == null || strUrl.length() <= 0 || param == null || param.size() <= 0) {
            return null;
        }

        StringBuffer queryString = new StringBuffer();
        queryString.append(strUrl);
        for (Map.Entry<?, ?> pair : param.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    "UTF-8") + "&");
        }

        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }

        java.net.URL url = new URL(queryString.toString());
        System.out.println(queryString.toString());
        URLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();

        InputStreamReader isr = new InputStreamReader(httpConnection.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        isr.close();
        JSONObject ak = JSONObject.parseObject(buffer.toString());
        JSONObject result = (JSONObject) ak.get("result");
        JSONArray forecasts = JSONArray.parseArray(result.get("forecasts").toString());
        return forecasts;
//        System.out.println(forecasts.toJSONString());
//        System.out.println(forecasts.get(0));
    }
}
