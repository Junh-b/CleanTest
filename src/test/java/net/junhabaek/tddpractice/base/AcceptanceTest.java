package net.junhabaek.tddpractice.base;


import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import net.junhabaek.tddpractice.utils.jpa.ResetTableTool;
import net.junhabaek.tddpractice.utils.jpa.TableIdPair;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static net.junhabaek.tddpractice.utils.jpa.HibernateTool.extractIdNameFromEntityClass;
import static net.junhabaek.tddpractice.utils.jpa.HibernateTool.extractTableNameFromEntityClass;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    @LocalServerPort
    int port;
    @Autowired
    private ResetTableTool resetTableTool;
    @Autowired
    WebApplicationContext applicationContext;
    List<TableIdPair> tableIdPairs = null;

    /**
     * @return entityClasses which have @Entity annotation. <br>
     * not works with hibernate/jpa xml mapping file. <br><br>
     * you should be careful if entity have inheritance strategy. <br>
     * - Joined Table: you should include both superclass and subclass. <br>
     * - Single Table: you should include only superclass. <br>
     * - Table per Class: you should include only subclasses.
     *
     */
    protected abstract List<Class> getEntityClasses();

    @BeforeEach
    public void setUpBeforeEachTest(){
        setUpRestAssured();
        resetTable();
    }

    private void setUpRestAssured(){
        RestAssuredMockMvc.webAppContextSetup(applicationContext);
        RestAssured.port = port;
    }

    private void resetTable(){
        if(tableIdPairs == null){
            tableIdPairs = extractTableIdPairsFromEntityClasses(getEntityClasses());
        }
        resetTableTool.execute(tableIdPairs);
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
