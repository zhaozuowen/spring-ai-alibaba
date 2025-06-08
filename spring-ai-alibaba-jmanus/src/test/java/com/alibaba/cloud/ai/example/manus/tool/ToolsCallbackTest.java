package com.alibaba.cloud.ai.example.manus.tool;

import com.alibaba.cloud.ai.example.manus.OpenManusSpringBootApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;

@SpringBootTest(classes = OpenManusSpringBootApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled("仅用于本地测试，CI 环境跳过")
public class ToolsCallbackTest{
    private static final Logger log = LoggerFactory.getLogger(ToolsCallbackTest.class);

    @Autowired
    private ChatModel chatModel;

    @Test
    public void testToolsCallback() {
        String content = ChatClient.create(chatModel).prompt("what day is now?")
                .tools(new DateTimeTools())
                .call()
                .content();

        log.info("content:{}",content);

    }

    private class DateTimeTools {

        @Tool(description = "Get the current date and time in the user's timezone",returnDirect = true)
        public String getCurrentDateTime() {
            return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
        }
    }
}
