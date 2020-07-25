package scnu.a225.easyoffice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * @Description: 测试API是否正常
 * @Author: jiangjian
 * @CreateDate: 2020/7/25 12:11
 * @UpdateUser: jiangjian
 * @UpdateDate: 2020/7/25 12:11
 * @UpdateRemark: TODO
 * @Version: V1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperation("测试API")//swagger文档注解
    @GetMapping
    public String test() {
        return "ok";
    }
}
