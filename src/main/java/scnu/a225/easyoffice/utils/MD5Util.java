package scnu.a225.easyoffice.utils;

import net.bytebuddy.utility.RandomString;
import org.springframework.util.DigestUtils;

/**
 * @ClassName MD5Util
 * @Description MD5 工具类
 * @Author Lin
 * @Date 2020/7/25 21:01
 * @Version 1.0
 */
public class MD5Util {

    /*
     * @Description 进行MD5加密
     * @Date 2020/7/25 21:10
     * @Param [str]
     * @return java.lang.String
     **/
    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /*
     * @Description 生成 10 位随机 salt
     * @Date 2020/7/25 21:09
     * @Param []
     * @return java.lang.String
     **/
    public static String getSalt() {
        return RandomString.make(10);
    }

    /*
     * @Description 判断密码是否一致
     * @Date 2020/7/25 21:08
     * @Param [password, DbPassWord, salt]
     * @return boolean
     **/
    public static boolean compare(String password, String DbPassWord, String salt) {
        return md5(password + salt).equals(DbPassWord);
    }

}
