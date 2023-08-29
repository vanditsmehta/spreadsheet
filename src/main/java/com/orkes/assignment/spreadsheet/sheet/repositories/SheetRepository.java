package com.orkes.assignment.spreadsheet.sheet.repositories;

public interface SheetRepository<T> {

    public void save(String id, T t);

    public T findById(String id);
}
