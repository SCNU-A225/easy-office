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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import scnu.a225.easyoffice.dao.EmployeeDao;
import scnu.a225.easyoffice.entity.Employee;
import scnu.a225.easyoffice.utils.Result;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeDao employeeDao;

    @PostMapping("/user/login")
    public Object login(String sn, String password, HttpSession session) {
        logger.info("登录");
        if(null == sn || sn.isEmpty()) return new Result(401, "请输入工号");
        else if(null == password || password.isEmpty()) return new Result(401, "请输入密码");

        Subject subject = SecurityUtils.getSubject();   //获取当前用户
        UsernamePasswordToken token = new UsernamePasswordToken(sn, password);    //封装令牌
        try {
            subject.login(token);   //登录
            Map<String, Object> employeeInfo = employeeDao.getEmployeeInfo(sn, password);
            Assert.notNull(employeeInfo, "用户不可能为空");

            session.setAttribute("sn", sn);
            session.setAttribute("name", employeeInfo.get("name"));
            session.setAttribute("post", employeeInfo.get("post"));
            session.setAttribute("department_sn", employeeInfo.get("department_sn"));
            session.setAttribute("department", employeeInfo.get("department_name"));

            return new HashMap<String, Object>() {{
                put("code", 200);
                put("sn", sn);
                put("name", employeeInfo.get("name"));
                put("post", employeeInfo.get("post"));
                put("department", employeeInfo.get("department_name"));
            }};
        } catch (UnknownAccountException e) {
            return new Result(403, "工号或密码不正确");
        } catch (IncorrectCredentialsException e) {
            return new Result(404, "IncorrectCredentialsException");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(405, "未知错误");
        }
    }

    @GetMapping("/logout")
    public Object logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();   //shiro注销
        return new Result(200, "注销成功");
    }

    @GetMapping("/info")
    public Object info(HttpSession session) {
        return new HashMap<String, Object>() {{
            put("sn", session.getAttribute("sn"));
            put("name", session.getAttribute("name"));
            put("post", session.getAttribute("post"));
            put("department", session.getAttribute("department"));
        }};
    }

    @PostMapping("/password")
    public Object password(String password, String newPassword, HttpSession session) {
        String sn = (String) session.getAttribute("sn");
        int res = employeeDao.changePassword(password, newPassword, sn);
        if (res == 1) return new Result(200, "修改成功");
        else return new Result(401, "原密码错误");
    }


}
