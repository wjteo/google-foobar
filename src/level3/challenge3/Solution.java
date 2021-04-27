package level3.challenge3;

import java.math.BigInteger;

public class Solution {
    static final BigInteger MINUS_ONE=BigInteger.valueOf(-1);
    static final BigInteger ONE=BigInteger.ONE;
    static final BigInteger ZERO=BigInteger.ZERO;


    public static String solution(String x, String y) {
        BigInteger numX=new BigInteger(x);
        BigInteger numY=new BigInteger(y);
        BigInteger numCycles=getSmallestCycle(numX,numY, BigInteger.ZERO);
        String result=numCycles.compareTo(BigInteger.ZERO)==-1?"impossible":numCycles.toString();
        return result;
    }

    public static BigInteger getSmallestCycle(BigInteger x, BigInteger y, BigInteger cycle){
        while(!(isOne(x) && isOne(y))){
            if(isOne(x)){
                return cycle.add(y.subtract(ONE));
            }
            if(isOne(y)){
                return cycle.add(x.subtract(ONE));
            }

            if(x.compareTo(y)==1){ //x larger than y
                BigInteger[] quotientAndRemainder = x.divideAndRemainder(y);
                BigInteger quotient=quotientAndRemainder[0];
                BigInteger remainder=quotientAndRemainder[1];
                if(isZero(remainder)){
                    return MINUS_ONE;
                }
                cycle=cycle.add(quotient);
                x=remainder;
                continue;
            }

            if(x.compareTo(y)==-1){ //x smaller than y
                BigInteger[] quotientAndRemainder = y.divideAndRemainder(x);
                BigInteger quotient=quotientAndRemainder[0];
                BigInteger remainder=quotientAndRemainder[1];
                if(isZero(remainder)){
                    return MINUS_ONE;
                }
                cycle=cycle.add(quotient);
                y=remainder;
                continue;
            }
            return MINUS_ONE;
        }
        return cycle;
    }

    public static boolean isOne(BigInteger num){
        return num.compareTo(ONE)==0;
    }

    public static boolean isZero(BigInteger num){
        return num.compareTo(ZERO)==0;
    }
}