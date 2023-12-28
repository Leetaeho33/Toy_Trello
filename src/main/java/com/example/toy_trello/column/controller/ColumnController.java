package com.example.toy_trello.column.controller;

import com.example.toy_trello.column.dto.requestDto.ColumnRequestDto;
import com.example.toy_trello.column.dto.responseDto.ColumnResponseDto;
import com.example.toy_trello.column.entity.Column;
import com.example.toy_trello.column.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/columns")
public class ColumnController {

    @Autowired
    private ColumnService columnService;

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
}