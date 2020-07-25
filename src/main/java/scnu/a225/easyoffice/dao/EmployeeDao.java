package scnu.a225.easyoffice.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import scnu.a225.easyoffice.entity.Employee;

import java.util.Map;

@Component
public interface EmployeeDao {
    @Select("SELECT * FROM employee WHERE sn=#{sn} and password=#{password}")
    Employee selectEmployee(String sn, String password);

    @Select("SELECT employee.sn,password,employee.name,department.name as department_name,department.sn as department_sn,post FROM employee,department " +
            "WHERE employee.sn=#{sn} and password=#{password} and employee.department_sn=department.sn")
    Map<String, Object> getEmployeeInfo(String sn, String password);

    @Update("UPDATE employee SET password=#{newPassword} WHERE sn=#{sn} and password=#{password}")
    int changePassword(String password, String newPassword, String sn);

}
