package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_qna_reply")
public class QnaReply {

    @Id
    @Column(name = "q_reply_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyNo;

    @Column(name = "q_reply_writer")
    private String replyWriter;

    @Column(name = "q_reply_text")
    private String replyText;

    @Column(name = "qna_no")
    private Long qnaNo;

    @CreationTimestamp
    private LocalDateTime replyDate;

    @CreationTimestamp
    private LocalDateTime updateReplyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_nos")
    private Qna qna;
}
