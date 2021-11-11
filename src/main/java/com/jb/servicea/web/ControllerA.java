package com.jb.servicea.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ControllerA {
    @Autowired
    RestTemplate client;

    @HystrixCommand(fallbackMethod = "fallback", groupKey = "srvA",commandKey = "srvA", threadPoolKey = "srvAThread")
    @GetMapping("service/a") //route
    public String handleA(){
        return "Service A - calling B:\n"+client.getForObject("http://service-b/service/b", String.class);
    }

    public String fallback(Throwable hystrixCommand){
        return "fall back message";
    }
}
