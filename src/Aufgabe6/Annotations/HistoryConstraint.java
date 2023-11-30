package Annotations;

import java.lang.annotation.*;

@Author(name = "Maximilian Mauroner")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE})
public @interface HistoryConstraint {
    String constraint();
}


