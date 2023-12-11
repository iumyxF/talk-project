package com.example.talk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author iumyxF
 * @description: knife4j测试接口
 * @date 2023/12/11 11:15
 */
@Tag(name = "knife4j测试接口")
@RestController
@RequestMapping
public class Knife4jControllerTest {

    @Operation(summary = "普通请求+Param+Header+Path")
    @Parameters({
            @Parameter(name = "id", description = "文件id", in = ParameterIn.PATH),
            @Parameter(name = "token", description = "请求token", required = true, in = ParameterIn.HEADER),
            @Parameter(name = "name", description = "文件名称", required = true, in = ParameterIn.QUERY)
    })
    @PostMapping("/knife4j/test/{id}")
    public ResponseEntity<String> bodyParamHeaderPath(@PathVariable("id") String id,
                                                      @RequestHeader("token") String token,
                                                      @RequestParam("name") String name) {
        System.out.println(String.format("id = %s , token = %s , name = %s", id, token, name));
        return ResponseEntity.ok("ok");
    }
}
