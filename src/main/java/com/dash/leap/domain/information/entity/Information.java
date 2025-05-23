package com.dash.leap.domain.information.entity;

import com.dash.leap.global.domain.BaseEntity;
import com.dash.leap.domain.information.entity.enums.InfoType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Information extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private InfoType infoType;

    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 2083)
    private String url;

    public void update(InfoType infoType, String title, String content, String url) {
        this.infoType = infoType;
        this.title = title;
        this.content = content;
        this.url = url;
    }
}
