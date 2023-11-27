package me.jaewonna.Aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LogAspect {
    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //모든 패키지 내의 aop package에 존재하는 클래스
    @Around("execution(**..aop.*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        //해당 클래스 처리 전의 시간
        StopWatch sw = new StopWatch();
        sw.start();

        // 해당하는 클래스의 메소드 핵심기능을 실행
        Object result = pjp.proceed();

        //해당 클래스 처리 후의 시간
        sw.stop();
        long executionTime = sw.getTotalTimeMillis();

        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        String task = className + ". " + methodName;

        logger.debug("[ExecutionTime] " + task + "-->" + executionTime + "(ms)");

        return result;
    }

}
