public class Game {
    public static void main(String[] args) {
        Generator generator = new Generator();
        String generatedStr = generator.generateNum();
        InputData inputData = new InputData(generatedStr);
        InputReader reader = new InputReader();

        do {
            String userStr = reader.readLine();
            try {
                inputData.countCowsAndBulls(userStr);
                System.out.println("Cows: " + inputData.getCows() + ", bulls: " + inputData.getBulls());
            } catch (WrongInputException exception) {
                System.out.println(exception.getMessage());
            }
        } while (inputData.getBulls() != generatedStr.length());
        System.out.println("Congrats! You've guessed the string.");
    }
}
