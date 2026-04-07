package com.university.repository;
import com.university.model.Section;

public class SectionRepository extends InMemoryRepository<Section> {

    @Override
    protected String getId(Section item) {
        return item.getSectionId(); 
    }
}