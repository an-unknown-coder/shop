//package com.qf.interceptor;
//
//import com.qf.constant.RedisConstant;
//import com.qf.entity.TUser;
//import com.qf.util.RedisUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class AuthInterceptor implements HandlerInterceptor {
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    public boolean loginHandle(HttpServletRequest request, HttpServletResponse response, Object handle) {
//        // 获取cookie中的uuid
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (RedisConstant.USER_LOGIN_UUID.equals(cookie.getName())) {
//                    String uuid = cookie.getValue();
//                    String redisKey = RedisUtil.getRedisKey(RedisConstant.USER_LOGIN_PRE, uuid);
//                    // 拿到cookie中的值
//                    Object o = redisTemplate.opsForValue().get(redisKey);
//                    if (o != null) {
//                        // 用户已登录
//                        TUser user = (TUser) o;
//                        // 存到request中
//                        request.setAttribute("user", user);
//                        return true;
//                    }
//                }
//            }
//        }
//        return true;
//    }
//}
//
