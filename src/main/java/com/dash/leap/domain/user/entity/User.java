package com.dash.leap.domain.user.entity;

import com.dash.leap.domain.user.entity.enums.ChatbotType;
import com.dash.leap.domain.user.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String loginId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String nickname;

    @NotNull
    private LocalDate birth;

    @CreatedDate
    @Column(name = "registered_at", updatable = false)
    private LocalDateTime registerTime;

    @Column(name = "chatbot")
    @Enumerated(EnumType.STRING)
    private ChatbotType chatbotType;

    private Integer level;

    @Column(name = "class")
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.USER;
}
