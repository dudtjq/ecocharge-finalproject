package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @ToString(exclude = "board")
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor @Builder
@Entity
@Table(name = "tbl_board_reply")
public class BoardReply {

    @Id
    @Column(name = "b_reply_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyNo;

    @Column(name = "b_reply_writer")
    private String replyWriter;

    @Column(name = "b_reply_text")
    private String replyText;

    @CreationTimestamp
    private LocalDateTime replyDate;

    @CreationTimestamp
    private LocalDateTime updateReplyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no")
    private Board board;


    @Column(name = "user_id")
    private String userId;









}
