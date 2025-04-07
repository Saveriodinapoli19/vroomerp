package com.vroomerp.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XLSMapping {
	
	public static interface Converter {
		
		public static int LONG_TO_DATE = 1;
		public static int LONG_TO_DATETIME = 2;
		public static int CENT_TO_EURO = 3;
		public static int REMOVE_CENT = 4;
		
	}
	
    public String explicitName() default "";
    public boolean exclude() default false;
    public int converter() default 0;
    
}