package com.loafer.filter;

import com.alibaba.fastjson.JSON;
import com.loafer.common.JSONResponse;
import com.loafer.common.TokenStatus;
import com.loafer.utils.JwtUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private JwtUtil jwtUtil = new JwtUtil();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("access_token");
        //token是否存在
        if (null != token) {
            //验证token是否正确
            String result = jwtUtil.verifyToken(token);
            if(result.equals(TokenStatus.TOKEN_INVALID)){//无效
                outputStream(servletResponse,"token令牌无效...");
            }else{//有效令牌，需要重新刷新token，再将token传回客户端，客户端会拿着新的token进行访问
                String refreshedToken = jwtUtil.refreshToken(token);
                System.out.println("refreshedToken:"+refreshedToken);
                // Access-Control-Allow-Origin就是我们需要设置的域名
                // Access-Control-Allow-Headers跨域允许包含的头。
                // Access-Control-Allow-Methods是允许的请求方式
//                response.setHeader("Access-Control-Allow-Origin", "*");// *,任何域名
//                response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
                // response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type, Accept");
                // 允许请求头Token
                // httpResponse.setHeader("Access-Control-Allow-Headers","Origin,X-Requested-With, Content-Type, Accept, Token");
                // 允许客户端，发一个新的请求头jwt
//                response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With, Content-Type, Accept, jwt");
                // 允许客户端，处理一个新的响应头jwt
//                response.setHeader("Access-Control-Expose-Headers", "jwt");
                response.setHeader("access_token", refreshedToken);
            }
            filterChain.doFilter(request, response);
            return;
        }
        outputStream(servletResponse,"无token令牌...");
    }

    /**
     * @description: 向客户端返回响应信息（json格式）
     * @author wangdong
     * @date 2019/10/8 16:46
     */
    private void outputStream(ServletResponse servletResponse,String message){
        try{
            String string = JSON.toJSONString(JSONResponse.info(message));
            servletResponse.setContentType("application/json;charset=UTF-8");
            servletResponse.getOutputStream().write(string.getBytes("UTF-8"));
            servletResponse.getOutputStream().close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
