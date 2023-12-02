package Annotations;

import java.lang.annotation.*;

@Author(name = "Maximilian Mauroner")

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface PostCondition {
    String condition();
}
