package io.pivotal.springcache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingControllerRequestAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingControllerRequestAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
        // noop needed to define pointcut
    }

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
        // noop needed to define pointcut
    }

    @Around("controller() && allMethod()")
    @SuppressWarnings("squid:S00112")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("Beginning processing of {}", joinPoint.getSignature());

        Object results = joinPoint.proceed();

        log.info("Completed processing of {}", joinPoint.getSignature());

        return results;
    }

}
