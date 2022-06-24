package net.junhabaek.tddpractice;


import io.restassured.module.mockmvc.RestAssuredMockMvc;
import net.junhabaek.tddpractice.utils.ResetTableTool;
import org.junit.jupiter.api.BeforeAll;
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

    protected abstract List<String> getTableNames();

    @BeforeEach
    public void setUp(){
        RestAssuredMockMvc.webAppContextSetup(applicationContext);
        resetTableTool.execute(this.getTableNames());
    }
}
