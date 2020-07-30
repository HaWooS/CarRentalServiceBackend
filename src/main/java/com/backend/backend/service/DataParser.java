package com.backend.backend.service;

import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DataParser {

    private LocalDateTime parsedStartDate;
    private LocalDateTime parsedEndDate;
    private String beforeConversionStartDate;
    private String beforeConversionEndDate;
    private LocalDateTime parsedDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //private String singleDateFormatInput = "yyyy-MM-dd'T'HH:mm:ss";
    private String singleDateFormat = "yyyy-MM-dd HH:mm:ss";


    public DataParser(String beforeConversionStartDate, String beforeConversionEndDate) {
        this.beforeConversionStartDate = beforeConversionStartDate;
        this.beforeConversionEndDate = beforeConversionEndDate;
    }

    public DataParser(){};

    public LocalDateTime parseSingleDateFromString(String beforeConversionStartDate){
       // SimpleDateFormat formatInput = new SimpleDateFormat(singleDateFormatInput);
       // SimpleDateFormat formatOutput = new SimpleDateFormat(singleDateFormatOutput);
        String dateToConvert = beforeConversionStartDate.replace('T',' ');
        System.out.println("Z requesta "+dateToConvert);
        parsedDate = LocalDateTime.parse(dateToConvert,formatter);
        System.out.println("po pARSIE "+parsedDate);
        return parsedDate;
    }

    public void convertRequestDates(String beforeConversionStartDate, String beforeConversionEndDate)
    {
        this.parsedStartDate = LocalDateTime.parse(beforeConversionStartDate+" 00:00:59",formatter);
        this.parsedEndDate = LocalDateTime.parse(beforeConversionEndDate+" 23:59:59",formatter);
    }


    public LocalDateTime getParsedStartDate() {
        return parsedStartDate;
    }

    public void setParsedStartDate(LocalDateTime parsedStartDate) {
        this.parsedStartDate = parsedStartDate;
    }

    public LocalDateTime getParsedEndDate() {
        return parsedEndDate;
    }

    public void setParsedEndDate(LocalDateTime parsedEndDate) {
        this.parsedEndDate = parsedEndDate;
    }

    public String getBeforeConversionStartDate() {
        return beforeConversionStartDate;
    }

    public void setBeforeConversionStartDate(String beforeConversionStartDate) {
        this.beforeConversionStartDate = beforeConversionStartDate;
    }

    public String getBeforeConversionEndDate() {
        return beforeConversionEndDate;
    }

    public void setBeforeConversionEndDate(String beforeConversionEndDate) {
        this.beforeConversionEndDate = beforeConversionEndDate;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }
}
