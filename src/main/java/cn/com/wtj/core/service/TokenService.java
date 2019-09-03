package cn.com.wtj.core.service;

import cn.com.wtj.core.repository.entity.User;
import cn.com.wtj.entity.TokenDetail;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
public interface TokenService {

    TokenDetail createToken(User user, Long clientId);

}
