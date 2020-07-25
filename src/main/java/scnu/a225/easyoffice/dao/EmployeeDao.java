package scnu.a225.easyoffice.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import scnu.a225.easyoffice.entity.Employee;

@Component
public interface EmployeeDao {
    @Select("SELECT * FROM employee WHERE sn=#{sn} and password=#{password}")
    Employee selectEmployee(String sn, String password);
}
