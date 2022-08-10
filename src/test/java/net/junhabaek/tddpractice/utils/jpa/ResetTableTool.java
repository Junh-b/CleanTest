package net.junhabaek.tddpractice.utils.jpa;

import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static net.junhabaek.tddpractice.utils.jpa.HibernateTool.extractIdNameFromEntityClass;
import static net.junhabaek.tddpractice.utils.jpa.HibernateTool.extractTableNameFromEntityClass;

@Component
@ActiveProfiles("test")
public class ResetTableTool {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void execute(List<Class> entityClasses) {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        List<TableIdPair> tableIdPairs = extractTableIdPairsFromEntityClasses(entityClasses);

        for (TableIdPair tableIdPair: tableIdPairs) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableIdPair.tableName).executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE " + tableIdPair.tableName + " ALTER COLUMN "
                    + tableIdPair.idName + " RESTART WITH 1").executeUpdate();
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private List<TableIdPair> extractTableIdPairsFromEntityClasses(List<Class> entityClasses) {
        List<TableIdPair> tableIdPairs = new ArrayList<>();

        for(Class entityClass:
            entityClasses){
            String tableName = extractTableNameFromEntityClass(entityClass);
            String idName = extractIdNameFromEntityClass(entityClass);

            TableIdPair tableIdPair = new TableIdPair(tableName, idName);

            tableIdPairs.add(tableIdPair);
        }

        return tableIdPairs;
    }
}