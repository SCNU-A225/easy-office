package scnu.a225.easyoffice.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scnu.a225.easyoffice.dao.DepartmentDao;
import scnu.a225.easyoffice.entity.Department;
import scnu.a225.easyoffice.utils.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DepartmentController
 * @Description TODO
 * @Author Lin
 * @Date 2020/7/25 22:44
 * @Version 1.0
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentDao departmentDao;

    @GetMapping("/all")
    @RequiresRoles(value = {"总经理","部门经理"},logical = Logical.OR)
    public Map<String, Object> list() {
        return new HashMap<String, Object>() {{
            put("departmentList", departmentDao.selectAll());
        }};
    }

    @PostMapping("/add")
    @RequiresRoles(value = "总经理")
    public Object addDepartment(Department department) {
        if (null == department.getSn()|| department.getSn().isEmpty()) return new Result(401, "请输入部门编号");
        if (null == department.getName() || department.getName().isEmpty()) return new Result(402, "请输入部门名称");
        if (null == department.getAddress() || department.getAddress().isEmpty()) return new Result(403, "请输入部门地址");
        if (departmentDao.checkIsExits(department.getSn(), department.getName())!=null) return new Result(501, "部门编号或部门名称已存在");

        departmentDao.insert(department);

        return new Result(200, "添加成功");
    }

    @PostMapping("/update")
    @RequiresRoles(value = "总经理")
    public Object updateDepartment(Department department) {
        if (null == department.getName() || department.getName().isEmpty()) return new Result(402, "请输入部门名称");
        if (null == department.getAddress() || department.getAddress().isEmpty()) return new Result(403, "请输入部门地址");

        departmentDao.update(department);

        return new Result(200, "更新成功");
    }

    @PostMapping("/delete")
    @RequiresRoles(value = "总经理")
    public Object deleteDepartment(String sn) {
        departmentDao.delete(sn);

        return new Result(200, "删除成功");
    }

    @PostMapping("/info")
    @RequiresRoles(value = "总经理")
    public  Object getDepartmentInfo(String sn) {
        return new HashMap<String, Object>() {{
           put("department", departmentDao.select(sn));
        }};
    }
}
