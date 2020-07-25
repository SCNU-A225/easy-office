package scnu.a225.easyoffice.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scnu.a225.easyoffice.config.ShiroConfig;
import scnu.a225.easyoffice.dao.ExpenseDao;
import scnu.a225.easyoffice.utils.Result;

import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpSession;
import java.io.ObjectInput;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ExpenseController
 * @Description: 报销单Conrtoller
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
    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    @Autowired
    ExpenseDao expenseDao;

    /**
     * 创建报销单
     */
    @PostMapping("/create")
    public Object create(String formData, HttpServletRequest request) {
        JSONObject jsonObject = JSONObject.parseObject(formData);
        return "ok";
    }

    /**
     * TODO
     * 3.3提交报销单
     */
    @PostMapping("/submit")
    public Object submitVoucher(HttpSession session, int id, double amount){
        String deal_sn = (String) session.getAttribute("sn");
        String department_sn = (String) session.getAttribute("department_sn");
        String status = "已提交";
        String deal_way = "提交";
        String deal_result = "已提交";
        String comment = "提交报销单";
        String next_deal_sn = "部门经理";
        if (amount < 5000) next_deal_sn = expenseDao.randEmployee(department_sn,"部门经理");
        else next_deal_sn = expenseDao.randNextDeal("总经理");
        expenseDao.updateVoucherStatus(next_deal_sn,status,id);
        expenseDao.insertRecord(id,deal_sn,deal_way,deal_result,comment);
        return new Result(200,"提交成功");
    }

    /**
     * 3.4通过报销单
     */
    @PostMapping("/agree")
    public Object agreeVoucher(HttpSession session, int id, String comment){
        String deal_sn = (String) session.getAttribute("sn");
        String status = "已通过";
        String deal_way = "审核";
        String deal_result = "通过";
        String next_deal_sn = expenseDao.randNextDeal("财务");
        expenseDao.updateVoucherStatus(next_deal_sn,status,id);
        expenseDao.insertRecord(id,deal_sn,deal_way,deal_result,comment);
        return new Result(200,"审核成功");
    }


    /**
     * 3.5打回报销单
     */
    @PostMapping("/return")
    public Object returnVoucher(HttpSession session, int id, String comment){
        String deal_sn = (String) session.getAttribute("sn");
        String status = "已打回";
        String deal_way = "审核";
        String deal_result = "打回";
        String create_sn = expenseDao.selectVoucherOwner(id);
        expenseDao.updateVoucherStatus(create_sn,status,id);
        expenseDao.insertRecord(id,deal_sn,deal_way,deal_result,comment);
        return new Result(200,"审核成功");
    }

    /**
     * 3.6拒绝报销单
     */
    @PostMapping("/refuse")
    public Object refuseVoucher(HttpSession session,int id, String comment){
        String deal_sn = (String) session.getAttribute("sn");
        String next_deal_sn = "00000";
        String status = "已拒绝";
        String deal_way = "审核";
        String deal_result = "已拒绝";
        expenseDao.updateVoucherStatus(next_deal_sn,status,id);
        expenseDao.insertRecord(id,deal_sn,deal_way,deal_result,comment);
        return new Result(200,"审核成功");
    }

    /**
     * 3.7打款报销单
     */
    @PostMapping("/pay")
    public Object payVoucher(HttpSession session,int id, String comment){
        String deal_sn = (String) session.getAttribute("sn");
        String next_deal_sn = "00000";
        String status = "已打款";
        String deal_way = "审核";
        String deal_result = "已打款";
        expenseDao.updateVoucherStatus(next_deal_sn,status,id);
        expenseDao.insertRecord(id,deal_sn,deal_way,deal_result,comment);
        return new Result(200,"审核成功");
    }

    /**
     * 3.8查看待处理报销单
     */
    @GetMapping("todo")
    public Map<String,Object> getTodo(HttpSession session){
        Map<String,Object> result = new HashMap<>();
        String sn = (String) session.getAttribute("sn");
        result.put("arr",expenseDao.selectTodo(sn));
        return result;
    }

    /**
     * 查看报销单详细
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
