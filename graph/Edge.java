package graph;

public class Edge<E> {
    private Vertex<E> refDest;
    private int weight;

    public Edge(Vertex<E> refDest) {
        this(refDest, -1);
    }

    public Edge(Vertex<E> refDest, int weight) {
        this.refDest = refDest;
        this.weight = weight;
    }

    public Vertex<E> getRefDest() {
        return refDest;
    }

    public boolean equals(Object o) {
        if (o instanceof Edge<?>) {
            Edge<E> e = (Edge<E>) o;
            return this.refDest.equals(e.refDest);
        }
        return false;
    }

    public String toString() {
        if (this.weight > -1)
            return refDest.getData() + " [" + this.weight + "] ";
        else
            return refDest.getData() + " ";
    }
}
