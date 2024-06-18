package com.example.demo.qnaapi.entity;

import com.example.demo.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;


@Getter @Setter
@EqualsAndHashCode @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_QnA")
public class Qna {

    @Id
    @NotNull
    @Column(name = "qna_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaNo;

    @Column(name = "q_title")
    @NotNull
    private String qTitle;

    @Column(name = "q_content")
    @NotNull
    private String qContent;

    @Column(name = "q_answer")
    @Null
    private String qAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
