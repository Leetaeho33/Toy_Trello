package com.example.toy_trello.domain.column.controller;

import com.example.toy_trello.domain.column.dto.requestDto.ColumnRequestDto;
import com.example.toy_trello.domain.column.dto.responseDto.ColumnResponseDto;
import com.example.toy_trello.domain.column.entity.Column;
import com.example.toy_trello.domain.column.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/columns")
public class ColumnController {

    private final ColumnService columnService;

    @Autowired
    public ColumnController(ColumnService columnService) {
        this.columnService = columnService;
    }

    @GetMapping
    public ResponseEntity<List<ColumnResponseDto>> getAllColumns() {
        List<Column> columns = columnService.getAllColumns();
        List<ColumnResponseDto> responseDtos = columns.stream()
                .map(column -> new ColumnResponseDto(column.getId(), column.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColumnResponseDto> getColumnById(@PathVariable Long id) {
        Column column = columnService.getColumnById(id);
        if (column != null) {
            return new ResponseEntity<>(new ColumnResponseDto(column.getId(), column.getName()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ColumnResponseDto> createColumn(@RequestBody ColumnRequestDto requestDto) {
        Column column = new Column(requestDto.getName());
        Column createdColumn = columnService.createColumn(column);
        return new ResponseEntity<>(new ColumnResponseDto(createdColumn.getId(), createdColumn.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColumnResponseDto> updateColumn(@PathVariable Long id, @RequestBody ColumnRequestDto requestDto) {
        Column updatedColumn = columnService.updateColumn(id, new Column(requestDto.getName()));
        if (updatedColumn != null) {
            return new ResponseEntity<>(new ColumnResponseDto(updatedColumn.getId(), updatedColumn.getName()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long id) {
        columnService.deleteColumn(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<ColumnResponseDto> createColumn(@PathVariable Long boardId, @RequestBody ColumnRequestDto requestDto) {
        Column column = new Column(requestDto.getName());
        Column createdColumn = columnService.createColumn(boardId, column);
        return new ResponseEntity<>(new ColumnResponseDto(createdColumn.getId(), createdColumn.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update-order")
    public ResponseEntity<Void> updateColumnOrder(@PathVariable Long id, @RequestParam Long newPosition) {
        columnService.updateColumnOrder(id, newPosition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    // Card 조회 API 추가
//    @GetMapping("/{id}/cards")
//    public ResponseEntity<List<CardResponseDto>> getCardsByColumnId(@PathVariable Long id) {
//        List<Card> cards = columnService.getCardsByColumnId(id);
//        List<CardResponseDto> responseDtos = cards.stream()
//                .map(card -> new CardResponseDto(card.getCardId(), card.getCardName()))
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
//    }


}
