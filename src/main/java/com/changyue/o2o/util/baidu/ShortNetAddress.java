package com.changyue.o2o.util.baidu;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 袁阊越
 * @Title: ShortNetAddress
 * @Package com.changyue.o2o.util
 * @Description: 将长连接处理为短链接 文档 https://dwz.cn/console/apidoc
 * @date 2019/10/19/019
 */
public class ShortNetAddress {
    private static Logger log = LoggerFactory.getLogger(ShortNetAddress.class);
    final static String CREATE_API = "https://dwz.cn/admin/v2/create";
    final static String TOKEN = "0fe40735106033adb0370299fc9ecbb7";

    class UrlResponse {
        @SerializedName("Code")
        private int code;

        @SerializedName("ErrMsg")
        private String errMsg;

        @SerializedName("LongUrl")
        private String longUrl;

        @SerializedName("ShortUrl")
        private String shortUrl;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public String getLongUrl() {
            return longUrl;
        }

        public void setLongUrl(String longUrl) {
            this.longUrl = longUrl;
        }

        public String getShortUrl() {
            return shortUrl;
        }

        public void setShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
        }
    }

    /**
     * 创建短网址
     *
     * @param longUrl 长网址：即原网址
     *                termOfValidity
     *                有效期：默认值为long-term
     * @return 成功：短网址
     * 失败：返回空字符串
     */
    public static String createShortUrl(String longUrl, String termOfValidity) {
        String params = "{\"Url\":\"" + longUrl + "\",\"TermOfValidity\":\"" + termOfValidity + "\"}";

        BufferedReader reader = null;
        try {
            // 创建连接
            URL url = new URL(CREATE_API);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST");
            // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/json");
            // 设置发送数据的格式
            connection.setRequestProperty("Token", TOKEN);
            // 设置发送数据的格式");

            // 发起请求
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            // utf-8编码
            out.append(params);
            out.flush();
            out.close();

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();

            // 抽取生成短网址
            UrlResponse urlResponse = new Gson().fromJson(res, UrlResponse.class);
            if (urlResponse.getCode() == 0) {
                return urlResponse.getShortUrl();
            } else {
                System.out.println(urlResponse.getErrMsg());
            }
            return "";
        } catch (IOException e) {
            log.error("创建失败"+e.toString());
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String res = createShortUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx44de284a108c4d05&redirect_uri=http://changyue.club/o2o/shopadmin/addshopauthmap&role_type=1&response_type=code&scope=snsapi_userinfo&state=%7BaaashopIdaaa%3A19%2CaaacreateTimeaaa%3A1571491960359%7D#wechat_redirect", "1-year");
        System.out.println(res);
    }

}
