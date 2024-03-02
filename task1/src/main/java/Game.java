public class Game {
    public static void main(String[] args) {
        Generator generator = new Generator();
        String generatedStr = generator.generateNum();
        GameController GameController = new GameController(generatedStr);
        InputReader reader = new InputReader();

        do {
            String userStr = reader.readLine();
            try {
                GameController.countCowsAndBulls(userStr);
                System.out.println("Cows: " + GameController.getCows() + ", bulls: " + GameController.getBulls());
            } catch (WrongInputException exception) {
                System.out.println(exception.getMessage());
            }
        } while (GameController.getBulls() != generatedStr.length());
        System.out.println("Congrats! You've guessed the string.");
    }
}
