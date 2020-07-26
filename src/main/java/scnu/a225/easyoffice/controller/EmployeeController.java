package scnu.a225.easyoffice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import scnu.a225.easyoffice.dao.DepartmentDao;
import scnu.a225.easyoffice.dao.EmployeeDao;
import scnu.a225.easyoffice.entity.Employee;
import scnu.a225.easyoffice.global.Contant;
import scnu.a225.easyoffice.utils.ControllerUtil;
import scnu.a225.easyoffice.utils.Result;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private DepartmentDao departmentDao;

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

   /*
    2.1 添加员工
    */
    @PostMapping("/employee/add")
    @RequiresRoles(value = "总经理")
    public Object employee_add(Employee employee) {
        if (null == employee.getSn()|| employee.getSn().isEmpty())
            return new Result(401, "请输入员工编号");
        if (null == employee.getName() || employee.getName().isEmpty())
            return new Result(402, "请输入员工姓名");
        if (null == employee.getPassword() || employee.getPassword().isEmpty())
            return new Result(403, "请输入密码");
        if (null == employee.getDepartmentSn() || employee.getDepartmentSn().isEmpty())
            return new Result(404, "请输入部门编号");
        if (null == employee.getPost() || employee.getPost().isEmpty())
            return new Result(405, "请输入职务");
        if (employeeDao.checkIsExits(employee.getSn())!=null)
            return new Result(501, "员工编号已存在");
        if (departmentDao.checkIsExits(employee.getDepartmentSn(), employee.getDepartment().getName())==null)
            return new Result(501, "部门编号不存在");

        employeeDao.insert(employee);
        return new Result(200, "添加成功");
    }

    /*
    2.2 删除员工
     */
    @DeleteMapping("/delete")
    @RequiresRoles(value = "总经理")
    public Object deleteEmployee(String sn) {
        employeeDao.delete(sn);
        return new Result(200, "删除成功");
    }

   /*
    2.3按部门查找员工
     */
    @GetMapping("/employee/departlist")
    @RequiresRoles(value = "总经理")
    public JSONObject employeeInfoByD_sn(String department_sn){
        List<Employee> list = employeeDao.getInfoByDepartment_sn(department_sn);
        JSONObject json = new JSONObject();
        json.put("employees", list.toString());
        return json;
    }

    /*
    2.4 查找所有员工
    */
    @PostMapping("/employee/all")
    public Object employee_all(String post, String department,HttpSession session) {
        if (!ControllerUtil.stringNotNull(post)) return new Result(400, "职位不能为空");
        else if (!ControllerUtil.stringNotNull(department)) return new Result(401, "部门名称不能为空");
        Map<String, Object> data;
        if (Contant.POST_GM.equals(post)) {
            data = employeeDao.getAllEmployee();
        } else {
            data = employeeDao.getEmployeeByDepartmentName(department);
        }
        JSONObject json = new JSONObject();
        json.put("employees", JSON.toJSONString(data));
        logger.info(json.toJSONString());
        return json;
    }

    /*
    2.5 查找一个员工
    */
    @PostMapping("/employee/info")
    @RequiresRoles(value = "总经理")
    public JSONObject employeeInfo(String sn) {
        List list = employeeDao.getInfoBySn(sn);
        JSONObject json = new JSONObject();
        json.put("employee", list.toString());
        return json;
    }

    /*
    2.6 修改员工信息
     */
    @PostMapping("/employee/update")
    @RequiresRoles(value = "总经理")
    public Object updateEmployee(Employee employee,String sn, String department_sn, String post) {
        if (null == employee.getDepartmentSn() || employee.getDepartmentSn().isEmpty())
            return new Result(401, "请输入部门编号");
        if (null == employee.getPost() || employee.getPost().isEmpty())
            return new Result(402, "请输入职务");
        employeeDao.update(employee);
        return new Result(200, "更新成功");

    }

}
