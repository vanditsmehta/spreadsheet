package com.orkes.assignment.spreadsheet.sheet.repositories;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.orkes.assignment.spreadsheet.sheet.exceptions.CyclicDependencyException;

@Repository
public final class Triggers implements TriggerRepository<String> {

    private Map<String, Set<String>> triggers;

    private Triggers() {
        triggers = new HashMap<>();
    }

    @Override
    public void save(Set<String> fromCellIds, String toCellId)
            throws CyclicDependencyException {
        if (fromCellIds != null) {
            for (String fromCellId : fromCellIds) {
                this.add(fromCellId, toCellId);
            }
        }
    }

    @Override
    public void add(String fromCellId, String toCellId)
            throws CyclicDependencyException {
        Set<String> dependentList = this.triggers.get(fromCellId);
        if (dependentList == null) {
            dependentList = new HashSet<>();
        }
        dependentList.add(toCellId);
        this.triggers.put(fromCellId, dependentList);
        if (this.hasCyclicDependency(fromCellId)) {
            dependentList.remove(toCellId);
            this.triggers.put(fromCellId, dependentList);
            throw new CyclicDependencyException(
                    "Cyclic Dependency because of current expression.");
        }
    }

    @Override
    public void remove(String fromCellId, String toCellId) {
        Set<String> set = this.triggers.get(fromCellId);
        if (set != null) {
            set.remove(toCellId);
            if (set.isEmpty())
                this.triggers.remove(fromCellId);
            else
                this.triggers.put(fromCellId, set);
        }
    }

    @Override
    public void remove(Set<String> fromCellIds, String toCellId) {
        if (fromCellIds == null)
            return;
        for (String fromCellId : fromCellIds)
            this.remove(fromCellId, toCellId);
    }

    @Override
    public Set<String> findById(String cellId) {
        return this.triggers.getOrDefault(cellId, null);
    }

    private boolean hasCyclicDependency(String cellId) {
        Set<String> cells = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(cellId);
        while (!queue.isEmpty()) {
            cellId = queue.poll();
            if (cells.contains(cellId))
                return true;
            else {
                cells.add(cellId);
                Set<String> toCellIds = this.findById(cellId);
                if (toCellIds != null)
                    queue.addAll(toCellIds);
            }
        }
        return false;
    }
}
