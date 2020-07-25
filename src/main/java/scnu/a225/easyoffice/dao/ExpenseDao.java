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
    @Select("SELECT deal_sn, deal_time, deal_way, deal_result, comment FROM deal_record WHERE claim_voucher_id=#{id}")
    List<Map<String,Object>> selectRecord(int id);

    // 获取个人所有报销单
    @Select("SELECT id, cause, create_time, total_amount, status FROM claim_voucher WHERE create_sn=#{sn}")
    List<Map<String,Object>> selectAllVoucher(String sn);
}
