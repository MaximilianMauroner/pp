package Annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE})
public @interface HistoryConstraint {
    String constraint();
}


