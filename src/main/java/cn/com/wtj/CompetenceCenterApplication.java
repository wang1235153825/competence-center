package cn.com.wtj;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@SpringBootApplication
public class CompetenceCenterApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(CompetenceCenterApplication.class).run(args);
    }

}
