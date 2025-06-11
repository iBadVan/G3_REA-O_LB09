package graph;

public class VertexObj<V, E> {
    protected V info;
    protected int position;
    public boolean visited;

    public VertexObj(V info, int position) {
        this.info = info;
        this.position = position;
        this.visited = false;
    }

    public V getInfo() {
        return info;
    }

    public int getPosition() {
        return position;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexObj<?, ?> that = (VertexObj<?, ?>) o;
        return position == that.position && info.equals(that.info);
    }

    @Override
    public int hashCode() {
        return info.hashCode() + 31 * position;
    }

    @Override
    public String toString() {
        return info.toString();
    }
}
