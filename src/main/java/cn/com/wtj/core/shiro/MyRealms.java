package cn.com.wtj.core.shiro;

import cn.com.wtj.core.repository.UserRepository;
import cn.com.wtj.core.repository.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/9/5.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Component
public class MyRealms extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return true;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) auth;
        User user = userRepository.findByUserName(token.getUsername()).orElseThrow(
                () -> new RuntimeException("用户名不存在")
        );
        System.out.println(getName());
        return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
    }
}
