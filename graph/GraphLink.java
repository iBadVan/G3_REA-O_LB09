package graph;

import java.util.*;
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

    public void insertEdgeWeight(E verOri, E verDes, int weight) {
        Vertex<E> vertexOri = searchVertex(verOri);
        Vertex<E> vertexDes = searchVertex(verDes);

        if (vertexOri != null && vertexDes != null) {
            Edge<E> edgeOri = new Edge<>(vertexDes, weight);
            Edge<E> edgeDes = new Edge<>(vertexOri, weight);

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

    public void bfs(E v) {
        Vertex<E> startVertex = searchVertex(v);
        if (startVertex == null) return;

        Queue<Vertex<E>> queue = new LinkedList<>();
        startVertex.visited = true;
        queue.add(startVertex);

        System.out.println("Recorrido en anchura comenzando en: " + v);

        while (!queue.isEmpty()) {
            Vertex<E> currentVertex = queue.poll();
            System.out.println(currentVertex.getData());

            for (Edge<E> edge : currentVertex.listAdj) {
                Vertex<E> adjVertex = edge.getRefDest();
                if (!adjVertex.visited) {
                    adjVertex.visited = true;
                    queue.add(adjVertex);
                }
            }
        }
    }

    public List<E> bfsPath(E v, E z) {
        Vertex<E> startVertex = searchVertex(v);
        Vertex<E> endVertex = searchVertex(z);

        if (startVertex == null || endVertex == null) return null;

        Queue<Vertex<E>> queue = new LinkedList<>();
        Map<Vertex<E>, Vertex<E>> previous = new HashMap<>();
        startVertex.visited = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Vertex<E> currentVertex = queue.poll();

            if (currentVertex.equals(endVertex)) {
                List<E> path = new ArrayList<>();
                for (Vertex<E> at = endVertex; at != null; at = previous.get(at)) {
                    path.add(at.getData());
                }
                Collections.reverse(path);
                return path;
            }

            for (Edge<E> edge : currentVertex.listAdj) {
                Vertex<E> adjVertex = edge.getRefDest();
                if (!adjVertex.visited) {
                    adjVertex.visited = true;
                    queue.add(adjVertex);
                    previous.put(adjVertex, currentVertex);
                }
            }
        }

        return null;
    }

    public boolean isConexo() {
        if (listVertex.size() == 0) return true;

        dfs(listVertex.get(0).getData());

        for (Vertex<E> vertex : listVertex) {
            if (!vertex.visited) {
                return false;
            }
        }

        return true;
    }

    public Stack<E> Dijkstra(E v, E w) {
        Vertex<E> startVertex = searchVertex(v);
        Vertex<E> endVertex = searchVertex(w);

        if (startVertex == null || endVertex == null) return null;

        Map<Vertex<E>, Integer> distances = new HashMap<>();
        Map<Vertex<E>, Vertex<E>> previous = new HashMap<>();
        PriorityQueue<Vertex<E>> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (Vertex<E> vertex : listVertex) {
            distances.put(vertex, Integer.MAX_VALUE);
            previous.put(vertex, null);
        }

        distances.put(startVertex, 0);
        pq.add(startVertex);

        while (!pq.isEmpty()) {
            Vertex<E> currentVertex = pq.poll();

            if (currentVertex.equals(endVertex)) break;

            for (Edge<E> edge : currentVertex.listAdj) {
                Vertex<E> neighbor = edge.getRefDest();
                int newDist = distances.get(currentVertex) + edge.getWeight();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, currentVertex);
                    pq.add(neighbor);
                }
            }
        }

        Stack<E> path = new Stack<>();
        for (Vertex<E> at = endVertex; at != null; at = previous.get(at)) {
            path.push(at.getData());
        }

        return path;
    }

    public String toString() {
        return this.listVertex.toString();
    }

    public int getDegree(Vertex<E> vertex) {
        int degree = 0;
        for (Edge<E> edge : vertex.listAdj) {
            degree++;
        }
        return degree;
    }

    
    
}
