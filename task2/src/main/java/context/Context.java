package context;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

public class Context { // используем функции для стека и мапы
    private Stack<Double> numStack = new Stack<>();
    private HashMap<String, Double> values = new HashMap<>();

    public void push(Double val) {
        numStack.push(val);
    }

    public Double pop() throws EmptyStackException {
           return numStack.pop();
    }

    public Double peek() throws EmptyStackException {
            return numStack.peek();
    }

    public void insertValue(String name, Double val) {
        values.put(name, val);
    }

    public Double getValue(String name) {
        return values.get(name);
    }
}
