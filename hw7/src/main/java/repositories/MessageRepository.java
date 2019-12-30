package repositories;

import models.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message,Integer> {
    List<Message> findByStartAndEnd(int start, int end);
    void save(Message message);
}
