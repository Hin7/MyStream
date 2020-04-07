package ru.sbt.MyStream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class MyStreamTest {

    private List<Person> testList;
    private List<Person> etalonList;

    @Before
    public void setUp() throws Exception {
        testList = new ArrayList<>();
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

        etalonList = new ArrayList<>();
        etalonList.addAll(testList);
    }

    @Test
    public void of() throws Exception {
        assertNotNull(MyStream.of(testList));
    }

    @Test
    public void filter() throws Exception {
        MyStream.of(testList).filter(p -> p.getAge() > 30);
        assertEquals(testList, etalonList);
    }

    @Test
    public void transform() throws Exception {
        MyStream.of(testList).transform(p -> new Person(p.getName(), p.getAge() + 3));
        assertEquals(testList, etalonList);
    }

    @Test
    public void toList() throws Exception {
        assertEquals(testList, MyStream.of(testList).toList());
        List<Person> transList = MyStream.of(testList).transform(p -> new Person(p.getName(), p.getAge() + 3))
                .toList();
        assertNotEquals(testList, transList);
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(transList.get(i).getName(), testList.get(i).getName());
            assertEquals(transList.get(i).getAge(), testList.get(i).getAge() + 3);
        }
    }

    @Test
    public void toMap() throws Exception {
        Map<String, Person> testMap = MyStream.of(testList)
                .filter(p -> p.getAge() < 30)
                .transform(p -> new Person(p.getName(), p.getAge() + 5))
                .toMap(p -> p.getName(), p -> p);
        assertNotNull(testMap);
        for (Person item : testList) {
            if (item.getAge() < 30) {
                Person transItem = new Person(item.getName(), item.getAge() + 5);
                Person mapItem = testMap.get(item.getName());
                assertEquals(transItem.getName(), mapItem.getName());
                assertEquals(transItem.getAge(), mapItem.getAge());
            } else
                assertNull(testMap.get(item.getName()));
        }
    }

}
