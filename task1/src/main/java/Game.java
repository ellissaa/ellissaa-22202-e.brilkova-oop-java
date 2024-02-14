public class Game {
    public static void main(String[] args) {
        Generator generator = new Generator();
        String generatedStr = generator.generateNum();
        InputData inputData = new InputData(generatedStr);
        InputReader reader = new InputReader();
        int numOfBulls = 0;

        do {
            String userStr = reader.readLine();
            try {
                numOfBulls = inputData.countCowsAndBulls(userStr);
            } catch (WrongInputException exception) {
                System.out.println(exception.getMessage());
            }
        } while (numOfBulls != generatedStr.length());
    }
}
