package com.github.peacetrue.dictionary;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author peace
 **/
public class H2Test {

    @SneakyThrows
    @AfterAll
    static void afterAll() {
        // 测试完成后，删除 h2 的数据存储文件
        Files.deleteIfExists(Paths.get("test.mv.db"));
    }

    /** 内存数据库是基于连接的，连接不关闭一直在，关闭连接数据库消失 */
    @SneakyThrows
    @Test
    void mem() {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test");
        Statement statement = connection.createStatement();
        statement.execute("select count(1) from INFORMATION_SCHEMA.SCHEMATA");
        ResultSet resultSet = statement.getResultSet();
        Assertions.assertTrue(resultSet.next());
        Assertions.assertEquals(2, resultSet.getLong(1), "存在 2 个库：INFORMATION_SCHEMA/PUBLIC");
        resultSet.close();
        statement.close();

        statement = connection.createStatement();
        statement.execute("create schema test");
        statement.close();

        statement = connection.createStatement();
        statement.execute("select count(1) from INFORMATION_SCHEMA.SCHEMATA");
        resultSet = statement.getResultSet();
        Assertions.assertTrue(resultSet.next());
        Assertions.assertEquals(3, resultSet.getLong(1), "存在 3 个库");
        resultSet.close();
        statement.close();
        // connection.close(); 注意这里不关闭连接

        Connection connection2 = DriverManager.getConnection("jdbc:h2:mem:test");
        statement = connection2.createStatement();
        statement.execute("select count(1) from INFORMATION_SCHEMA.SCHEMATA");
        resultSet = statement.getResultSet();
        Assertions.assertTrue(resultSet.next());
        // 查询到了 connection 创建的库
        Assertions.assertEquals(3, resultSet.getLong(1), "存在 3 个库");
        resultSet.close();
        statement.close();
        connection2.close();
        connection.close(); // 注意这里关闭之前未关闭的连接

        Connection connection3 = DriverManager.getConnection("jdbc:h2:mem:test");
        statement = connection3.createStatement();
        statement.execute("select count(1) from INFORMATION_SCHEMA.SCHEMATA");
        resultSet = statement.getResultSet();
        Assertions.assertTrue(resultSet.next());
        Assertions.assertEquals(2, resultSet.getLong(1), "存在 2 个库");
        resultSet.close();
        statement.close();
        connection3.close();
    }

    //    @Test
    @SneakyThrows
    void tables() {
        Connection connection = DriverManager.getConnection("jdbc:h2:file:./test2;DB_CLOSE_ON_EXIT=FALSE;MODE=MySQL;DATABASE_TO_LOWER=TRUE");
        System.out.println(tables(connection));
        connection = DriverManager.getConnection("jdbc:h2:file:./test2");
        System.out.println(tables(connection));
    }

    @SneakyThrows
    static String tables(Connection connection) {
        StringBuilder buffer = new StringBuilder();
        Statement statement = connection.createStatement();
        statement = connection.createStatement();
        // SCHEMATA
        statement.execute("SELECT * FROM INFORMATION_SCHEMA.TABLES");
        ResultSet resultSet = statement.getResultSet();
        int columnCount = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                buffer.append(resultSet.getObject(i)).append("|");
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }
}
