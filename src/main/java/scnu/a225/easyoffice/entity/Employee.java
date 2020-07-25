package scnu.a225.easyoffice.entity;

/**
 * @ClassName Employee
 * @Description 员工实体类
 * @Author Lin
 * @Date 2020/7/25 18:29
 * @Version 1.0
 */
public class Employee {

    @Override
    public String toString() {
        return "Employee{" +
                "sn='" + sn + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", departmentSn='" + departmentSn + '\'' +
                ", post='" + post + '\'' +
                ", department=" + department +
                '}';
    }

    // 职员编号
    private String sn;
    // 密码
    private String password;
    // 名字
    private String name;
    // 所属部门编号
    private String departmentSn;
    // 职务
    private String post;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentSn() {
        return departmentSn;
    }

    public void setDepartmentSn(String departmentSn) {
        this.departmentSn = departmentSn;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
