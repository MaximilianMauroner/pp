package Annotations;

import java.lang.annotation.*;

@Author(name = "Maximilian Mauroner")

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface PreCondition {
    String condition();
}