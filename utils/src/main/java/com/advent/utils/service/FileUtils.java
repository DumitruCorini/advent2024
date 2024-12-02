package com.advent.utils.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

@Service
public class FileUtils {
    public static List<String> readFileAndGetLines(String fileName) {
        Resource file = new ClassPathResource(fileName);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.getFile()));

            return reader.lines().toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
