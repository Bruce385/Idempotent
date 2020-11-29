package com.pay.common.controller;

import com.pay.common.annotation.Idempotent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: IdempotentController
 * @Description:
 * @author: Bruce cyc
 * @date: 2020/11/29 23:26
 * @Copyright:
 */
@RestController
public class IdempotentController {
    private String name;

    @GetMapping("/go")
    @Idempotent(key = "IndexRecordServiceImplKey", expirMillis = 100)
    public String go(@RequestParam String name, @RequestParam int age) {
        return "IDEA class is ok when running";
    }
}
