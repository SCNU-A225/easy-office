package scnu.a225.easyoffice.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import scnu.a225.easyoffice.dao.ExpenseDao;

import javax.servlet.http.HttpServletRequest;
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
     * 查看报销单详细
     */
    public Map<String,Object> expenseDetail(int id){
        return null;
    }
}
