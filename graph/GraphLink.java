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

    public boolean isPath() {
        int startEndCount = 0;
        for (Vertex<E> vertex : listVertex) {
            int degree = getDegree(vertex);
            if (degree == 1) {
                startEndCount++;
            } else if (degree != 2) {
                return false;
            }
        }
        return startEndCount == 2;
    }
    
    public boolean isCycle() {
        for (Vertex<E> vertex : listVertex) {
            if (getDegree(vertex) != 2) {
                return false;
            }
        }
        Vertex<E> firstVertex = listVertex.get(0);
        Vertex<E> lastVertex = listVertex.get(listVertex.size() - 1);
        return searchEdge(firstVertex.getData(), lastVertex.getData());
    }

    public boolean isWheel() {
        int n = listVertex.size();
        int centerCount = 0;
        int outerCount = 0;
        for (Vertex<E> vertex : listVertex) {
            int degree = getDegree(vertex);
            if (degree == n - 1) {
                centerCount++;
            } else if (degree == 3) {
                outerCount++;
            } else {
                return false;
            }
        }
        return centerCount == 1 && outerCount == n - 1;
    }
    
    public boolean isComplete() {
        int n = listVertex.size();
        for (Vertex<E> vertex : listVertex) {
            if (getDegree(vertex) != n - 1) {
                return false;
            }
        }
        return true;
    }

    public void printFormalRepresentation() {
        System.out.println("Vértices:");
        for (Vertex<E> vertex : listVertex) {
            System.out.println(vertex.getData());
        }

        System.out.println("Aristas:");
        for (Vertex<E> vertex : listVertex) {
            for (Edge<E> edge : vertex.listAdj) {
                System.out.println(vertex.getData() + " - " + edge.getRefDest().getData());
            }
        }
    }

    public void printAdjacencyList() {
        for (Vertex<E> vertex : listVertex) {
            System.out.print(vertex.getData() + ": ");
            for (Edge<E> edge : vertex.listAdj) {
                System.out.print(edge.getRefDest().getData() + " ");
            }
            System.out.println();
        }
    }

    public void printAdjacencyMatrix() {
        int n = listVertex.size();
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 0;
            }
        }

        for (Vertex<E> vertex : listVertex) {
            for (Edge<E> edge : vertex.listAdj) {
                int i = listVertex.indexOf(vertex);
                int j = listVertex.indexOf(edge.getRefDest());
                matrix[i][j] = 1;
                matrix[j][i] = 1;
            }
        }

        System.out.println("Matriz de Adyacencia:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isIsomorphic(GraphLink<E> otherGraph) {
        if (this.listVertex.size() != otherGraph.listVertex.size()) {
            return false; 
        }

        for (Vertex<E> vertex : this.listVertex) {
            Vertex<E> correspondingVertex = otherGraph.searchVertex(vertex.getData());
            if (correspondingVertex == null || vertex.listAdj.size() != correspondingVertex.listAdj.size()) {
                return false;
            }
        }

        return true;
    }

    public boolean isPlanar() {
        int n = listVertex.size();

        if (n < 5) {
            return true; 
        }

        if (n >= 5) {
            for (int i = 0; i < n - 4; i++) {
                for (int j = i + 1; j < n - 3; j++) {
                    for (int k = j + 1; k < n - 2; k++) {
                        for (int l = k + 1; l < n - 1; l++) {
                            for (int m = l + 1; m < n; m++) {
                                // Obtener los 5 vértices
                                Vertex<E> v1 = listVertex.get(i);
                                Vertex<E> v2 = listVertex.get(j);
                                Vertex<E> v3 = listVertex.get(k);
                                Vertex<E> v4 = listVertex.get(l);
                                Vertex<E> v5 = listVertex.get(m);

                                if (isCompleteSubgraph(v1, v2, v3, v4, v5)) {
                                    return false; 
                                }
                            }
                        }
                    }
                }
            }
        }

        if (n >= 6) {
            for (int i = 0; i < n - 5; i++) {
                for (int j = i + 1; j < n - 4; j++) {
                    for (int k = j + 1; k < n - 3; k++) {
                        for (int l = k + 1; l < n - 2; l++) {
                            for (int m = l + 1; m < n - 1; m++) {
                                for (int o = m + 1; o < n; o++) {
                                    // Obtener los 6 vértices
                                    Vertex<E> v1 = listVertex.get(i);
                                    Vertex<E> v2 = listVertex.get(j);
                                    Vertex<E> v3 = listVertex.get(k);
                                    Vertex<E> v4 = listVertex.get(l);
                                    Vertex<E> v5 = listVertex.get(m);
                                    Vertex<E> v6 = listVertex.get(o);

                                    if (isBipartiteSubgraph(v1, v2, v3, v4, v5, v6)) {
                                        return false;  
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true; 
    }

    private boolean isCompleteSubgraph(Vertex<E> v1, Vertex<E> v2, Vertex<E> v3, Vertex<E> v4, Vertex<E> v5) {
        return searchEdge(v1.getData(), v2.getData()) && searchEdge(v1.getData(), v3.getData()) && searchEdge(v1.getData(), v4.getData()) && searchEdge(v1.getData(), v5.getData()) &&
            searchEdge(v2.getData(), v3.getData()) && searchEdge(v2.getData(), v4.getData()) && searchEdge(v2.getData(), v5.getData()) &&
            searchEdge(v3.getData(), v4.getData()) && searchEdge(v3.getData(), v5.getData()) &&
            searchEdge(v4.getData(), v5.getData());
    }

    private boolean isBipartiteSubgraph(Vertex<E> v1, Vertex<E> v2, Vertex<E> v3, Vertex<E> v4, Vertex<E> v5, Vertex<E> v6) {
        return searchEdge(v1.getData(), v4.getData()) && searchEdge(v1.getData(), v5.getData()) && searchEdge(v1.getData(), v6.getData()) &&
            searchEdge(v2.getData(), v4.getData()) && searchEdge(v2.getData(), v5.getData()) && searchEdge(v2.getData(), v6.getData()) &&
            searchEdge(v3.getData(), v4.getData()) && searchEdge(v3.getData(), v5.getData()) && searchEdge(v3.getData(), v6.getData()) &&
            !searchEdge(v1.getData(), v2.getData()) && !searchEdge(v1.getData(), v3.getData()) &&
            !searchEdge(v4.getData(), v5.getData()) && !searchEdge(v4.getData(), v6.getData()) &&
            !searchEdge(v5.getData(), v6.getData());
    }

    public boolean isConnected() {
        if (listVertex.isEmpty()) {
            return true; 
        }
        
        dfs(listVertex.get(0).getData());

        for (Vertex<E> vertex : listVertex) {
            if (!vertex.visited) {
                return false; 
            }
        }

        return true;
    }

    public boolean isAutocomplementary() {
        GraphLink<E> complementGraph = this.getComplementGraph();

        return this.isIsomorphic(complementGraph);
    }

    private GraphLink<E> getComplementGraph() {
        GraphLink<E> complementGraph = new GraphLink<>();

        for (Vertex<E> vertex : listVertex) {
            complementGraph.insertVertex(vertex.getData());
        }

        for (Vertex<E> vertex : listVertex) {
            for (Vertex<E> otherVertex : listVertex) {
                if (!vertex.equals(otherVertex) && !searchEdge(vertex.getData(), otherVertex.getData())) {
                    complementGraph.insertEdge(vertex.getData(), otherVertex.getData());
                }
            }
        }

        return complementGraph;
    }


}
