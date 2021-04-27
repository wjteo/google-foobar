package level3.challenge2.test;

import level3.challenge2.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionTest {

    @Test
    void case1Test(){
        int result = Solution.solution("4");
        assertEquals(2, result);
    }

    @Test
    void case2Test(){
        int result = Solution.solution("15");
        assertEquals(5, result);
    }
}
