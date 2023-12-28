package com.example.toy_trello.column.service;

import com.example.toy_trello.column.entity.Column;

import java.util.List;

public interface ColumnService {
    List<Column> getAllColumns();
    Column getColumnById(Long id);
    Column createColumn(Column column);
    Column updateColumn(Long id, Column column);
    void deleteColumn(Long id);
}