package com.example.employeeworkplace.Utils;

import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Вспомогательный класс для работы с датами.
 *
 * <p>Предоставляет форматирование даты в строку с использованием заданного формата.</p>
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * Форматтер для преобразования даты в строку в формате yyyy-MM-dd.
     */
    public static final DateTimeFormatter DateToString = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    static {
        logger.info("Инициализация DateUtils с форматом даты yyyy-MM-dd");
    }
}
