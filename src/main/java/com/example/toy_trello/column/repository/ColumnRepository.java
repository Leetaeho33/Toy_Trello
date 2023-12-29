package com.example.toy_trello.column.repository;

import com.example.toy_trello.column.entity.Column;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<Column, Long> {
    // 추가적인 쿼리 메서드가 필요시 작성
}
