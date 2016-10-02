package org.ranceworks.nginxlog;

import org.ranceworks.nginxlog.conf.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class NginxLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(NginxLogApplication.class, args);
	}
	

}

