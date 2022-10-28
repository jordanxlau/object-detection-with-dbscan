public interface Sequence<E>{
    public int size();

    public E get(int index);

    public void addFirst(E elem);

    public void add(E elem);
}