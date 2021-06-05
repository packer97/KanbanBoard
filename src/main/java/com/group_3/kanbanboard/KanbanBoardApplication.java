package com.group_3.kanbanboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KanbanBoardApplication {

    public static void main(String[] args) {
        System.out.println(System.getProperties());
        SpringApplication.run(KanbanBoardApplication.class, args);
    }

}
