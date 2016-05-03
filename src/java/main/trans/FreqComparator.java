package trans;

import java.util.Comparator;

public class FreqComparator implements Comparator<NameFreq> {
 
    @Override
    public int compare(NameFreq nf1, NameFreq nf2) {
        if(nf1.getFreq() < nf2.getFreq()){
            return 1;
        } else {
            return -1;
        }
    }
}
 
