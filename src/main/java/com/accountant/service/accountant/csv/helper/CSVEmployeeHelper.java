package com.accountant.service.accountant.csv.helper;

import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.exception.employee.CSVEmployeeFileParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVEmployeeHelper {
    private static final Logger logger = LoggerFactory.getLogger(CSVEmployeeHelper.class);
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }


    public static List<EmployeeDTO> csvToEmployees(InputStream is, String fileName, Long uploadedFileId) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<EmployeeDTO> employeeDTOS = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                EmployeeDTO dto = new EmployeeDTO(null,
                        csvRecord.get("Employee"),
                        BigDecimal.valueOf(Long.parseLong(csvRecord.get("Salary"))),
                        LocalDateTime.now(), fileName, String.valueOf(uploadedFileId)
                );

                employeeDTOS.add(dto);
            }

            return employeeDTOS;
        } catch (IOException e) {
            String message = "Fail to parse CSV file";
            logger.error(message, e);
            throw new CSVEmployeeFileParseException(message);
        }catch (IllegalArgumentException e) {
            logger.info("Failed to parse .csv file: incorrect arguments are passes");
            throw  new IllegalArgumentException();
        }catch (RuntimeException e) {
            logger.error(e.getMessage());
            throw new CSVEmployeeFileParseException(e.getMessage());
        }
    }
}
