package level4.challenge2.test;

import level4.challenge2.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionTest {
    @Test
    void case1Test(){
        int result = Solution.solution(new int[]{3,2},new int[]{1,1},new int[]{2,1},4);
        assertEquals(7,result);
    }

    @Test
    void case2Test(){
        int result = Solution.solution(new int[]{300,275},new int[]{150,150},new int[]{185,100},500);
        assertEquals(9,result);
    }
}
