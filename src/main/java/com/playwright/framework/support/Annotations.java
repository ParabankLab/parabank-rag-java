package com.playwright.framework.support;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public class Annotations {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface Quarantine {
        String reason() default "Flaky behavior under investigation";
        String ticket() default "";
        String owner() default "";
    }
}
