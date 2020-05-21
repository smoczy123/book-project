package com.karankumar.bookproject.ui.shelf;

import com.karankumar.bookproject.backend.model.Shelf;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class ShelfToShelfNameConverter implements Converter<Shelf.ShelfName, Shelf> {
    @Override
    public Result<Shelf> convertToModel(Shelf.ShelfName shelfName, ValueContext valueContext) {
        if (shelfName == null) {
            return Result.error("Please select a shelf");
        }
        return Result.ok(new Shelf(shelfName));
    }

    @Override
    public Shelf.ShelfName convertToPresentation(Shelf shelf, ValueContext valueContext) {
        if (shelf == null) {
            System.out.println("Shelf is null");
            return Shelf.ShelfName.READ; // have to convert it to something
        }
        return shelf.getName();
    }
}
