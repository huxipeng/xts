package com.mogo.xts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xipeng
 **/
@RestController
public class SocketController {

    @GetMapping("/testSocket")
    public void testSocket() throws Exception{

    }
}
