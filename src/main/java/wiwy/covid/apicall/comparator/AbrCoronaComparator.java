package wiwy.covid.apicall.comparator;

import org.springframework.context.annotation.Bean;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;

import java.util.Comparator;

public class AbrCoronaComparator implements Comparator<AbrCoronaDto> {

    @Override
    public int compare(AbrCoronaDto o1, AbrCoronaDto o2) {
        int firstValue = o1.getNatDefCnt();
        int secondValue = o2.getNatDefCnt();

        if (firstValue > secondValue) {
            return 1;
        } else if (firstValue < secondValue) {
            return -1;
        } else {
            return 0;
        }
    }
}
