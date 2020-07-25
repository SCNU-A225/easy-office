package scnu.a225.easyoffice.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import scnu.a225.easyoffice.entity.Employee;

import java.util.Map;

@Component
public interface EmployeeDao {
    @Select("SELECT * FROM employee WHERE sn=#{sn} and password=#{password}")
    Employee selectEmployee(String sn, String password);

    @Select("SELECT * FROM employee,department")
    Map<String, Object> getEmployeeInfo(String sn, String password);
}
