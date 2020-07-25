package scnu.a225.easyoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scnu.a225.easyoffice.dao.EmployeeDao;
import scnu.a225.easyoffice.entity.Employee;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author Lin
 * @Date 2020/7/25 18:24
 * @Version 1.0
 */
@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public Employee selectEmployee(String sn, String password){
        return employeeDao.selectEmployee(sn, password);
    }
}
