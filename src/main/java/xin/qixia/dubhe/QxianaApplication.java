package xin.qixia.dubhe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "xin.qixia.dubhe.datasource.mapper")
public class QxianaApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxianaApplication.class, args);
    }

}
