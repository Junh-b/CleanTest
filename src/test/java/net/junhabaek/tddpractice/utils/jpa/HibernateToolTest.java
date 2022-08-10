package net.junhabaek.tddpractice.utils.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

public class HibernateToolTest {

    @Test
    void givenEntityHasFieldWithColumnAnnotation_whenExtractColumnNameFromIt_thenShouldEqualsToExpectedColumnName() {
        //given
        String expectedIdColumnName = "book_id";
        Class<SampleEntityWithOverridingIdColumnName> c = SampleEntityWithOverridingIdColumnName.class;

        //when
        String extractedColumnName = HibernateTool.extractIdNameFromEntityClass(c);

        //then
        Assertions.assertEquals(expectedIdColumnName, extractedColumnName);
    }

    @Test
    void givenEntityHaveFieldWithoutAnnotation_WhenExtractColumNameFromIt_thenShouldEqaulsToExpectedColumnName() {
        //given
        String expectedIdColumnName = "book_id";
        Class<SampleEntityWithoutOverridingColumnName> c = SampleEntityWithoutOverridingColumnName.class;

        //when
        String extractedColumnName = HibernateTool.extractIdNameFromEntityClass(c);

        //then
        Assertions.assertEquals(expectedIdColumnName, extractedColumnName);
    }

    protected static class SampleEntityWithOverridingIdColumnName {
        @Column(name="anything")
        private String someDistractingField;

        @Id
        @Column(name = "book_id")
        private Long id;
    }

    protected static class SampleEntityWithoutOverridingColumnName {
        @Column(name="anything")
        private String someDistractingField;

        @Id
        private Long bookId;
    }

    @Test
    void givenEntityHasTableAnnotation_whenExtractTableNameFromIt_thenShouldEqualsToExpectedTableName() {
        //given
        String expectedTableName = "MyTable";
        Class<SampleEntityWithTableAnnotation> c = SampleEntityWithTableAnnotation.class;

        //when
        String extractedTableName = HibernateTool.extractTableNameFromEntityClass(c);

        //then
        Assertions.assertEquals(expectedTableName, extractedTableName);
    }

    @Test
    void givenEntityDoesntHaveTableAnnotation_whenExtractTableNameFromIt_thenShouldEqualsToExpectedTableName() {
        //given
        String expectedTableName = "SampleEntityWithoutTableAnnotation";
        Class<SampleEntityWithoutTableAnnotation> c = SampleEntityWithoutTableAnnotation.class;

        //when
        String extractedTableName = HibernateTool.extractTableNameFromEntityClass(c);

        //then
        Assertions.assertEquals(expectedTableName, extractedTableName);
    }

    @Table(name = "MyTable")
    protected static class SampleEntityWithTableAnnotation{

    }

    protected static class SampleEntityWithoutTableAnnotation{

    }
}
