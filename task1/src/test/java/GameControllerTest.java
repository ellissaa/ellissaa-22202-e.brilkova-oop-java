import org.testng.Assert;
import org.testng.annotations.Test;

public class GameControllerTest {
    @Test
    public void test1() {
        GameController input = new GameController("1234");
        Assert.expectThrows(WrongInputException.class,
                () -> input.countCowsAndBulls("bad line"));
    }

    @Test
    public void test2() {
        GameController input = new GameController("1234");
        Assert.expectThrows(WrongInputException.class,
                () -> input.countCowsAndBulls("12345"));
    }

    @Test
    public void test3() {
        GameController input = new GameController("1234");
        Assert.expectThrows(WrongInputException.class,
                () -> input.countCowsAndBulls("1123"));
    }

    @Test
    public void test4() {
        GameController input = new GameController("1234");
        try {
            input.countCowsAndBulls("1345");
        } catch (WrongInputException exception) {
            System.out.println(exception.getMessage());
        }
        Assert.assertEquals(input.getBulls(), 1);
        Assert.assertEquals(input.getCows(), 2);
    }

    @Test
    public void test5() {
        GameController input = new GameController("1234");
        try {
            input.countCowsAndBulls("4123");
        } catch (WrongInputException exception) {
            System.out.println(exception.getMessage());
        }
        Assert.assertEquals(input.getBulls(), 0);
        Assert.assertEquals(input.getCows(), 4);
    }

    @Test
    public void test6() {
        GameController input = new GameController("1234");
        try {
            input.countCowsAndBulls("1234");
        } catch (WrongInputException exception) {
            System.out.println(exception.getMessage());
        }
        Assert.assertEquals(input.getBulls(), 4);
        Assert.assertEquals(input.getCows(), 0);
    }
}
