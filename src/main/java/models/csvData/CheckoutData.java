package models.csvData;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutData extends CsvBean{
    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName
    private String lastname;

    @CsvBindByName
    private String postalCode;
}
