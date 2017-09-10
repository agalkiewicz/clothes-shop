package com.example.zzjp.clothesShop.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Slf4j
@Component
public class TracingAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before("com.example.zzjp.clothesShop.aop.ClothesShopPointcuts.asyncControllerMethod()")
    public void tracingController(final JoinPoint joinPoint) {
        logger.info("Method invoked: {}", joinPoint.getSignature());
    }

    @Around("com.example.zzjp.clothesShop.aop.ClothesShopPointcuts.monitored()")
    public Object monitor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("Measuring time for method {}", proceedingJoinPoint.getSignature());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        logger.info("Took {} ms", stopWatch.getTotalTimeMillis());

        return result;
    }
}
