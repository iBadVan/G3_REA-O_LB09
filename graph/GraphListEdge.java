package graph;

import java.util.ArrayList;


public class GraphListEdge<V, E> {
    ArrayList<VertexObj<V, E>> secVertex;
    ArrayList<EdgeObj<V, E>> secEdge;

    public GraphListEdge() {
        this.secVertex = new ArrayList<>();
        this.secEdge = new ArrayList<>();
    }

    public void insertVertex(V v) {
        for (VertexObj<V, E> vertex : secVertex) {
            if (vertex.info.equals(v)) {
                return; // El vértice ya existe
            }
        }
        VertexObj<V, E> newVertex = new VertexObj<>(v, secVertex.size());
        secVertex.add(newVertex);
    }

    public void insertEdge(V v, V z) {
        VertexObj<V, E> vertex1 = null;
        VertexObj<V, E> vertex2 = null;

        for (VertexObj<V, E> vertex : secVertex) {
            if (vertex.info.equals(v)) {
                vertex1 = vertex;
            }
            if (vertex.info.equals(z)) {
                vertex2 = vertex;
            }
        }

        if (vertex1 != null && vertex2 != null) {
            for (EdgeObj<V, E> edge : secEdge) {
                if ((edge.endVertex1 == vertex1 && edge.endVertex2 == vertex2) ||
                    (edge.endVertex1 == vertex2 && edge.endVertex2 == vertex1)) {
                    return; // La arista ya existe
                }
            }
            EdgeObj<V, E> newEdge = new EdgeObj<>(vertex1, vertex2, null, secEdge.size());
            secEdge.add(newEdge);
        }
    }

    public boolean searchVertex(V v) {
        for (VertexObj<V, E> vertex : secVertex) {
            if (vertex.info.equals(v)) {
                return true;
            }
        }
        return false;
    }

    public boolean searchEdge(V v, V z) {
        VertexObj<V, E> vertex1 = null;
        VertexObj<V, E> vertex2 = null;

        for (VertexObj<V, E> vertex : secVertex) {
            if (vertex.info.equals(v)) {
                vertex1 = vertex;
            }
            if (vertex.info.equals(z)) {
                vertex2 = vertex;
            }
        }

        if (vertex1 != null && vertex2 != null) {
            for (EdgeObj<V, E> edge : secEdge) {
                if ((edge.endVertex1 == vertex1 && edge.endVertex2 == vertex2) ||
                    (edge.endVertex1 == vertex2 && edge.endVertex2 == vertex1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void bfs(V v) {
        VertexObj<V, E> startVertex = null;
        for (VertexObj<V, E> vertex : secVertex) {
            if (vertex.info.equals(v)) {
                startVertex = vertex;
                break;
            }
        }

        if (startVertex == null) return;

        for (VertexObj<V, E> vertex : secVertex) {
            vertex.visited = false;
        }

        ArrayList<VertexObj<V, E>> queue = new ArrayList<>();
        startVertex.visited = true;
        queue.add(startVertex);

        System.out.println("Recorrido en anchura comenzando en: " + v);

        while (!queue.isEmpty()) {
            VertexObj<V, E> currentVertex = queue.remove(0);
            System.out.println(currentVertex.info);

            for (EdgeObj<V, E> edge : secEdge) {
                if (edge.endVertex1 == currentVertex && !edge.endVertex2.visited) {
                    edge.endVertex2.visited = true;
                    queue.add(edge.endVertex2);
                }
                if (edge.endVertex2 == currentVertex && !edge.endVertex1.visited) {
                    edge.endVertex1.visited = true;
                    queue.add(edge.endVertex1);
                }
            }
        }
    }

    public int getDegree(VertexObj<V, E> vertex) {
        int degree = 0;
        for (EdgeObj<V, E> edge : secEdge) {
            if (edge.endVertex1.equals(vertex) || edge.endVertex2.equals(vertex)) {
                degree++;
            }
        }
        return degree;
    }

    

}
