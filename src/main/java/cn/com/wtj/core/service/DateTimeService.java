package cn.com.wtj.core.service;

import org.springframework.lang.NonNull;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

/**
 * Created on 2019/8/21.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
public interface DateTimeService {


    /**
     * Now optional.
     *
     * @return the optional
     */
    @NonNull
    Optional<TemporalAccessor> getNow();
}
