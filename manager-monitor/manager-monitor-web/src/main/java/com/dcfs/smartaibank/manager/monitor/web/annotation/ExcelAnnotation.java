package com.dcfs.smartaibank.manager.monitor.web.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * excel导出表头定义
 *
 * @author liangchenglong
 * @date 2019/7/19 10:58
 * @since 1.0.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ExcelAnnotation {
    String header();
}
