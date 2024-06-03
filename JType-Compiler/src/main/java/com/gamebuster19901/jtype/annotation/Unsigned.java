package com.gamebuster19901.jtype.annotation;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target({ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE_PARAMETER, ElementType.RECORD_COMPONENT, ElementType.TYPE})
public @interface Unsigned {}