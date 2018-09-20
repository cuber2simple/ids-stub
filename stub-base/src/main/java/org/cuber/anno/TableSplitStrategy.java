package org.cuber.anno;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface TableSplitStrategy {

    SplitStrategy strategy() default SplitStrategy.current;

    String expl() default "";

    String bottomExpl() default "";

    String topExpl() default "";

    String[] splitTables();
}
