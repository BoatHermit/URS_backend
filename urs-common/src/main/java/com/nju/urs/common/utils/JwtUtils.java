package com.nju.urs.common.utils;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import com.auth0.jwt.exceptions.JWTDecodeException;

import javax.servlet.http.HttpServletRequest;

public class JwtUtils {
    //设置token过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24;

    //密钥
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLqq";

    //生成token字符串
    public static String getJwtToken(String phone, String pwd){
        String JwtToken = Jwts.builder()
                //设置header(头部）：token类型、使用的算法
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                //设置payload(负荷)：主题、发行者、字符串过期时间
                .setSubject("cpirsystem-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                //设置主体部分，即sign(签名)：，存储用户信息
                .claim("phone", phone)
                .claim("pwd", pwd)

                //签名哈希，根据密钥和方式进行编码
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    //判断token是否存在与有效
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //判断token是否存在与有效，参数与上面不同，通过request在header里面将字符串得到，再做验证
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //根据token获取phone
    public static String getCheckPhoneByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("phone");
    }

}
