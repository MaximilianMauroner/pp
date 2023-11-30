package Annotations;

import java.lang.annotation.*;

@Author(name = "Maximilian Mauroner")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Author {
    String name();

}
