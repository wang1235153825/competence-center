package cn.com.wtj;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@SpringBootApplication
public class CompetenceCenterApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(CompetenceCenterApplication.class).run(args);
    }

}
