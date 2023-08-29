package com.orkes.assignment.spreadsheet.sheet.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.orkes.assignment.spreadsheet.sheet.entities.Cell;

@Repository
public final class Sheet implements SheetRepository<Cell> {

    private Map<String, Cell> map;

    private Sheet() {
        map = new HashMap<>();
    }

    @Override
    public void save(String cellId, Cell cell) {
        map.put(cellId, cell);
    }

    @Override
    public Cell findById(String id) {
        return map.getOrDefault(id, null);
    }
}
