package scnu.a225.easyoffice.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import scnu.a225.easyoffice.entity.Employee;

import java.util.List;
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

//    添加员工
    @Insert("INSERT INTO employee VALUES(#{sn},#{password},#{name},#{department_sn},#{post})")
    void insert(Employee employee);
//    删除员工
    @Delete("DELETE FROM employee WHERE sn=#{sn}")
    void delete(String sn);
//    按部门查找员工
    @Select("SELECT employee.sn, employee.name, employee.department_sn, employee.post, department.name as departmentName FROM employee,department WHERE employee.department_sn = department.sn and employee.department_sn=#{department_sn}")
    List<Employee> getInfoByDepartment_sn(String department_sn);

    //    所有员工信息
    @Select("SELECT employee.sn, employee.name, employee.department_sn, employee.post, department.name as departmentName " +
            "FROM employee,department WHERE employee.department_sn=department.sn")
    Map<String, Object> getAllEmployee();
//    所有员工信息
    @Select("SELECT employee.sn, employee.name, employee.department_sn, employee.post, department.name as departmentName " +
            "FROM employee,department WHERE employee.department_sn=department.sn and department.name=#{department_name}")
    Map<String, Object> getEmployeeByDepartmentName(String department_name);

//   按员工编号查找员工信息
    @Select("SELECT employee.sn, employee.name, employee.department_sn, employee.post, department.name as departmentName FROM employee,department WHERE employee.department_sn = department.sn and employee.sn=#{sn}")
    List<Employee> getInfoBySn(String sn);
//    修改员工信息
    @Select("UPDATE employee set name=#{name}, department_sn=#{department_sn}, post=#{post} WHERE sn=#{sn}")
    void update(Employee employee);
//    查看存在
    @Select("select * from employee where sn = #{sn}")
    Employee checkIsExits(String sn);
}
