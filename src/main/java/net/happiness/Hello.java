package net.happiness;

public class Hello {

    static class X {
        Object get() {
            return null;
        }
    }

    static class Y extends X {
        String get() {
            return "foo";
        }
    }

    void test(X x) {
        Object o = x.get();
    }

}
