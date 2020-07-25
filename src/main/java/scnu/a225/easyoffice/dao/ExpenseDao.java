package scnu.a225.easyoffice.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ExpenseDao {

    // 获取报销单信息
    @Select("SELECT * FROM claim_voucher WHERE id = #{id}")
    Map<String,Object> selectVoucher(int id);

    // 获取报销单item
    @Select("SELECT item, amount, comment FROM claim_voucher_item WHERE claim_voucher_id=#{id}")
    List<Map<String,Object>> selectVoucherItems(int id);

    // 获取报销单处理记录

}
