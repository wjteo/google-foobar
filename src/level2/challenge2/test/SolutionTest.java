package level2.challenge2.test;


import level2.challenge2.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SolutionTest {
    @Test
    void case1Test(){
        String[] result = Solution.solution(new String[]{"1.1.2", "1.0", "1.3.3", "1.0.12", "1.0.2"});
        assertArrayEquals(new String[]{"1.0", "1.0.2", "1.0.12", "1.1.2", "1.3.3"},result);
    }

    @Test
    void case2Test(){
        String[] result = Solution.solution(new String[]{"1.11", "2.0.0", "1.2", "2", "0.1", "1.2.1", "1.1.1", "2.0"});
        assertArrayEquals(new String[]{"0.1", "1.1.1", "1.2", "1.2.1", "1.11", "2", "2.0", "2.0.0"},result);
    }
}