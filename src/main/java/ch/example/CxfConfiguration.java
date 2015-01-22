package ch.example;

import org.apache.cxf.bus.spring.SpringBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfiguration {

    @Bean(name = "cxf", destroyMethod = "shutdown")
    public SpringBus configureCxfBus() {
        final SpringBus bus = new SpringBus();
        bus.setId("cxf");

        return bus;
    }

}
