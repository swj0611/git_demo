package net.codejava.spring.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.*;
@Order(1)
public class SecurityWebApplicationInitializer
	extends AbstractSecurityWebApplicationInitializer {

}