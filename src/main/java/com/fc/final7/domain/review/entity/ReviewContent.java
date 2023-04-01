package com.fc.final7.domain.review.entity;

import com.fc.final7.global.entity.ContentType;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Table(name = "review_content")
@Builder
public class ReviewContent {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_content_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "priority")
    private Long priority;

    @Column(name = "type", columnDefinition = "VARCHAR(20)")
    @Enumerated(STRING)
    private ContentType contentType;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
}
