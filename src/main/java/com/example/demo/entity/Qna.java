package com.example.demo.entity;

import com.example.demo.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Getter @Setter
@EqualsAndHashCode @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_QnA")
public class Qna {

    @Id
    @Column(name = "qna_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaNo;

    @Column(name = "q_title", nullable = false)
    @NotNull
    private String qTitle;

    @Column(name = "q_content", nullable = false)
    @NotNull
    private String qContent;

    @Column(name = "q_category", nullable = false)
    @NotBlank
    private String qCategory;

    @CreationTimestamp
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "qna")
    private List<QnaReply> qnaReplyList;


}
