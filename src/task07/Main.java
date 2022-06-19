package task07;

/*
  Урок 7. Графы
    1. Реализовать программу, в которой задается граф из 10 вершин.
       Задать ребра и найти кратчайший путь с помощью поиска в ширину.
*/
public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();

        int N_VERT = 12;
        char c = 'A';
        for (int i = 0; i < N_VERT; i++) graph.addVertex(c++);

        // по графу с урока 7
        graph.addEdge(0, 2, 1); //AC
        graph.addEdge(1, 3, 3); //BD
        graph.addEdge(1, 4, 2); //BE
        graph.addEdge(2, 3, 2); //CD
        graph.addEdge(2, 5, 5); //CF
        graph.addEdge(3, 4, 1); //DE
        graph.addEdge(3, 6, 7); //DG
        graph.addEdge(6, 7, 2); //GH
        graph.addEdge(4, 8, 4); //EI
        graph.addEdge(5, 9, 8); //FJ
        graph.addEdge(6, 9, 9); //GJ
        graph.addEdge(8, 10, 1); //IK
        graph.addEdge(9, 10, 2); //JK
        graph.bfs(0,10);

        /* модификация графа с городами из методички
        graph.addEdge(0, 1, 300); //AB
        graph.addEdge(1, 2, 150); //BC
        graph.addEdge(2, 3, 150); //CD
        graph.addEdge(0, 4, 200); //AE
        graph.addEdge(4, 5, 100); //EF
        graph.addEdge(5, 6, 550); //FG
        graph.addEdge(6, 3, 700); //GD
        graph.addEdge(0, 7, 250); //AH
        graph.addEdge(7, 8, 200); //HI
        graph.addEdge(8, 9, 100); //IJ
        graph.addEdge(9, 3, 350); //JD

        graph.addEdge(2, 5, 400); //СF
        graph.addEdge(2, 10, 200); //CK
        graph.addEdge(2, 11, 200); //CL
        graph.bfs(0,3);*/
    }
}