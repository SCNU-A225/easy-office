package scnu.a225.easyoffice.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import scnu.a225.easyoffice.entity.Employee;
import scnu.a225.easyoffice.service.EmployeeService;

/**
 * @ClassName: ShiroConfig
 * @Description: TODO
 * @Author: jiangjian
 * @CreateDate: 2020/7/25 18:46
 * @UpdateUser: jiangjian
 * @UpdateDate: 2020/7/25 18:46
 * @UpdateRemark: TODO
 * @Version: V1.0
 */
@Configuration
public class ShiroConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        //chainDefinition.addPathDefinition("/", "anon");
        chainDefinition.addPathDefinition("/user/login", "anon");
        //defaultShiroFilterChainDefinition.addPathDefinition("/logout", "logout"); //shiro注销页
        //defaultShiroFilterChainDefinition.addPathDefinition("/**", "authc"); //其它路径需要登录
        chainDefinition.addPathDefinition("/**", "authc"); //全部路径的授权交由注解控制
        return chainDefinition;
    }

    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

}

class UserRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizingRealm.class);

    public UserRealm() {
        super(new MemoryConstrainedCacheManager()); //缓存
    }

    @Autowired
    private EmployeeService employeeService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();  //principal是在认证的时候设置的
        info.addRole(employee.getPost());

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        Employee employee = employeeService.selectEmployee(userToken.getUsername(), String.valueOf(userToken.getPassword()));

        if (null == employee) {
            return null;    //UnknownAccountException
        }

        return new SimpleAuthenticationInfo(employee,employee.getPassword(),"");
    }
}

/*
 * 用于解决shiro注解检测抛出的异常，避免500页面
 * */
//@ControllerAdvice
//class AuthExceptionHandler {
//    @ExceptionHandler(AuthorizationException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public String handleException(AuthorizationException e, Model model) {
//        return "error";
//    }
//}
