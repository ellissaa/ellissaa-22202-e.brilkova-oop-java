import org.testng.Assert;
import org.testng.annotations.Test;

public class InputDataTest {
    @Test
    public void test1() {
        InputData input = new InputData("1234");
        Assert.expectThrows(WrongInputException.class,
                () -> input.countCowsAndBulls("bad line"));
    }

    @Test
    public void test2() {
        InputData input = new InputData("1234");
        Assert.expectThrows(WrongInputException.class,
                () -> input.countCowsAndBulls("12345"));
    }

    @Test
    public void test3() {
        InputData input = new InputData("1234");
        Assert.expectThrows(WrongInputException.class,
                () -> input.countCowsAndBulls("1123"));
    }

    @Test
    public void test4() {
        InputData input = new InputData("1234");
        try {
            Assert.assertEquals(input.countCowsAndBulls("1234"), 4);
        } catch (WrongInputException exception) {
            System.out.println(exception.getMessage());
        }
    }
}