package com.dmdev.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * Некоторые классы c чисто статическими функциями не предназначены для инициализации.
 * Один из способов предотвратить создание экземпляра — объявить приватный конструктор,
 * который выбрасывает исключение. Lombok кодифицировал этот шаблон в аннотации @UtilityClass.
 * Она генерирует приватный конструктор, который создаёт исключение, окончательно выводит класс
 * и делает все методы статическими.
 */

@UtilityClass
public class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";

    static {
        loadDriver();
    }

    @SneakyThrows
    private static void loadDriver() {
        Class.forName(PropertiesUtil.get(DRIVER_KEY));
    }

    @SneakyThrows
    public static Connection get() {
        return DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USER_KEY),
                PropertiesUtil.get(PASSWORD_KEY));
    }
}
