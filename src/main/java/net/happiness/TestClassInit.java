package net.happiness;

public class TestClassInit {

    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println("Class: " + InitTest.class);
        InitTest[] data = new InitTest[10];
        System.out.println("Array is created");
        System.out.println(InitTest.class.getDeclaredConstructor());
        System.out.println(new InitTest());
    }

}

class InitTest {
    static {
        System.out.println("Initialized");
    }
}
