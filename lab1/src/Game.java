class Game {
    public static void main(String[] args) {
        Generator generator = new Generator();
        String new_str = generator.generateNum();

        InputData reader = new InputData(new_str);
        String user_str = reader.readStr();

        while (reader.countCowsAndBulls(user_str) != new_str.length()) {
            user_str = reader.readStr();
        }
    }
}