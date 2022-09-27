package net.junhabaek.tddpractice.base;


import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import net.junhabaek.tddpractice.utils.jpa.ResetTableTool;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    @LocalServerPort
    int port;

    @Autowired
    private ResetTableTool resetTableTool;

    @Autowired
    WebApplicationContext applicationContext;

    protected abstract List<Class> getEntityClasses();

    // TODO execute 중 annotation으로부터 table, id 추출하는 로직을 BeforeALL로 옮기기
    @BeforeEach
    public void setUp(){
        RestAssuredMockMvc.webAppContextSetup(applicationContext);
        RestAssured.port = port;
        resetTableTool.execute(this.getEntityClasses());
    }
}
