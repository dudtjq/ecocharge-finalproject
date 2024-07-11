package com.example.demo.repository;

import com.example.demo.common.ItemWithSequence;
import com.example.demo.common.Page;
import com.example.demo.entity.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface QnaRepositoryCustom {
    
    List<ItemWithSequence> findAll(Page page);

    List<ItemWithSequence> findAllByUser(Page page, User user);

    @Transactional
    List<ItemWithSequence> findAllByAdminRole(Page page);
}
