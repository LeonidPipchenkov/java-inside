package net.happiness.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class TestClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        String myRunnableName = "net.happiness.classloader.MyRunnable";

        ClassLoader loader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if (name.equals(myRunnableName)) {
                    try (InputStream stream = MyRunnable.class.getResourceAsStream("MyRunnable.class")) {
                        if (Objects.nonNull(stream)) {
                            byte[] classData = stream.readAllBytes();
                            return defineClass(myRunnableName, classData, 0, classData.length);
                        }
                    } catch (IOException e) {
                        throw new ClassNotFoundException();
                    }
                }
                return super.loadClass(name);
            }
        };

        Runnable original = new MyRunnable();
        original.run();
        Class<?> loadedClass = loader.loadClass(myRunnableName);
        Runnable loaded = loadedClass.asSubclass(Runnable.class).getConstructor().newInstance();
        loaded.run();

        System.out.println(original.getClass());
        System.out.println(loaded.getClass());
        System.out.println(original.getClass().getClassLoader());
        System.out.println(loaded.getClass().getClassLoader());
    }

}
