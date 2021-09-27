package wiwy.covid.apicall;

import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;

import java.util.Comparator;

public class AbrCoronaComparator implements Comparator<AbrCoronaDto> {

    @Override
    public int compare(AbrCoronaDto o1, AbrCoronaDto o2) {
        int firstValue = o1.getSeq();
        int secondValue = o2.getSeq();

        if (firstValue > secondValue) {
            return 1;
        } else if (firstValue < secondValue) {
            return -1;
        } else {
            return 0;
        }
    }
}
