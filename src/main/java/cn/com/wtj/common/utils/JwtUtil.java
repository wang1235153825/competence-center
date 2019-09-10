package cn.com.wtj.common.utils;

import com.nimbusds.jwt.JWTClaimsSet;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created on 2019/9/10.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
public class JwtUtil {

    public static LocalDateTime expirationTime(JWTClaimsSet jwtClaimsSet){
        Date time  = jwtClaimsSet.getExpirationTime();
        if (time == null){
            return null;
        }
        return LocalDateTimeUtil.dateForLocalDateTime(time);
    }
}
