package scnu.a225.easyoffice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scnu.a225.easyoffice.dao.ExpenseDao;
import scnu.a225.easyoffice.global.Contant;
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
     * 3.1创建报销单
     */
    @PostMapping("/create")
    public Object create(String formData, HttpServletRequest request, HttpSession session) {
        try {
            String createSn = (String) session.getAttribute("sn");
            JSONObject data = JSONObject.parseObject(formData);
            String cause = data.getString("cause");
            Double total_amount = data.getDouble("total_amount");
            JSONArray items = data.getJSONArray("items");
            if (null==items || items.toJSONString().isEmpty() || items.toString().equals("undefined"))
                return new Result(401, "报销单明细不能为空");
            else if (null==cause || cause.isEmpty() || cause.equals("undefined"))
                return new Result(402, "事由不能为空");
            Result rest = new Result();
            expenseDao.insertClaimVoucher(cause, createSn, createSn, total_amount, Contant.CLAIMVOUCHER_CREATED, rest);
            int claimVoucherId = rest.getCode();
            if (claimVoucherId > 0) {
                expenseDao.insertDealRecord(claimVoucherId,createSn,Contant.DEAL_CREATE,Contant.CLAIMVOUCHER_SAVED,"创建报销单");
                for (int i = 0; i < items.size(); i++) {
                    JSONArray item = items.getJSONArray(i);
                    expenseDao.insertClaimVoucherItem(claimVoucherId,item.getString(0),item.getDouble(1),item.getString(2));
                }
                return new Result(200, "保存成功");
            } else {
                return new Result(403, "插入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(500, "未知错误");
        }
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
        return result;
    }
}
