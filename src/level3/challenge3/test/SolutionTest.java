package level3.challenge3.test;


import level3.challenge3.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionTest {

    @Test
    void case1Test(){
        String result=Solution.solution("2","1");
        assertEquals("1", result);
    }

    @Test
    void case2Test(){
        String result=Solution.solution("4","7");
        assertEquals("4", result);
    }

    @Test
    void case3Test(){
        String result=Solution.solution("2","4");
        assertEquals("impossible", result);
    }


}


