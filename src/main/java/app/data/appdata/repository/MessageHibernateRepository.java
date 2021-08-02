package app.data.appdata.repository;

import app.data.appdata.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Slf4j
public class MessageHibernateRepository extends HibernateDaoSupport {

    @Autowired
    public MessageHibernateRepository(EntityManagerFactory entityManagerFactory){
        super.setSessionFactory(entityManagerFactory.unwrap(SessionFactory.class));
    }

    public void saveAll(List<Message> messages) {
        Session currentSession = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        for(Message message: messages) {
            currentSession.save(message);
        }
        log.info("before flushing");
        currentSession.flush();
        log.info("after flushing");
        transaction.commit();
    }
}
