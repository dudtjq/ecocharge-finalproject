package com.example.demo.common;

import com.example.demo.entity.Board;
import com.example.demo.entity.Qna;
import com.example.demo.entity.SubsidyCar;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ItemWithSequence {
    private int sequence;
    private Board board;
    private Qna qna;
    
    
    public ItemWithSequence(int sequence, Board item) {
        this.sequence = sequence;
        this.board = item;
    }
    
    public ItemWithSequence(int sequence, Qna item) {
        this.sequence = sequence;
        this.qna = item;
    }
    
    // getters and setters
}
