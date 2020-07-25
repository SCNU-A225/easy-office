package scnu.a225.easyoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scnu.a225.easyoffice.dao.DepartmentDao;

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
    public Map<String, Object> list() {
        return new HashMap<String, Object>() {{
            put("departmentList", departmentDao.selectAll());
        }};
    }
}
