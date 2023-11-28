package Annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface PostCondition {
    String condition();
}
