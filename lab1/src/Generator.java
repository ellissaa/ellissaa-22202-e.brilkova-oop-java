import java.util.Random;

class Generator {
    private final Random numGenerator = new Random();
    public String generateNum() {
         String str = "";
         for (int i = 0; i < 4; i++) {
            Integer num = numGenerator.nextInt(10);
            while (str.contains(num.toString())) {
                num = numGenerator.nextInt(10);
            }
            str += num.toString();
         }
         return str;
    }
}
