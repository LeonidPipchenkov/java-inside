package net.happiness.bytecode;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.InvocationTargetException;

public class TestClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        String myRunnableName = "net.happiness.bytecode.MyRunnable";

        ClassLoader loader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if (name.equals(myRunnableName)) {
                    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                    String runnablePathName = myRunnableName.replace(".", "/");
                    cw.visit(Opcodes.V17, Opcodes.ACC_SUPER | Opcodes.ACC_PUBLIC, runnablePathName, null, "java/lang/Object",
                            new String[] {"java/lang/Runnable"});

                    MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
                    mv.visitInsn(Opcodes.RETURN);
                    mv.visitMaxs(0, 0);
                    mv.visitEnd();

                    mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "run", "()V", null, null);
                    mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"); // stack: System.out
                    mv.visitInsn(Opcodes.ICONST_3); // stack: System.out, 3
                    mv.visitIntInsn(Opcodes.BIPUSH, 10); // stack: System.out, 3, 10
                    mv.visitInsn(Opcodes.IADD); // stack: System.out, 13
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
                    mv.visitInsn(Opcodes.RETURN);
                    mv.visitMaxs(0, 0);
                    mv.visitEnd();

                    byte[] classData = cw.toByteArray();
                    return defineClass(myRunnableName, classData, 0, classData.length);
                }
                return super.loadClass(name);
            }
        };

        Runnable original = new MyRunnable();
        original.run();
        Class<?> loadedClass = loader.loadClass(myRunnableName);
        Runnable loaded = loadedClass.asSubclass(Runnable.class).getConstructor().newInstance();
        loaded.run();
    }

}
