package scnu.a225.easyoffice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scnu.a225.easyoffice.dao.EmployeeDao;
import scnu.a225.easyoffice.entity.Employee;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: EmployeeController
 * @Description: TODO
 * @Author: jiangjian
 * @CreateDate: 2020/7/25 18:57
 * @UpdateUser: jiangjian
 * @UpdateDate: 2020/7/25 18:57
 * @UpdateRemark: TODO
 * @Version: V1.0
 */
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeDao employeeDao;

    @PostMapping("/user/login")
    public Map<String, Object> login(String sn, String password) {
        Subject subject = SecurityUtils.getSubject();   //获取当前用户
        UsernamePasswordToken token = new UsernamePasswordToken(sn, password);    //封装令牌
        try {
            subject.login(token);   //登录
            Employee employee = employeeDao.selectEmployee(sn, password);
            Assert.notNull(employee, "用户不可能为空");
            return new HashMap<String, Object>() {{
                put("code", 200);
                put("sn", sn);
                put("password", password);
                put("post", employee.getPost());
                put("department", employee.getDepartment());
            }};
        } catch (UnknownAccountException e) {
            return new HashMap<String, Object>() {{
                put("msg", "用户名或密码不正确");
            }};
        } catch (IncorrectCredentialsException e) {
            return new HashMap<String, Object>() {{
                put("msg", "IncorrectCredentialsException");
            }};
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        return null;
    }

}
