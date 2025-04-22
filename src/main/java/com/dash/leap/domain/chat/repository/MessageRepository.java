package com.dash.leap.domain.chat.repository;

import com.dash.leap.domain.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m " +
            "where m.chat.id = :chatId")
    List<Message> findByMessagesByChatId(@Param("chatId") Long chatId);
}
