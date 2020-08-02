package jqhk.ssm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication()
@EnableAsync
public class SsmApplication {

    public static void main(String[] args) {
        System.out.println(System.getProperty("file.encoding"));
        SpringApplication.run(SsmApplication.class, args);
    }
}
