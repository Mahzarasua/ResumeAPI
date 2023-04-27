package com.izars.resumeapi.auth.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import static com.izars.resumeapi.auth.advice.LoggingConstants.EXCLUDED_PKGS;
import static com.izars.resumeapi.auth.advice.LoggingConstants.OBSERVED_PKGS;
import static com.izars.resumeapi.auth.utils.SpringUtils.OBJECT_MAPPER;

@Aspect
@Component
@Primary
@Slf4j
public class LoggingAdvice {

    private static final String POINTCUT = OBSERVED_PKGS + " " + EXCLUDED_PKGS;

    @Pointcut(value = POINTCUT)
    public void myPointcut() {
    }

    @Around("myPointcut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString().replace("class ", "");
        Object[] array = pjp.getArgs();

        log.info("Entering " + className + ":" + methodName + "()" + " arguments: " + OBJECT_MAPPER.writeValueAsString(array));

        Object obj = pjp.proceed();

        log.info("Output " + className + ":" + methodName + "()" + " Response: " + OBJECT_MAPPER.writeValueAsString(obj));

        return obj;
    }
}
