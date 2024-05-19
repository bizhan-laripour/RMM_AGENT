package com.submodule.annotation;



import com.submodule.enums.SearchType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author a.mehdizadeh on 5/5/2024
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchField {

    SearchType type() default SearchType.EQUAL_TO;

    String target_field() default "";
}
