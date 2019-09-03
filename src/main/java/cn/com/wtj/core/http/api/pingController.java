package cn.com.wtj.core.http.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created on 2019/9/2.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@RestController
@RequestMapping("/ping")
public class pingController {

    @RequestMapping
    public String ping(){
        return "ping" + LocalDateTime.now();
    }

}
