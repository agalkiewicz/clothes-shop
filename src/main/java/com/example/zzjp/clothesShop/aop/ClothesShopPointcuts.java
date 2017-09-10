package com.example.zzjp.clothesShop.aop;

import org.aspectj.lang.annotation.Pointcut;

public class ClothesShopPointcuts {

    @Pointcut("execution(* *..controller..*(..))")
    public void asyncControllerMethod() {
    }

    @Pointcut("@annotation(Monitored)")
    public void monitored() {

    }
}
