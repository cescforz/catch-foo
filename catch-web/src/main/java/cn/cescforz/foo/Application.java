package cn.cescforz.foo;

import cn.cescforz.foo.annotation.security.EnableEncryptBody;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@EnableEncryptBody
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

