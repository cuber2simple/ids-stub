package org.cuber.anno;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface TableSplitStrategy {

    SplitStrategy strategy() default SplitStrategy.current;

    /**
     * 应该返回LocalDataTime
     * @return
     */
    String expl() default "";

    /**
     * 应该返回LocalDataTime
     * @return
     */
    String bottomExpl() default "";

    /**
     * 应该返回LocalDataTime
     * @return
     */
    String topExpl() default "";

    /**
     * 应该返回LocalDataTime
     * @return
     */
    String[] splitTables();
}
