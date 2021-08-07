package app.data.appdata.repository;

import app.data.appdata.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
@Repository
public class MessageJdbcRepository {

    @Autowired
    private DataSource dataSource;

    String sql = " insert into text_message (message) values (?)";

    LongAdder longAdder = new LongAdder();

    public void saveAll_(List<Message> messages) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            for (int i=0; i< messages.size(); i++) {
                pst.setString(1, messages.get(i).getMessage());
                pst.executeUpdate();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception ex) {
            log.error("error occurred while inserting into DB", ex);
        }
    }

    public void saveAll(List<Message> messages) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            int batchSize = 1000;
            for (int i = 0; i< messages.size(); i++) {
                pst.setString(1, messages.get(i).getMessage());
                pst.addBatch();
                longAdder.increment();
                if(longAdder.intValue() % batchSize == 0) {
                    long l = longAdder.longValue()/ batchSize;
                    runBatch(pst, l);
                }
            }
            long l = longAdder.longValue()/ batchSize;
            runBatch(pst, l);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception ex) {
            log.error("error occurred while inserting into DB", ex);
        }
    }

    private void runBatch(PreparedStatement pst, long batch) throws SQLException {
        log.info("executing batch {}", batch);
        pst.executeBatch();
        log.info("executed batch {}", batch);
    }
}
