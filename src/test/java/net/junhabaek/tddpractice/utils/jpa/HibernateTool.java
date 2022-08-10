package net.junhabaek.tddpractice.utils.jpa;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class HibernateTool {
    public static String extractIdNameFromEntityClass(Class entityClass) {
        List<Field> fields = List.of(entityClass.getDeclaredFields());

        Field idField = null;

        for (Field field
                :fields) {
            Annotation columnAnnotation = field.getAnnotation(Id.class);
            if(columnAnnotation != null){
                idField = field;
                break;
            }
        }

        Column columnAnnotation = idField.getAnnotation(Column.class);

        String extractedColumnName = null;
        if(columnAnnotation != null && columnAnnotation.name()!= null){
            extractedColumnName = columnAnnotation.name();
        }
        else{
            String fieldName = idField.getName();
            extractedColumnName = fieldName.replaceAll("([A-Z][a-z])", "_$1").toLowerCase();
        }
        return extractedColumnName;
    }

    public static String extractTableNameFromEntityClass(Class entityClass) {
        String tableName = null;
        Annotation tableAnnotation = entityClass.getAnnotation(Table.class);
        if(tableAnnotation != null && ((Table)tableAnnotation).name()!= null){
            tableName = ((Table)tableAnnotation).name();
        }
        else{
            tableName = entityClass.getName().split("\\$")[1];
        }
        return tableName;
    }
}
