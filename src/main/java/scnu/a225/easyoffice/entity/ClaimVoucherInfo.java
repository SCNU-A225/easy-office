package scnu.a225.easyoffice.entity;

import java.util.List;

/**
 * @ClassName ClaimVoucherInfo
 * @Description 对应前端数据，是一个pojo 类
 * @Author Lin
 * @Date 2020/7/25 18:37
 * @Version 1.0
 */
public class ClaimVoucherInfo {
    private ClaimVoucher claimVoucher;
    private List<ClaimVoucherItem> items;

    public ClaimVoucher getClaimVoucher() {
        return claimVoucher;
    }

    public void setClaimVoucher(ClaimVoucher claimVoucher) {
        this.claimVoucher = claimVoucher;
    }

    public List<ClaimVoucherItem> getItems() {
        return items;
    }

    public void setItems(List<ClaimVoucherItem> items) {
        this.items = items;
    }
}
