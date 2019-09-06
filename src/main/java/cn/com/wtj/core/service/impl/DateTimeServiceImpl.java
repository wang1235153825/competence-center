package cn.com.wtj.core.service.impl;

import cn.com.wtj.core.service.DateTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

/**
 * Created on 2019/8/22.
 * jpa的@createDate等标签的字段自动注入
 * @author wangtingjun
 * @since 1.0.0
 */
@Slf4j
@Service
public class DateTimeServiceImpl implements DateTimeService {

    @Override
    @NonNull
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(LocalDateTime.now());
    }
}