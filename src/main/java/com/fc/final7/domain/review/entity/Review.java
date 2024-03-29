package com.fc.final7.domain.review.entity;

import com.fc.final7.domain.comment.entity.Comment;
import com.fc.final7.domain.product.entity.Product;
import com.fc.final7.domain.reservation.entity.Reservation;
import com.fc.final7.global.entity.Auditing;
import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.fc.final7.domain.review.entity.Posting.POSTING;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Review extends Auditing {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Builder.Default
    @OneToMany(mappedBy = "review")
    private List<ReviewContent> reviewContents = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "review")
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "title", columnDefinition = "VARCHAR(40)")
    private String title;

    @Column(name = "grade", columnDefinition = "DECIMAL(2,1)")
    private BigDecimal grade;

    @Column(name = "thumbnail", columnDefinition = "TEXT")
    private String thumbnail;

    @Column(name = "name", columnDefinition = "VARCHAR(20)")
    private String name;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;

    @Builder.Default
    @Column(name = "status", columnDefinition = "VARCHAR(10) DEFAULT 'POSTING'")
    @Enumerated(STRING)
    private Posting posting = POSTING;

}
