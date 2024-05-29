package com.mycompany.interviewtask.service.batch;


import lombok.Data;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.core.io.ClassPathResource;

@Data
public class JsonReaderResource<T> implements AutoCloseable {

    private String fullFilePath;

    private final Class<T> type;

    private JsonItemReader<T> jsonItemReader;

    public JsonReaderResource(String fullFilePath, Class<T> type) {
        this.fullFilePath = fullFilePath;
        this.type = type;
        this.jsonItemReader = new JsonItemReaderBuilder<T>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(type))
                .resource(new ClassPathResource(fullFilePath))
                .name(type + "ItemReader")
                .build();
    }

    @Override
    public void close() {
        jsonItemReader.close();
    }
}
