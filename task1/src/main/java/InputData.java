class WrongInputException extends Exception {
    public WrongInputException(String errorMsg) {
        super(errorMsg); // superclasses' method
    }
}

class InputData {
    private String numGenerated;

    public InputData(String numGenerated) {
        this.numGenerated = numGenerated;
    }

    private boolean isCorrectStr(String input_str) {
        if (input_str.length() != numGenerated.length())
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

    public int countCowsAndBulls(String str) throws WrongInputException {
        if (!isCorrectStr(str)) {
            throw new WrongInputException ("Possible lenght is " + numGenerated.length() +
                    ", correct format is digits 0..9, all are different.");
        }

        int sameNumGenerated = 0, bulls = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == numGenerated.charAt(i))
                bulls++;
            if (numGenerated.contains(Character.toString(str.charAt(i))))
                sameNumGenerated++;
        }

        System.out.println("Cows: " + (sameNumGenerated - bulls) + ", bulls: " + bulls);
        if (bulls == numGenerated.length()) {
            System.out.println("Congrats! You've guessed the string.");
        }
        return bulls;
    }
}
