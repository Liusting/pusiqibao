package com.pusiqibao;


import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })

public class PusiqibaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PusiqibaoApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  普斯汽保启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
