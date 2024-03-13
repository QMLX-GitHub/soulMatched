package com.qmlx.usercenter.once;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class InsertUser {
    public static void main(String[] args) {


        List<String> imageUrlList = new ArrayList<>();

        // Read image URLs from a file and add them to the list
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\桌面\\output.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                imageUrlList.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Print all image URLs from the list
        for (String imageUrl : imageUrlList) {
            System.out.println(imageUrl);
        }
    }
}



