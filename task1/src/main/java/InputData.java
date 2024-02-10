import java.util.Scanner;

class InputData {
    private String num;
    public InputData(String num) {
        this.num = num;
    }

    private boolean isCorrectStr(String input_str) {
        if (input_str.length() != num.length())
            return false;
        String new_str = "";
        for (int i = 0; i < input_str.length(); i++) {
            Character symbol = input_str.charAt(i);
            if (!Character.isDigit(input_str.charAt(i)) ||
                    new_str.contains(symbol.toString()))
                return false;
            new_str += symbol;
        }
        return true;
    }
    public String readStr() {
        Scanner user_str = new Scanner(System.in);
        String input_str = user_str.nextLine();

        while (!isCorrectStr(input_str)) {
            System.out.println("Possible lenght is " + num.length() +
                    ", correct format is digits 0..9, all are different.");
            input_str = user_str.nextLine();
        }
        return input_str;
    }

    public int countCowsAndBulls(String str) {
        int same_nums = 0, bulls = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == num.charAt(i))
                bulls++;
            if (num.contains(Character.toString(str.charAt(i))))
                same_nums++;
        }

        System.out.println("Cows: " + (same_nums - bulls) + ", bulls: " + bulls);
        if (bulls == num.length()) {
            System.out.println("Congrats! You've guessed the string.");
        }
        return bulls;
    }
}
