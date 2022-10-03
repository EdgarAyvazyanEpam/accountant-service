package com.accountant.service.accountant.csv.helper;

import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.enums.IsoCodeEnum;
import com.accountant.service.accountant.exception.currency.CSVCurrencyFileParseException;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVCurrencyHelper {
    private static final Logger logger = LoggerFactory.getLogger(CSVCurrencyHelper.class);
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }


    public static List<CurrencyDTO> csvToCurrencies(InputStream is, String fileName, Long uploadedFileId) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<CurrencyDTO> currencyDTOS = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                CurrencyDTO currency = new CurrencyDTO(null,
                        LocalDateTime.parse(convertCurrencyDateToDBDate(csvRecord.get("Date"))),
                        convertCurrencyDateToDBDay(csvRecord.get("Date")),
                        csvRecord.get("Rate"),
                        IsoCodeEnum.valueOf(csvRecord.get("ISO Code From")),
                        IsoCodeEnum.valueOf(csvRecord.get("ISO Code To")),
                        LocalDateTime.now(), fileName, String.valueOf(uploadedFileId)
                );

                currencyDTOS.add(currency);
            }

            return currencyDTOS;
        } catch (IOException e) {
            String message = "Fail to parse CSV file";
            logger.error(message, e);
            throw new CSVCurrencyFileParseException(message);
        } catch (IllegalArgumentException e) {
            logger.info("Failed to parse .csv file: incorrect arguments are passes");
            throw new IllegalArgumentException();
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            throw new CSVCurrencyFileParseException(e.getMessage());
        }
    }

    private static String convertCurrencyDateToDBDate(String currencyDate) {
        String[] s = currencyDate.split(" ");
        String[] splitDate = s[0].split("/");
        int month = Integer.parseInt(splitDate[0]);
        int day = Integer.parseInt(splitDate[1]);
        int year = Integer.parseInt(splitDate[2]);
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 0, 0);
        return localDateTime.toString();
    }

    private static String convertCurrencyDateToDBDay(String currencyDate) {
        String[] s = currencyDate.split(" ");
        return s[1];
    }
}
