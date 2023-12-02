package com.senlatest.weatheranalyzer.logging.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(@com.senlatest.weatheranalyzer.logging.annotation.Log * *(..))")
    private void annotatedMethods() {
    }

    @Pointcut("within(@com.senlatest.weatheranalyzer.logging.annotation.Log *)")
    private void annotatedClass() {
    }


    @Around("annotatedMethods() || annotatedClass()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Object methodResult = joinPoint.proceed(joinPoint.getArgs());

        String logMessage = "Class: : " + joinPoint.getTarget().getClass().getSimpleName() +
                "\nMethod: " + joinPoint.getSignature().getName() + "\n" +
                "request: " + Arrays.toString(joinPoint.getArgs()) + '\n' +
                "response: " + methodResult;
        log.info(logMessage);

        return methodResult;
    }
}
