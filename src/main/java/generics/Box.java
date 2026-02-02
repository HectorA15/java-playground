package generics;

public class Box<T> {
    private T value;

    public T setValue(T value) {
        return this.value = value;
    }

    public T getValue() {
        return value;
    }

}
