package com.loafer.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loafer.common.RSAUtil;
import com.loafer.utils.BodyReaderHttpServletRequestWrapper;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.Cipher;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @ClassName RequestFilter
 * @Description [拦截器，验证参数签名是否通过]
 * @Author wangdong
 * @Date 2019/10/6 18:42
 * @Version V1.0
 **/

@WebFilter(filterName = "request", urlPatterns = "/user/*")
public class RequestFilter implements Filter {
    //服务端私钥，用来解密客户端加密的请求报文
    String private_key="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";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        String bodyString = getBodyString(requestWrapper.getReader());
        System.out.println("未解密的报文参数:" + bodyString);
        //将请求的参数转换为json对象
        JSONObject jsonObject = JSON.parseObject(bodyString);
        //后去参数的base里的json对象
        String param = jsonObject.getString("param");
        //私钥解密
        String param1 = RSAUtil.encryptByprivateKey(param,private_key, Cipher.DECRYPT_MODE);
        System.out.println("已解密的报文参数:"+param1);
        ((BodyReaderHttpServletRequestWrapper) requestWrapper).setInputStream(param1.getBytes());
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

    @Override
    public void destroy() {

    }
}
