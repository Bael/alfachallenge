package com.github.bael.alfa.task5.csv;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CsvReader<T extends Transformable> {
    private final Function<String[], T> creatorFunction;

    public CsvReader(Function<String[], T> creatorFunction) {
        this.creatorFunction = creatorFunction;
    }

    public List<T> readCollection(String filename, Consumer<T> consumer) {
        List<T> list = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        try (
//                Reader reader = Files.newBufferedReader(Paths.get(
//                ClassLoader.getSystemResource(filename).toURI()));
             InputStream inputStream = classLoader.getResourceAsStream(filename);
             InputStreamReader reader = new InputStreamReader(inputStream);

             ) {
            CSVReader csvReader = new CSVReader(reader);
            csvReader.skip(1);
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                T record = creatorFunction.apply(nextRecord);
                list.add(record);
                consumer.accept(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
