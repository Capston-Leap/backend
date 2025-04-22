package com.dash.leap.domain.chat.repository;

import com.dash.leap.domain.chat.entity.Chat;
import com.dash.leap.domain.chat.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByUserId(Long userId);

    @Query("select m from Message m " +
            "where m.chat.id = :chatId " +
            "order by m.id desc ")
    Slice<Message> findByChatIdAndLessThanMessageId(
            @Param("chatId") Long chatId,
            Pageable pageable
    );
}
