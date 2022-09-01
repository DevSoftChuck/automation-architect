package utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import models.csvData.CheckoutData;
import models.csvData.CsvBean;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Utils {

    public static void pause(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * We can perform the mappings between .csv column headings using the @CsvBindByPosition or the @CsvBindByName
     * annotations, which specify a mapping by position or heading string match, respectively.
     * @return returned List using the CsvToBean.
     * */
    public static List<CsvBean> injectCsvFileToPojo(Path path, Class<? extends CsvBean> clazz) {
        List<CsvBean> csvList;
        try (Reader reader = Files.newBufferedReader(path)) {
            CsvToBean<CsvBean> cb = new CsvToBeanBuilder<CsvBean>(reader)
                    .withType(clazz)
                    .build();

            csvList = cb.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return csvList;
    }

    //TODO
}
