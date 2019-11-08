package cloud.tianai.order.business.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "cloud.tianai.order.business.core.mapper")
@ImportResource(locations = {"classpath:dubbo/*.xml"})
public class OrderBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderBusinessApplication.class, args);
    }
}
