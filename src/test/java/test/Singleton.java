package test;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package test
 * @ClassName Singleton
 * @Description
 * @Author young
 * @Modify young
 * @Date 2020/2/19 11:02
 * @Version 1.0.0
 **/
public class Singleton {

    private static class SingletonHolder {
        private static Singleton singleton;

        static {
            singleton = new Singleton();
        }
    }

    public static Singleton getInstance() {
        return SingletonHolder.singleton;
    }

    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        System.out.println("instance = " + instance);
    }
}
