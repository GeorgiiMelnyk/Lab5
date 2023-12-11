package lab5.application;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ShapeEditor shapeEditor = ShapeEditor.getInstance();
        List<Integer> a;
        List<Integer> b = new ArrayList<>();
        b.add(1);
        b.add(1);
        b.add(1);
        a = new ArrayList<>(b);
        System.out.println(b);
        b.clear();
        System.out.println(a);

    }
}