package level4.challenge1.test;

import level4.challenge1.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionTest {

    @Test
    void case1Test(){
        int result = Solution.solution(new int[]{1,1});
        assertEquals(2,result);
    }

    @Test
    void case2Test(){
        int result = Solution.solution(new int[]{1, 7, 3, 21, 13, 19});
        assertEquals(0,result);
    }

}
