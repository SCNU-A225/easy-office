package scnu.a225.easyoffice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("scnu.a225.easyoffice.dao")
@SpringBootApplication
public class EasyOfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyOfficeApplication.class, args);
    }

}
