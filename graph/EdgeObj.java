package graph;

public class EdgeObj<V, E> {
    protected E info; 
    protected VertexObj<V, E> endVertex1; 
    protected VertexObj<V, E> endVertex2; 
    protected int position; 

    public EdgeObj(VertexObj<V, E> vert1, VertexObj<V, E> vert2, E info, int position) {
        this.endVertex1 = vert1;
        this.endVertex2 = vert2;
        this.info = info;
        this.position = position;
    }

    public E getInfo() {
        return info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public VertexObj<V, E> getEndVertex1() {
        return endVertex1;
    }

    public void setEndVertex1(VertexObj<V, E> endVertex1) {
        this.endVertex1 = endVertex1;
    }

    public VertexObj<V, E> getEndVertex2() {
        return endVertex2;
    }

    public void setEndVertex2(VertexObj<V, E> endVertex2) {
        this.endVertex2 = endVertex2;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeObj<?, ?> that = (EdgeObj<?, ?>) o;
        return position == that.position &&
                endVertex1.equals(that.endVertex1) &&
                endVertex2.equals(that.endVertex2);
    }

    @Override
    public int hashCode() {
        return endVertex1.hashCode() + endVertex2.hashCode() + 31 * position;
    }

    @Override
    public String toString() {
        return "Edge between " + endVertex1.getInfo() + " and " + endVertex2.getInfo() + " with info: " + info;
    }
}
