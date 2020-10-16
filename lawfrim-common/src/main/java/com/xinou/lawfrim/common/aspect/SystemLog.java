package com.xinou.lawfrim.common.aspect;

import java.lang.annotation.*;

/**
 * All rights Reserved, Designed By 信鸥科技
 * Created by xiao_XX on 2019/10/22.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */

    @Target({ElementType.PARAMETER, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface SystemLog {
        String module()  default "";
        String methods()  default "";
        String name() default "";
        String type() default "";
        String loginType() default "";
    }
