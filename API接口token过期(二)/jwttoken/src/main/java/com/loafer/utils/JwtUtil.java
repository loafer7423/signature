package com.loafer.utils;
import com.loafer.common.TokenStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JwtUtil
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/10 11:05
 * @Version V1.0
 **/
public class JwtUtil {
    /**加密秘钥*/
    private String secret = "a1g2y47dg3dj59fjhhsd7cnewy73j";
    /**设置有效时间，为30秒*/
    private long expiration = (long)30 * 1000;

    /**
     * 初始化生成token的参数
     * @param userId
     * @return String
     */
    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>(1);
        claims.put("sub", userId);
        return generateToken(claims);
    }

    /**
     * 生成token
     * @param claims
     * @return String
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .setIssuedAt(this.generateCurrentDate())
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }


    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration);
    }

    /**
     * 判断token是否可以刷新
     * @param token
     * @param lastPasswordReset
     */
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
            final Date iat = claims.getIssuedAt();
            final Date exp = claims.getExpiration();
            if (iat.before(lastPasswordReset) || exp.before(generateCurrentDate())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新token
     * @param token
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
            refreshedToken = this.generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 校验token
     * @param token
     */
    public String verifyToken(String token) {
        String result = "";
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
            result = TokenStatus.TOKEN_VALID;
        } catch (Exception e) {
            result = TokenStatus.TOKEN_INVALID;
        }
        return result;
    }

    /**
     * 获取用户编号
     * @param token
     */
    public String getUserNameFromToken(String token) {
        String userName;
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
        }
        return userName;
    }

}
