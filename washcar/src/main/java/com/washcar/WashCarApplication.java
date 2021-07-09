package com.washcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class WashCarApplication {

    public static void main(String[] args) {
        SpringApplication.run(WashCarApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  精洗车启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
