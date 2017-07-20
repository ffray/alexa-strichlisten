package de.frayit.strichlisten;

import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Collections.singleton;

@SpringBootApplication
@Configuration
public class StrichlistenApp {

    @Bean
    public Strichliste strichliste() {
        return new Strichliste();
    }

    @Bean
    public FilterRegistrationBean requestLogger() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new RequestLoggerFilter());
        bean.setOrder(0);
        bean.setUrlPatterns(singleton("/*"));

        return bean;
    }

    @Bean
    public ServletRegistrationBean strichlistenSpeechlet() {
        SpeechletServlet servlet = new SpeechletServlet();
        servlet.setSpeechlet(new StrichlistenSpeechlet(strichliste()));

        ServletRegistrationBean bean  = new ServletRegistrationBean();
        bean.setServlet(servlet);
        bean.setLoadOnStartup(0);
        bean.setUrlMappings(singleton("/strichlisten"));

        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(StrichlistenApp.class, args);
    }

}
