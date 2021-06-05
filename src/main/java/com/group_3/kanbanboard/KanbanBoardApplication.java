package com.group_3.kanbanboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class KanbanBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanbanBoardApplication.class, args);
    }

}
