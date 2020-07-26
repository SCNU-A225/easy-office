package scnu.a225.easyoffice.utils;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName: ControllerUtil
 * @Description: TODO
 * @Author: jiangjian
 * @CreateDate: 2020/7/26 11:38
 * @UpdateUser: jiangjian
 * @UpdateDate: 2020/7/26 11:38
 * @UpdateRemark: TODO
 * @Version: V1.0
 */
public class ControllerUtil {
    public static boolean dataNotNull(JSON json) {
        System.out.println(json.toJSONString());
        return null!=json && !("undifened".equals(json.toJSONString())||"".equals(json.toJSONString()));
    }

    public static boolean stringNotNull(String str) {
        return null!=str && !str.isEmpty();
    }
}
