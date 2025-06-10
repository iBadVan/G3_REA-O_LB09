package graph;

import lista.ListLinked;

public class Vertex<E> {
    private E data;
    protected ListLinked<Edge<E>> listAdj;
    public boolean visited;  

    public Vertex(E data) {
        this.data = data;
        listAdj = new ListLinked<>();
        this.visited = false; 
    }

    public E getData() {
        return data;
    }

    public boolean equals(Object o) {
        if (o instanceof Vertex<?>) {
            Vertex<E> v = (Vertex<E>) o;
            return this.data.equals(v.data);
        }
        return false;
    }

    public String toString() {
        return this.data + " --> " + this.listAdj.toString() + "\n";
    }
}
