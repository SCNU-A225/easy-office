package scnu.a225.easyoffice.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import scnu.a225.easyoffice.entity.Department;

import java.util.List;

@Component
public interface DepartmentDao {
    @Insert("insert into department values (#{sn},#{name},#{address})")
    void insert(Department department);
    @Update("update department set name = #{name},address = #{address} where sn = #{sn}")
    void update(Department department);
    @Delete("delete from department where sn = #{sn}")
    void delete(String sn);
    @Select("select * from department where sn = #{sn}")
    Department select(String sn);
    @Select("select * from department")
    List<Department> selectAll();
}
