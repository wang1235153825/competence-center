package cn.com.wtj.common.utils;

import org.aspectj.apache.bcel.generic.RET;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created on 2019/9/10.
 * LocalDateTime 和 date 转换工具类
 * @author wangtingjun
 * @since 1.0.0
 */
public class LocalDateTimeUtil {

    private static final String ZONE_TYPE_SHANGHAI = "Asia/Shanghai";
    private static final String ZONE_TYPE_CHONGQING = "Asia/Chongqing";

    public static LocalDateTime dateForLocalDateTime(Date date){
        //表示时刻，不直接对应年月日信息，需要通过时区转换
        Instant instant = date.toInstant();
        //上海时间
        ZoneId zoneId = ZoneId.of(ZONE_TYPE_SHANGHAI);
        return instant.atZone(zoneId).toLocalDateTime();
    }

}
