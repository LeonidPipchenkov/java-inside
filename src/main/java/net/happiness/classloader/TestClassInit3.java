package net.happiness.classloader;

public class TestClassInit3 {

    static final String a = "String a";
    static final String b = "String b".trim();
    static final String c = init();
    static final String d = "String " + "d";
    static final String e = "String e".trim();

    private static String init() {
        System.out.println("a = " + a); // a = String a
        System.out.println("b = " + b); // b = String b
        System.out.println("c = " + c); // c = null
        System.out.println("d = " + d); // d = String d
        System.out.println("e = " + e); // e = null
        return "String c";
    }

    public static void main(String[] args) {
    }

}
