package level5.challenge1.test;

import level5.challenge1.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSolution {

    @Test
    void case1Test(){
        boolean[][] input=new boolean[][]{{true, false, true}, {false, true, false}, {true, false, true}};
        int result=Solution.solution(input);
        assertEquals(4, result);
    }

    @Test
    void case2Test(){
        boolean[][] input=new boolean[][]{{true, false, true, false, false, true, true, true}, {true, false, true, false, false, false, true, false}, {true, true, true, false, false, false, true, false}, {true, false, true, false, false, false, true, false}, {true, false, true, false, false, true, true, true}};
        int result=Solution.solution(input);
        assertEquals(254, result);
    }

    @Test
    void case3Test(){
        boolean[][] input=new boolean[][]{{true, true, false, true, false, true, false, true, true, false}, {true, true, false, false, false, false, true, true, true, false}, {true, true, false, false, false, false, false, false, false, true}, {false, true, false, false, false, false, true, true, false, false}};
        int result=Solution.solution(input);
        assertEquals(11567, result);
    }

}
