package ru.sbt.MyStream;

import java.util.ArrayList;
import java.util.List;

public class MyStreamTester {
    public static void main(String[] args) {


        List<Person> testList = new ArrayList<Person>();
        testList.add(new Person("Петров Александр", 46));
        testList.add(new Person("Глуштарь Владислав", 45));
        testList.add(new Person("Власов Сергей", 47));
        testList.add(new Person("Юниченко Анатолий", 35));
        testList.add(new Person("Антонова Виктория", 16));
        testList.add(new Person("Толстой Петр", 14));
        testList.add(new Person("Прилучный Павел", 25));
        testList.add(new Person("Чаплин Чарльз", 60));
        testList.add(new Person("Станиславский Антон", 28));
        testList.add(new Person("Китаев Игорь", 29));

        System.out.println("Исходный лист");
        System.out.println(testList);

        System.out.println("Оставим только больше 30 и прибавим каждому 1 год");
        System.out.println(MyStream.of(testList).filter(p -> p.getAge() > 30)
                .transform(p -> new Person(p.getName(), p.getAge() + 1)).toList());
        System.out.println("Оставим только меньше 30, добавим к имени \"!\" и преобразуем в Map");
        System.out.println(MyStream.of(testList).filter(p -> p.getAge() < 30)
                .transform(p -> new Person(p.getName() + "!", p.getAge()))
                .toMap(p -> p.getName().split(" ")[0], p -> p));


    }
}
