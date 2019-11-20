package cloud.tianai.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableAsync
@MapperScan(basePackages = {"cloud.tianai.order.core.mapper"})
@ImportResource(locations = {"classpath:dubbo/*.xml"})
@SpringBootApplication
@EnableDiscoveryClient
public class OrderCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderCoreApplication.class, args);
    }
}
