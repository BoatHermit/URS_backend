package com.nju.urs.common.aspect;

import com.github.benmanes.caffeine.cache.Cache;
import com.nju.urs.common.annotation.DoubleCache;
import com.nju.urs.common.constant.CacheConstant;
import com.nju.urs.common.enums.CacheType;
import com.nju.urs.common.utils.ElParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Aspect
@AllArgsConstructor
public class CacheAspect {
    private final Cache<String, Object> cache;
    private final RedisTemplate<String, Object> redisTemplate;
    @Pointcut("@annotation(com.nju.urs.common.annotation.DoubleCache)")
    public void cacheAspect() {
    }

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //拼接解析springEl表达式的map
        String[] paramNames = signature.getParameterNames();
        Object[] args = point.getArgs();
        TreeMap<String, Object> treeMap = new TreeMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            treeMap.put(paramNames[i],args[i]);
        }

        DoubleCache annotation = method.getAnnotation(DoubleCache.class);
        String[] keys = annotation.key().split(",");
        keys = Arrays.stream(keys).map(String::trim).toArray(String[]::new);
        StringBuilder elResult = new StringBuilder();
        for (String key : keys) {
            elResult.append(ElParser.parse(key, treeMap));
        }

        String realKey = annotation.cacheName() + CacheConstant.COLON + elResult;

        if (annotation.type() == CacheType.PUT){
            Object object = point.proceed();
            redisTemplate.opsForValue().set(realKey, object, annotation.l2TimeOut(), TimeUnit.SECONDS);
            cache.put(realKey, object);
            return object;
        } else if (annotation.type() == CacheType.DELETE){
            redisTemplate.delete(realKey);
            cache.invalidate(realKey);
            return point.proceed();
        } else if (annotation.type() == CacheType.FULL){
            //读写，查询Caffeine
            Object caffeineCache = cache.getIfPresent(realKey);
            if (Objects.nonNull(caffeineCache)) {
                log.info("Get data from caffeine");
                return caffeineCache;
            }

            //查询Redis
            Object redisCache = redisTemplate.opsForValue().get(realKey);
            if (Objects.nonNull(redisCache)) {
                log.info("Get data from redis");
                cache.put(realKey, redisCache);
                return redisCache;
            }

            log.info("Get data from database");
            Object object = point.proceed();
            if (Objects.nonNull(object)){
                redisTemplate.opsForValue().set(realKey, object, annotation.l2TimeOut(), TimeUnit.SECONDS);
                cache.put(realKey, object);
            }
            return object;
        } else {
            log.info("Undefined cache type");
            return point.proceed();
        }
    }
}