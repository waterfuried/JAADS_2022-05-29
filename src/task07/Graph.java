package task07;

class Graph {
    private Vertex[] vertexList;
    private int[][] adjMat, weight;
    private int size;
    private Stack stack;
    private Queue queue;

    public Graph() {
        final int MAX_VERTS = 32;
        stack = new Stack(MAX_VERTS);
        queue = new Queue(MAX_VERTS);
        vertexList = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        weight = new int[MAX_VERTS][MAX_VERTS];
        size = 0;
        for (int i = 0; i < MAX_VERTS; i++) {
            for (int j = 0; j < MAX_VERTS; j++) {
                adjMat[i][j] = 0;
                weight[i][j] = 0;
            }
        }
    }

    // добавление вершины
    public void addVertex(char label) {
        if (size == vertexList.length) {
            Vertex[] tmp = new Vertex[size<<1];
            System.arraycopy(vertexList, 0, tmp, 0, vertexList.length);
            vertexList = tmp;
        }
        vertexList[size++] = new Vertex(label);
    }

    // добавление ребра
    public void addEdge(int start, int end, int ... weight) {
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
        if (weight != null && weight.length > 0) {
            if (weight[0] == 0) adjMat[start][end] = 0;
            this.weight[start][end] = weight[0];
            if (weight.length > 1) {
                if (weight[1] == 0) adjMat[end][start] = 0;
                this.weight[end][start] = weight[1];
            }
        }
    }

    public char getLabel(int val) { return (char)((int)'A'+val); }

    // отображение вершины
    public void displayVertex(int vertex, int showDirection) {
        if (showDirection == -1) System.out.print("<-");
        System.out.println(vertexList[vertex].label+(showDirection == 1 ? "->" : ""));
    }

    // поиск соседней не посещенной вершины
    private int getAdjUnvisitedVertex(int ver) {
        for (int i = 0; i < size; i++) {
            if (adjMat[ver][i] == 1 && !vertexList[i].wasVisited) {
                return i;
            }
        }
        return -1;
    }

    // обход графа в глубину, DFS = Depth-First Search
    public void dfs() {
        vertexList[0].wasVisited = true;
        displayVertex(0, 1);
        stack.push(0);
        while (stack.hasElements()) {
            int v = getAdjUnvisitedVertex(stack.peek());
            if (v == -1) {
                stack.pop();
                if (stack.hasElements())
                    displayVertex(stack.peek(), -1);
            } else {
                vertexList[v].wasVisited = true;
                displayVertex(v, 1);
                stack.push(v);
            }
        }
        for (int i = 0; i < size; i++) {
            vertexList[i].wasVisited = false;
        }
    }

    // обход графа в ширину, BFS = Breadth-First search
    public void bfs(int start, int end) {
        vertexList[start].wasVisited = true;
        queue.insert(start);
        int[] p = new int[size]; // массив для пути
        p[start] = -1;
        int v2;
        while (!queue.isEmpty()) {
            int v1 = queue.remove();
            do {
                v2 = getAdjUnvisitedVertex(v1);
                if (v2 != -1) {
                    vertexList[v2].wasVisited = true;
                    queue.insert(v2);
                    // движение происходит по ребрам - для каждой вершины пути
                    // запоминать соседнюю (конец и начало участка)
                    p[v2] = v1;
                }
            } while (v2 != end && v2 != -1);
        }
        // поскольку стек все равно уже создан при создании объекта класса,
        // а используется он только в DFS, стоит использовать его вместо
        // выделения памяти под новый стек
        stack.clear();
        // восстановить путь от конца к началу
        v2 = end;
        while (v2 >= 0) {
            stack.push(v2);
            v2 = p[v2];
        }
        // и вывести его в нормальном (от начала к концу) виде
        if (stack.hasElements())
            stack.display(true,
                    "Кратчайший путь от "+getLabel(start)+" до "+getLabel(end)+":\n", "-> ");
        for (int i = 0; i < size; i++)
            vertexList[i].wasVisited = false;
    }
}