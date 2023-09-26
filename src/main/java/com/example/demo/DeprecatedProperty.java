package com.example.demo;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeprecatedProperty {
    String message() default "This property is deprecated.";
}

