package com.intland.codebeamer.integration.test.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.intland.codebeamer.integration.util.HttpStatus;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ METHOD, TYPE })
public @interface TestCase {

	String link();

	String description() default "";

	HttpStatus[] expectedHttpErrors() default { HttpStatus.NONE };
}
