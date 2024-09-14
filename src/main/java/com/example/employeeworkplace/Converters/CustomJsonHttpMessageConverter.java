package com.example.employeeworkplace.Converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import java.io.IOException;

/**
 * Кастомный конвертер для обработки JSON-сообщений с использованием Jackson.
 * Позволяет сериализовать и десериализовать объекты из HTTP-запросов/ответов в формате JSON.
 */
public class CustomJsonHttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    private static final Logger log = LoggerFactory.getLogger(CustomJsonHttpMessageConverter.class);

    /**
     * Инициализирует конвертер с ObjectMapper и MediaType.APPLICATION_JSON.
     */
    public CustomJsonHttpMessageConverter() {
        super(new ObjectMapper(), MediaType.APPLICATION_JSON);
        log.debug("CustomJsonHttpMessageConverter инициализирован с MediaType.APPLICATION_JSON");
    }

    /**
     * Сериализует объект в HTTP-ответ в формате JSON.
     *
     * @param object объект, который будет сериализован
     * @param outputMessage HTTP-ответ, в который будет записан объект
     * @throws IOException в случае ошибки при записи данных
     * @throws HttpMessageNotWritableException если объект не может быть записан
     */
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        log.info("Запись объекта {} в HTTP-ответ", object.getClass().getSimpleName());
        super.writeInternal(object, outputMessage);
        log.debug("Объект {} успешно записан в HTTP-ответ", object.getClass().getSimpleName());
    }

    /**
     * Десериализует объект из HTTP-запроса в формате JSON.
     *
     * @param clazz класс объекта, который нужно десериализовать
     * @param inputMessage HTTP-запрос, содержащий данные
     * @return десериализованный объект
     * @throws IOException в случае ошибки при чтении данных
     * @throws HttpMessageNotReadableException если объект не может быть прочитан
     */
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        log.info("Чтение объекта {} из HTTP-запроса", clazz.getSimpleName());
        Object result = super.readInternal(clazz, inputMessage);
        log.debug("Объект {} успешно прочитан из HTTP-запроса", clazz.getSimpleName());
        return result;
    }
}
