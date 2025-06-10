package graph;

import lista.ListLinked;

public class GraphLink<E> {
    protected ListLinked<Vertex<E>> listVertex;

    public GraphLink() {
        listVertex = new ListLinked<>();
    }

    public void insertVertex(E data) {
        Vertex<E> newVertex = new Vertex<>(data);
        listVertex.add(newVertex);
    }

    public void insertEdge(E verOri, E verDes) {
        Vertex<E> vertexOri = searchVertex(verOri);
        Vertex<E> vertexDes = searchVertex(verDes);

        if (vertexOri != null && vertexDes != null) {
            Edge<E> edgeOri = new Edge<>(vertexDes);
            Edge<E> edgeDes = new Edge<>(vertexOri);

            vertexOri.listAdj.add(edgeOri);
            vertexDes.listAdj.add(edgeDes);
        }
    }

    public Vertex<E> searchVertex(E v) {
        for (int i = 0; i < listVertex.size(); i++) {
            Vertex<E> vertex = listVertex.get(i);
            if (vertex.getData().equals(v)) {
                return vertex;
            }
        }
        return null;
    }

    public boolean searchEdge(E v, E z) {
        Vertex<E> vertexV = searchVertex(v);
        Vertex<E> vertexZ = searchVertex(z);

        if (vertexV != null && vertexZ != null) {
            for (Edge<E> edge : vertexV.listAdj) {
                if (edge.getRefDest().equals(vertexZ)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeVertex(E v) {
        Vertex<E> vertexToRemove = searchVertex(v);
        if (vertexToRemove != null) {
            for (Vertex<E> vertex : listVertex) {
                vertex.listAdj.removeIf(edge -> edge.getRefDest().equals(vertexToRemove));
            }
            listVertex.remove(vertexToRemove);
        }
    }

    public void removeEdge(E v, E z) {
        Vertex<E> vertexV = searchVertex(v);
        Vertex<E> vertexZ = searchVertex(z);

        if (vertexV != null && vertexZ != null) {
            vertexV.listAdj.removeIf(edge -> edge.getRefDest().equals(vertexZ));
            vertexZ.listAdj.removeIf(edge -> edge.getRefDest().equals(vertexV));
        }
    }

    public void dfs(E v) {
        Vertex<E> startVertex = searchVertex(v);
        if (startVertex == null) return;

        for (Vertex<E> vertex : listVertex) {
            vertex.visited = false;
        }

        dfsRecursive(startVertex);
    }

    private void dfsRecursive(Vertex<E> vertex) {
        if (vertex.visited) return;

        vertex.visited = true;
        System.out.println(vertex.getData());

        for (Edge<E> edge : vertex.listAdj) {
            Vertex<E> adjVertex = edge.getRefDest();
            dfsRecursive(adjVertex);
        }
    }

    public String toString() {
        return this.listVertex.toString();
    }
}
