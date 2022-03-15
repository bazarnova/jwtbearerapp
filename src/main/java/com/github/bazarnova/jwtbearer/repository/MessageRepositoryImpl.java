package com.github.bazarnova.jwtbearer.repository;

import com.github.bazarnova.jwtbearer.model.Message;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class MessageRepositoryImpl extends SimpleJpaRepository<Message, Long> implements MessageRepository {

    private EntityManager entityManager;

    public MessageRepositoryImpl(EntityManager em) {
        super(Message.class, em);
        this.entityManager = em;
    }

    @Override
    public List<Message> findHistorySizeForUser(Integer count, String name) {

        List<Message> resultList = entityManager.createQuery(
                        "SELECT m " +
                                "FROM Message m " +
                                "join m.user u where u.name = ?1 " +
                                "group by m.id " +
                                "ORDER BY m.id DESC", Message.class)
                .setParameter(1, name)
                .setMaxResults(count)
                .getResultList();

        return resultList;

    }
}
