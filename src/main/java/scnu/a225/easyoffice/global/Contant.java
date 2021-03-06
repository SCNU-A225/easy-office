package scnu.a225.easyoffice.global;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Contant
 * @Description 全局常量类
 * @Author Lin
 * @Date 2020/7/25 18:35
 * @Version 1.0
 */
public class Contant {
    // 职务
    public static final String POST_STAFF="员工";
    public static final String POST_FM="部门经理";
    public static final String POST_GM="总经理";
    public static final String POST_CASHIER="财务";
    public static List<String> getPost() {
        List<String> list = new ArrayList<String>();
        list.add(POST_STAFF);
        list.add(POST_FM);
        list.add(POST_GM);
        list.add(POST_CASHIER);
        return list;
    }

    // 费用类别
    public static List<String> getItems() {
        List<String> list = new ArrayList<String>();
        list.add("交通");
        list.add("餐饮");
        list.add("住宿");
        list.add("办公");
        return list;
    }

    // 报销单状态
    public static final String CLAIMVOUCHER_CREATED="已创建";
    public static final String CLAIMVOUCHER_SAVED="已保存";
    public static final String CLAIMVOUCHER_SUBMIT="已提交";
    public static final String CLAIMVOUCHER_BACK="已打回";
    public static final String CLAIMVOUCHER_UPDTAE="已修改";
    public static final String CLAIMVOUCHER_APPROVED="已审核";
    public static final String CLAIMVOUCHER_REJECTED="已拒绝";
    public static final String CLAIMVOUCHER_ADOPTED="已通过";
    public static final String CLAIMVOUCHER_TERMINATED="已终止";
    public static final String CLAIMVOUCHER_RECHECK="待复审";
    public static final String CLAIMVOUCHER_PAID="已打款";

    // 审核额度
    public static final double LIMIT_CHECK=5000.00;

    // 系统工号
    public static final String ADMIN_SN="00000";

    // 处理方式
    public static final String DEAL_CREATE="创建";
    public static final String DEAL_SUBMIT="提交";
    public static final String DEAL_CHECK="审核";
    public static final String DEAL_UPDATE="修改";
    public static final String DEAL_BACK="打回";
    public static final String DEAL_REJECT="拒绝";
    public static final String DEAL_PASS="通过";
    public static final String DEAL_PAID="打款";
}
