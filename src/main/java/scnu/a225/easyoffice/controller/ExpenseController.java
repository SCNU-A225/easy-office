package scnu.a225.easyoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import scnu.a225.easyoffice.dao.ExpenseDao;

import java.util.Map;

/**
 * @ClassName: ExpenseController
 * @Description: TODO
 * @Author: jiangjian
 * @CreateDate: 2020/7/25 22:56
 * @UpdateUser: jiangjian
 * @UpdateDate: 2020/7/25 22:56
 * @UpdateRemark: TODO
 * @Version: V1.0
 */
public class ExpenseController {

    @Autowired
    ExpenseDao expenseDao;

    /**
     * 查看报销单详细
     */
    public Map<String,Object> expenseDetail(int id){
        return null;
    }
}
