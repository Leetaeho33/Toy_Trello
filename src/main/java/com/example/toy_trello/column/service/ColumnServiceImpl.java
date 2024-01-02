package com.example.toy_trello.column.service;

import com.example.toy_trello.board.entity.Board;
import com.example.toy_trello.board.repository.BoardRepository;
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

    @Autowired
    private BoardRepository boardRepository;  // 보드와의 연계를 위해 추가
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
            Column column = existingColumn.get( );
            column.setName(updatedColumn.getName());
            return columnRepository.save(column);
        } else {
            return null; // 예외 처리
        }
    }

    @Override
    public Column createColumn(Long boardId, Column column) {
        Board board = boardRepository.findById(boardId).orElse(null);

        if (board != null) {
            column.setBoard(board);
            return columnRepository.save(column);
        } else {
            throw new IllegalArgumentException("보드를 찾을 수 없습니다.");
        }
    }

    @Override
    public void updateColumnOrder(Long columnId, Long newPosition) {
        Column column = columnRepository.findById(columnId).orElse(null);

        if (column != null) {
            column.setOrder(newPosition);
            columnRepository.save(column);
        } else {
            throw new IllegalArgumentException("컬럼을 찾을 수 없습니다.");
        }
    }



    @Override
    public void deleteColumn(Long id) {
        columnRepository.deleteById(id);
    }
}
