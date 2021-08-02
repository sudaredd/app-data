package app.data.appdata.controller;

import app.data.appdata.entity.Message;
import app.data.appdata.model.MessageModel;
import app.data.appdata.service.MessageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

//    @RequestMapping(path="/data/message/SaveAll", method = RequestMethod.POST)
    @SneakyThrows
    @PostMapping("/data/message/SaveAll")
    public void saveAll(@RequestBody MessageModel message) {

        int iterations = 100;
        CountDownLatch countDownLatch = new CountDownLatch(iterations);
        log.info("send all messages");
        LocalDateTime now = LocalDateTime.now();

        for(int i = 0; i< iterations; i++)
            executorService.submit(()->submitTasks(message, countDownLatch));

        countDownLatch.await();
        log.info("sent all messages by "+ Duration.between(now, LocalDateTime.now()).toMillis());
    }

    private void submitTasks(MessageModel message, CountDownLatch countDownLatch) {
        List<Message> messages = IntStream.rangeClosed(1, message.getCount())
            .mapToObj(i -> message.getMessage() + " by" + Thread.currentThread().getName() + " "+i)
            .map(Message::new)
            .collect(Collectors.toList());
        messageService.saveAll(messages);
        countDownLatch.countDown();
    }
}
