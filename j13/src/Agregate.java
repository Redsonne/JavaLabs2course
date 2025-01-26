public interface Agregate<T>{
    Iterator<T> createIterator();
}
interface Iterator<T>{
    void first();
    boolean is_done();
    void next();

    T current_item();
}
