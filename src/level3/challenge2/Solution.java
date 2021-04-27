package level3.challenge2;

import java.math.BigInteger;

public class Solution {
    public static int solution(String x){
        BigInteger numPellets=  new BigInteger(x);
        String bits = numPellets.toString(2);
        int count=0;
        int consecutive1s=0;

        //process until the 2nd last bit
        for(int i=bits.length()-1;i>0;i--){
            if(bits.charAt(i)=='1'){
                consecutive1s++;
                continue;
            }

            //if 0, handle past string of 1s
            //handle last bit of 1 and leftover string of 1s
            switch(consecutive1s){
                case 0:
                    count++; //divide pellet into 2: xxx0 -> xxx
                    consecutive1s=0;
                    continue;
                case 1:
                    count+=2; //remove pellet, then divide into 2: xxx01 -> xxx00 -> xxx0
                    count++; //divide into 2: xxx0->xxx
                    consecutive1s=0;
                    continue;
                default:
                    count+=(consecutive1s+1); //add pellet: xxx0111 -> xxx1000 -> xxx1
                    consecutive1s=1; //current bit is changed to 1
            }
        }

        //handle last bit of 1 and leftover string of 1s
        switch(consecutive1s){
            case 0:
                return count;
            case 1:
                count+=2; //11->10->1;
                return count;
            default:
                count+=(consecutive1s+2); //111-> 1000-> 1
                return count;
        }

    }


}
