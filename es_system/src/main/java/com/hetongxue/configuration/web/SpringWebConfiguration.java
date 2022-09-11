package com.hetongxue.configuration.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hetongxue.constant.Base;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * @Description: spring web configuration class
 *
 * @Class: SpringWebConfiguration
 *
 * @Author: hetongxue
 *
 * @DateTime: 2022/9/11 23:54:26
 */
@Configuration
@EnableWebMvc
public class SpringWebConfiguration implements WebMvcConfigurer {

    private final String[] classpathResourceLocations = {"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/", "classpath:/META-INF/resources/webjars/"};
    @Value("${spring.jackson.time-zone}")
    private String timeZone;
    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    /**
     * 资源处理器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(classpathResourceLocations);
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // add map path
        registry.addMapping("/**")
                // which original request headers to pass
                .allowedHeaders("*")
                // what header information is exposed
                .exposedHeaders("*")
                // which request methods are allowed
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE", "TRACE", "HEAD", "PATCH")
                // whether to send cookies
                .allowCredentials(true)
                // maximum time
                .maxAge(3600L)
                // exposed head
                .exposedHeaders(Base.AUTHORIZATION_KEY).allowedOriginPatterns("*");
    }

    /**
     * 解决json返回时间显示时间戳问题
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = converter.getObjectMapper();
        // when generating json convert all long to string
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance).addSerializer(Long.TYPE, ToStringSerializer.instance);
        // time formatting
        objectMapper.registerModule(simpleModule).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).setDateFormat(new SimpleDateFormat(dateFormat)).setTimeZone(TimeZone.getTimeZone(timeZone));
        // set formatted content
        converter.setObjectMapper(objectMapper);
        converters.add(0, converter);
    }

}