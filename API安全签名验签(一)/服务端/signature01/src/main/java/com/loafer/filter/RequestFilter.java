package com.loafer.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loafer.common.JSONResponse;
import com.loafer.util.BodyReaderHttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

/**
 * @ClassName RequestFilter
 * @Description [拦截器，验证参数签名是否通过]
 * @Author wangdong
 * @Date 2019/10/6 18:42
 * @Version V1.0
 **/

@WebFilter(filterName = "request", urlPatterns = "/test/*")
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    /**
     * @description: 拦截方法，处理业务逻辑
     * @author wangdong
     * @date 2019/10/8 16:39
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //获取请求地址，如：/test/list
        String requestURI = request.getRequestURI();
        //过滤哪些请求直接放行
        if (requestURI.contains("/callBack")){
            filterChain.doFilter(request, servletResponse);
            return;
        }
        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        String bodyString = getBodyString(requestWrapper.getReader());
        //将请求的参数转换为json对象
        JSONObject jsonObject = JSON.parseObject(bodyString);
        //后去参数的base里的json对象
        JSONObject base = (JSONObject) jsonObject.get("base");
        String signature=base.getString("signature");
        //删除客户端穿过来的签名
        base.remove("signature");
        //将base参数转换为map对象
        Map map = generateSignStr(base);
        //拼装参数（按照字段名首字母排序）
        String param = formatUrlMap(map,true,true);
        if(!md5(param).equals(signature)){//验证参数签名是否正确（客户端的签名和服务端根据参数重新加密生成签名，再验签）
            outputStream(servletResponse,"参数被篡改...");
            return;
        }
        //比较请求的参数是否过期
        if(!validateTimeStamp(base.getLong("timestamp"))){
            outputStream(servletResponse,"请求参数已过期...");
            return;
        }
        //拦截器放行，继续执行业务方法
        filterChain.doFilter(requestWrapper, servletResponse);
        return;
    }


    /**
     * @description: 解析body数据的字符
     * @author wangdong
     * @date 2019/10/8 16:38
     */
    public static String getBodyString(BufferedReader br) {
        String inputLine;
        StringBuffer str = new StringBuffer();
        try {
            while ((inputLine = br.readLine()) != null) {
                str.append(inputLine);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    /**
     * @description: 将参数按照字段名排序
     * @author wangdong
     * @date 2019/10/8 16:39
     */
    public static String formatUrlMap(Map<String, Object> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff = "";
        Map<String, Object> tmpMap = paraMap;
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
                @Override
                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, Object> item : infoIds) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    Object val = item.getValue();
                    if (urlEncode) {
                        val = URLEncoder.encode(val.toString(), "utf-8");
                    }
                    if (keyToLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }
            }

            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }

    /**
     * @description: 向客户端返回响应信息（json格式）
     * @author wangdong
     * @date 2019/10/8 16:46
     */
    private void outputStream(ServletResponse servletResponse,String message){
        try{
            String string = JSON.toJSONString(JSONResponse.failure(5002, message));
            servletResponse.setContentType("application/json;charset=UTF-8");
            servletResponse.getOutputStream().write(string.getBytes("UTF-8"));
            servletResponse.getOutputStream().close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @description: 将json格式转换为map对象
     * @author wangdong
     * @date 2019/10/8 16:47
     */
    private Map generateSignStr(JSONObject base) {
        String timestamp = base.getString("timestamp");
        String nonce = base.getString("nonce");
        String userId = base.getString("userId");
        Map map = new HashMap();
        map.put("nonce", nonce);
        map.put("timestamp", timestamp);
        map.put("userId", userId);
        return map;
    }

    /**
     * @description: md5加密
     * @author wangdong
     * @date 2019/10/8 16:47
     */
    public static String md5(String content) {
        // 用于加密的字符
        char[] md5String = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            // 使用平台默认的字符集将md5String编码为byte序列,并将结果存储到一个新的byte数组中
            byte[] byteInput = content.getBytes();
            // 信息摘要是安全的单向哈希函数,它接收任意大小的数据,并输出固定长度的哈希值
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // MessageDigest对象通过使用update方法处理数据,使用指定的byte数组更新摘要
            mdInst.update(byteInput);
            //摘要更新后通过调用digest() 执行哈希计算,获得密文
            byte[] md = mdInst.digest();
            //把密文转换成16进制的字符串形式
            int j = md.length;
            char[] str = new char[j*2];
            int k = 0;
            for (int i=0;i<j;i++) {
                byte byte0 = md[i];
                str[k++] = md5String[byte0 >>> 4 & 0xf];
                str[k++] = md5String[byte0 & 0xf];
            }
            // 返回加密后的字符串
            return new String(str);

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @description: 判断客户端的请求是否超过30分钟
     * @author wangdong
     * @date 2019/10/8 16:48
     */
    public boolean validateTimeStamp(long timestamp) {
        Long tims = (System.currentTimeMillis()-timestamp) / (1000 * 60);
        //验证时间戳是否超过30分钟
        if (Math.abs(tims) >30) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void destroy() {

    }
}
