package level2.challenge1.test;


import level2.challenge1.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionTest {
    @Test
    void case1Test(){
        int result = Solution.solution(143);
        assertEquals(3,result);
    }

    @Test
    void case2Test(){
        int result = Solution.solution(10);
        assertEquals(1,result);
    }
}
