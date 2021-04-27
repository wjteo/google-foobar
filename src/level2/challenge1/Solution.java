package level2.challenge1;

public class Solution{

    public static int solution(int total_lambs){
        int min=computeMinimum(total_lambs);
        System.out.println("min:"+min);
        int upperLimit=computeNumOfTermsFromSumOfLinearProgression(total_lambs);
        if(upperLimit<min){
            upperLimit=min;
        }
        System.out.println("upperbound:"+upperLimit);
        int max=searchForMaximum(min,upperLimit,total_lambs);
        System.out.println("max:"+max);
        return max-min;
    }

    public static int computeMinimum(int total_lambs){
        if(total_lambs<=2){
            return 1;
        }
        return (int)(Math.log(total_lambs+1)/Math.log(2));
    }


    //compute the number of terms we can get from sum of 1,2,3...
    public static int computeNumOfTermsFromSumOfLinearProgression(int sum){
        return (int)((-1+Math.sqrt(8)*Math.sqrt(sum+1/8))/2);
    }


    public static int searchForMaximum(int lowerLimit,int upperLimit,int total_lambs){
        if(lowerLimit==upperLimit){
            return lowerLimit;
        }

        if(upperLimit-lowerLimit==1){
            if(sumOfFibonnaciSequence(upperLimit)>total_lambs){
                return lowerLimit;
            }else{
                return upperLimit;
            }
        }



        //assume maximum is the middle of the lower and upper limit
        int n = (int)((lowerLimit+upperLimit)/2);
        System.out.println("trying lower limit: " +lowerLimit);
        System.out.println("trying upper limit: " +upperLimit);
        System.out.println("trying lower n: " +n);

        int sum=sumOfFibonnaciSequence(n);

        if(sum==total_lambs){
            //jackpot
            return n;
        }
        if(sum<total_lambs){
            System.out.println("n: "+n+" sum: "+sum+ " too low");
            //too low, try again
            return searchForMaximum(n,upperLimit,total_lambs);
        }
        //too high, try again
        System.out.println("n: "+n+" sum: "+sum+ " too high");
        return searchForMaximum(lowerLimit,n-1,total_lambs);
    }


    public static int sumOfFibonnaciSequence(int n){
        return getFibonacciTerm(n+2)-1;
    }

    static final double ROOT5=Math.sqrt(5);
    static final double PHI=(1+ROOT5)/2;

    //binet's formula
    public static int getFibonacciTerm(int n){
        return (int) ((Math.pow(PHI, n) - Math.pow(-PHI, -n))/ROOT5);
    }
}

