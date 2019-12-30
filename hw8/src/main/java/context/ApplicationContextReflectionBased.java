package context;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;


public class ApplicationContextReflectionBased implements ApplicationContext {
    Reflections reflections = new Reflections();

    @Override
    public <T> T getComponent(Class<T> componentType, String name) {
        Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(componentType);
        for (Class<? extends T> subType : subTypes) {
            try {
                Method m = subType.getMethod("getName");
                Constructor constructor = subType.getDeclaredConstructors()[0];
                boolean constructorWasPrivate = false;
                if (!constructor.isAccessible()) {
                    constructorWasPrivate = true;
                    constructor.setAccessible(true);
                }
                T instance = (T) constructor.newInstance();
                if (constructorWasPrivate) {
                    constructor.setAccessible(false);
                }
                if (m.invoke(instance).equals(name)) {
                    return subType.newInstance();
                }
            } catch (NoSuchMethodException e) {
                System.out.println("class " + subType + " not implementing getComponentName() method");
            } catch (IllegalAccessException e) {
                System.out.println("method getComponentName() is private in " + subType);
            } catch (InstantiationException e) {
                System.out.println("can't make instance of " + subType);
            } catch (InvocationTargetException e) {
                System.out.println("can't invoke method getComponentName() of " + subType);
            }

        }
        return null;
    }
}
