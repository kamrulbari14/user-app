package com.kamrul.userapp.annotation;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Custom annotation used to mark controllers as API controllers. This annotation combines the
 * functionality of various Spring annotations such as @Controller, @CrossOrigin, and @ResponseBody.
 * It provides a convenient way to specify controller mappings for APIs and enables cross-origin
 * resource sharing (CORS).
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@CrossOrigin(origins = "*", methods = {GET, POST, PUT, DELETE, OPTIONS})
@ResponseBody
public @interface ApiController {

  @AliasFor(annotation = Controller.class) String value() default "";
}
