package cn.com.wtj.core.http.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created on 2019/8/20.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Configuration
@ComponentScan(basePackages = {"cn.com.wtj"})
@EnableWebMvc   // 启用spring mvc
@EnableSpringDataWebSupport     // 启用springmvc对spring data的支持
public class WebMvcConfig extends WebMvcConfigurerAdapter {
}
