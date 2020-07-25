package scnu.a225.easyoffice.utils;

/**
 * @ClassName: Result
 * @Description: TODO
 * @Author: jiangjian
 * @CreateDate: 2020/7/25 22:24
 * @UpdateUser: jiangjian
 * @UpdateDate: 2020/7/25 22:24
 * @UpdateRemark: TODO
 * @Version: V1.0
 */
public class Result {
    private int code;
    private String msg;

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public Result() {
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
