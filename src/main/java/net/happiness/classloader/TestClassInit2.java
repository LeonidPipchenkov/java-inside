package net.happiness.classloader;

public class TestClassInit2 {

    public static void main(String[] args) {
        try {
            new InitTest2();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        try {
            new InitTest2();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}

class InitTest2 {
    static {
        System.out.println("In initializer");
        if (true) {
            throw new RuntimeException("Oops");
        }
    }
}
