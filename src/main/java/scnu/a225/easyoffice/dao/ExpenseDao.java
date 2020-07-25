package scnu.a225.easyoffice.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import scnu.a225.easyoffice.utils.Result;

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

    @Insert("INSERT INTO claim_voucher VALUES(null,#{cause},#{create_sn},now(),#{next_deal_sn},#{total_amount},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "res.code", keyColumn = "id")
    int insertClaimVoucher(String cause, String create_sn, String next_deal_sn, Double total_amount, String status, Result res);

    @Insert("INSERT INTO deal_record values(null,#{claim_voucher_id},#{create_sn},now(),#{deal_way},#{deal_result},#{comment})")
    int insertDealRecord(Integer claim_voucher_id, String create_sn, String deal_way, String deal_result, String comment);

    @Insert("INSERT INTO claim_voucher_item VALUES(null,#{claim_voucher_id},#{item},${amount},#{comment})")
    int insertClaimVoucherItem(Integer claim_voucher_id, String item, Double amount, String comment);

    // 获取待处理报销单
    @Select("SELECT c.id, c.cause, c.create_time, c.total_amount, c.status, e.name FROM claim_voucher c," +
            "employee e WHERE c.next_deal_sn=#{sn} AND c.create_sn=e.sn")
    List<Map<String,Object>> selectTodo(String sn);

    // 修改报销单状态
    @Update("update claim_voucher set next_deal_sn=#{next_deal_sn},status=#{status} WHERE id=#{claim_voucher_id}")
    void updateVoucherStatus(String next_deal_sn,String status,int claim_voucher_id);

    // 插入报销单处理记录
    @Insert({"insert into deal_record values(null,#{claim_voucher_id},#{deal_sn},NOW(),#{deal_way},#{deal_result},#{comment})"})
    void insertRecord(int claim_voucher_id,String deal_sn,String deal_way,String deal_result,String comment);

    // 获取报销单的创建者
    @Select("SELECT create_sn FROM claim_voucher WHERE id=#{id}")
    String selectVoucherOwner(int id);

    // 随机抽取一名某职位职员的sn
    @Select("SELECT sn FROM employee WHERE post=#{post} order by rand() LIMIT 1")
    String randNextDeal(String post);

    // 随机抽取一名某部门的职员
    @Select("SELECT sn FROM employee WHERE department_sn=#{department_sn} and post=#{post} order by rand() LIMIT 1")
    String randEmployee(String department_sn, String post);
}
