package level3.challenge1.test;


import level3.challenge1.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionTest {
    @Test
    void case1Test(){
        int result = Solution.solution(new int[]{1, 1, 1});
        assertEquals(1,result);
    }

    @Test
    void case2Test(){
        int result = Solution.solution(new int[]{1, 2, 3, 4, 5, 6});
        assertEquals(3,result);

    }
}