package com.intland.codebeamer.integration.sitemap.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Component {

	public String value();
	
	public String details() default "";

	public boolean includeInSitemap() default true;
	
}
