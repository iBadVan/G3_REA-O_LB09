package lista;

import java.util.Iterator;

import graph.Vertex;

public class ListLinked<T> implements Iterable<T> {

    private Node<T> head;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public ListLinked() {
        head = null;
        size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public boolean remove(T data) {
        if (head == null) return false;

        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> current = head;
        while (current.next != null && !current.next.data.equals(data)) {
            current = current.next;
        }

        if (current.next == null) return false;

        current.next = current.next.next;
        size--;
        return true;
    }

    public boolean contains(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) return true;
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void print() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;

        return current.data;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new java.util.NoSuchElementException();
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    public boolean removeIf(java.util.function.Predicate<T> condition) {
        if (head == null) return false;

        while (head != null && condition.test(head.data)) {
            head = head.next;
            size--;
        }

        if (head == null) return false; 

        Node<T> current = head;
        while (current.next != null) {
            if (condition.test(current.next.data)) {
                current.next = current.next.next;
                size--;
            } else {
                current = current.next;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    public int indexOf(Vertex<E> vertex) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indexOf'");
    }
}
