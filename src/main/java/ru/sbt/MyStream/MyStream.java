package ru.sbt.MyStream;
/**
 * MyStream - своя реализация класса, похожего на Stream.
 * Урок 10 СБТ. Lambda, Stream API.
 * <p>
 * Куда "всунуть" параметризацию по PECS не очень понял.
 *
 * После лекции Еськова Андрея, где он обратил внимание на наличие набора стандартных функциональных интерфейсов,
 * убрал использование своих MyPredicate, MyTransformer и MyFunction. /18/05/2020/
 *
 * @author - Hin7
 * @version - 1.1 07/04/2020
 * @version - 1.2 18/05/2020
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class MyStream<T> {

    //лист с перечнем действий, которые надо выполнить при вызове терминальной операции
    private List<Object> actionList = new ArrayList<>();
    //коллекция с данными
    private List<T> dataList;

    public MyStream(List<T> list) {
        dataList = list;
    }

    static public <T> MyStream<T> of(List<T> list) {
        return new MyStream(list);
    }

    /**
     * Фильтрация колекции в соответствии с заданным условием.
     * Не терминальная операция.
     * @param predicate - задаваемое условие для фильтрации
     * @return - возвращает себя.
     */
    public MyStream<T> filter(Predicate<T> predicate) {
        actionList.add(predicate);
        return this;
    }

    /**
     * Преобразование колекции в соответствии с заданной операцией трансформации.
     * Не терминальная операция.
     * @param transformer - задаваемая операция трансформации
     * @return - возвращает сам себя.
     */
    public MyStream<T> transform(Function<T, T> transformer) {
        actionList.add(transformer);
        return this;
    }

    /**
     * Выполнение перечня заданных операций над одним элементом колекции
     * @param item - элемент колекции
     * @return - преобразованный элемент коллекции или null, если элемент не прошел условие при фильтрации
     */
    private T executeActionsOnItem(T item) {
        T newItem = item;
        for (Object act : actionList) {
            if (act instanceof Predicate) {
                if (!((Predicate<T>) act).test((newItem)))
                    return null;
            } else if (act instanceof Function)
                newItem = ((Function<T, T>) act).apply(newItem);
        }
        return newItem;
    }

    /**
     * Возвращение преобразованной коллекции.
     * Терминальная операция.
     * @return - преобразованная колекция.
     */
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        for (T item : dataList) {
            T newItem = executeActionsOnItem(item);
            if (newItem != null)
                result.add(newItem);
        }
        actionList.clear();
        return result;
    }

    /**
     * Возвращаем Map на основе преобразованного листа.
     * Терминальная операция.
     * @param itemToKey - функция преобразования в ключ для Map
     * @param itemToVal - функция преобразования в значение для Map
     * @param <K> - тип ключа Map
     * @param <V> - тип значения Map
     * @return - Map из преобразованного листа
     */
    public <K, V> Map<K, V> toMap(Function<T, K> itemToKey, Function<T, V> itemToVal) {
        Map<K, V> result = new HashMap<>();
        for (T item : dataList) {
            T newItem = executeActionsOnItem(item);
            if (newItem != null)
                result.put(itemToKey.apply(newItem), itemToVal.apply(newItem));
        }
        actionList.clear();
        return result;
    }

}
