package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @EqualsAndHashCode
@ToString @NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_board")
public class Board {

    @Id
    @Column(name = "board_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNo;

    @Column(name = "b_address")
    private String bAddress;

    @Column(name = "b_writer")
    private String bWriter;

    @Column(name = "b_title", nullable = false)
  //  @NotNull
    private String bTitle;

    @Column(name = "b_content", nullable = false)
 //   @NotNull
    private String bContent;

    // 혹시 이미지를 업로드 할수도 있으니
    @Column(name = "b_profileImage")
    private String bProfileImage;

    @CreationTimestamp
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<BoardReply> boardReplyList;


}
