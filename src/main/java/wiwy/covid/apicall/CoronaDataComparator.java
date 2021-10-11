package wiwy.covid.apicall;

import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;
import wiwy.covid.apicall.coronadto.CoronaData;

import java.util.Comparator;

public class CoronaDataComparator implements Comparator<CoronaData> {

    @Override
    public int compare(CoronaData o1, CoronaData o2) {
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
