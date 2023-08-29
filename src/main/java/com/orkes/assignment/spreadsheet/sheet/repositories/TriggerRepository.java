package com.orkes.assignment.spreadsheet.sheet.repositories;

import java.util.Set;

import javax.management.InvalidAttributeValueException;

public interface TriggerRepository<T> {

    void save(Set<T> fromCellIds, T toCellId)
            throws InvalidAttributeValueException;

    void add(T fromCellId, T toCellId) throws InvalidAttributeValueException;

    void remove(T fromCellId, T toCellId);

    void remove(Set<T> fromCellIds, T toCellId);

    Set<T> findById(T cellId);

}