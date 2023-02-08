package com.xiaojukeji.know.streaming.test.container.mysql;

import com.xiaojukeji.know.streaming.test.container.BaseTestContainer;
import org.jetbrains.annotations.NotNull;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Supplier;

public class MySQLTestContainer extends BaseTestContainer {
    private static final String DB_PROPERTY = "?useUnicode=true" +
            "&characterEncoding=utf8" +
            "&jdbcCompliantTruncation=true" +
            "&allowMultiQueries=true" +
            "&useSSL=false" +
            "&alwaysAutoGeneratedKeys=true" +
            "&serverTimezone=GMT%2B8" +
            "&allowPublicKeyRetrieval=true";

    private static final KSMySQLContainer<?> MYSQL_CONTAINER = new KSMySQLContainer<>(
            DockerImageName.parse("knowstreaming/knowstreaming-mysql:latest").asCompatibleSubstituteFor("mysql")
    )
            .withEnv("MYSQL_ROOT_HOST", "%")
            .withEnv("TZ", "Asia/Shanghai")
            .withDatabaseName("know_streaming")
            .withUsername("root")
            .withPassword("mysql_pass");

    @NotNull
    public Supplier<Object> jdbcUrl() {
        return () -> "jdbc:mariadb://"
                + MYSQL_CONTAINER.getHost() + ":" + MYSQL_CONTAINER.getMappedPort(3306)
                + "/know_streaming" + DB_PROPERTY;
    }

    @Override
    public void init() {
        Startables.deepStart(MYSQL_CONTAINER).join();
    }

    @Override
    public void cleanup() {
    }
}
