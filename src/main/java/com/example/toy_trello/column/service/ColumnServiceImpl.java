package com.example.toy_trello.column.service;

import com.example.toy_trello.column.entity.Column;
import com.example.toy_trello.column.repository.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    private ColumnRepository columnRepository;

    @Override
    public List<Column> getAllColumns() {
        return columnRepository.findAll();
    }

    @Override
    public Column getColumnById(Long id) {
        return columnRepository.findById(id).orElse(null);
    }

    @Override
    public Column createColumn(Column column) {
        return columnRepository.save(column);
    }

    @Override
    public Column updateColumn(Long id, Column updatedColumn) {
        Optional<Column> existingColumn = columnRepository.findById(id);

        if (existingColumn.isPresent()) {
            Column column = existingColumn.get();
            column.setName(updatedColumn.getName());
            return columnRepository.save(column);
        } else {
            return null; // 예외 처리
        }
    }

    @Override
    public void deleteColumn(Long id) {
        columnRepository.deleteById(id);
    }
}
