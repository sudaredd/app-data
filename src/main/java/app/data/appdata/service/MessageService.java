package app.data.appdata.service;

import app.data.appdata.entity.Message;
import app.data.appdata.repository.MessageHibernateRepository;
import app.data.appdata.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
public class MessageService {


    @Autowired
    private MessageHibernateRepository messageHibernateRepository;

//    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void saveAll(List<Message> messages) {
        messageHibernateRepository.saveAll(messages);
    }

    @Service
    private static class MessageInnerService implements CommandLineRunner {

        @Autowired
        MessageService messageService;

        @Autowired
        private MessageRepository messageRepository;

        @Override
        public void run(String... args) throws Exception {
//        messageRepository.saveAll(List.of(new Message("one"), new Message("two"), new Message("three")));
            List<Message> messages = IntStream.rangeClosed(101, 200)
                .mapToObj(i -> "message " + i)
                .map(Message::new)
                .collect(Collectors.toList());
//            messageService.saveAll(messages);

            Iterable<Message> all = messageRepository.findAll();

            // Iterator -> Spliterators -> Stream -> List
            List<Message> result = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(all.iterator(), Spliterator.ORDERED), false)
                .collect(Collectors.toList());

            result.forEach(x -> System.out.println("Message:::" + x));
            System.out.println(result.getClass());
        }
    }

}
