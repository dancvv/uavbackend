package com.mountain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//扫描mapper文件夹
@MapperScan("com.mountain.Mapper")
public class MountainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MountainApplication.class, args);
    }

}
