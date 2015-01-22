package ch.example;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@Configuration
@EnableAutoConfiguration
@ComponentScan
class Application {

    private static final String CAMEL_URL_MAPPING = "/camel/*";
    private static final String CXF_URL_MAPPING = "/cxf/*";

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new CamelHttpTransportServlet(), CAMEL_URL_MAPPING);
    }

    @Bean
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), CXF_URL_MAPPING);
    }
}