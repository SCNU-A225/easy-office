package scnu.a225.easyoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scnu.a225.easyoffice.dao.ExpenseDao;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
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

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    ExpenseDao expenseDao;

    /**
     * 3.9查看个人报销单
     */
    @GetMapping("/history")
    public Map<String,Object> getHistory(HttpSession session){
        Map<String,Object> result = new HashMap<>();
        String create_sn = (String) session.getAttribute("sn");
        result.put("arr",expenseDao.selectAllVoucher(create_sn));
        return result;
    }

    /**
     * 3.10查看报销单详细
     */
    @PostMapping("/detail")
    public Map<String,Object> expenseDetail(int id){
        Map<String,Object> result = new HashMap<>();
        result.put("info",expenseDao.selectVoucher(id));
        result.put("detail",expenseDao.selectVoucherItems(id));
        result.put("record",expenseDao.selectRecord(id));
        System.out.println(result);
        return result;
    }
}
