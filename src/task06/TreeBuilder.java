/*
    Урок 6. Деревья
    1. Создать и запустить программу для построения двоичного дерева.
       В цикле построить двести деревьев из 100 элементов.
       Данные, которыми необходимо заполнить узлы деревьев,
       представляются в виде чисел типа int.
       Число, которое попадает в узел, должно генерироваться
       случайным образом в диапазоне от -100 до 100.
    2. Проанализировать, какой процент созданных деревьев являются несбалансированными.
*/
package task06;

public class TreeBuilder {
    public static class Tree<T extends Comparable<T>> {
        public class TreeNode {
            private final T data;
            public TreeNode leftChild;
            public TreeNode rightChild;

            public TreeNode(T data) {
                this.data = data;
            }

            @Override public String toString() {
                return String.format("%s", data);
            }
        }

        private TreeNode root;

        public Tree() {
            root = null;
        }

        public boolean insert(T t) {
            TreeNode node = new TreeNode(t);
            if (root == null) {
                root = node;
                return true;
            } else {
                TreeNode current = root;
                TreeNode parent;
                while (true) {
                    parent = current;
                    if (t.compareTo(current.data) < 0) {
                        current = current.leftChild;
                        if (current == null) {
                            parent.leftChild = node;
                            return true;
                        }
                    } else if (t.compareTo(current.data) > 0){
                        current = current.rightChild;
                        if (current == null) {
                            parent.rightChild = node;
                            return true;
                        }
                    } else
                        return false;
                }
            }
        }

        public T find(T t) {
            TreeNode current = root;
            while (current.data.compareTo(t) != 0) {
                if (t.compareTo(current.data) < 0)
                    current = current.leftChild;
                else
                    current = current.rightChild;

                if (current == null)
                    return null;
            }
            return current.data;
        }

        String tabs;

        private int inOrderTravers(TreeNode current, String s) {
            int count = 0;
            if (current != null) {
                count++;
                logger.info(s+": "+current+" (heights of subtrees: "+
                        "left="+getHeight(current.leftChild)+
                        ", right="+getHeight(current.rightChild)+")");

                tabs+="\t";
                count += inOrderTravers(current.leftChild, tabs+"left child");
                tabs+="\t";
                count += inOrderTravers(current.rightChild, tabs+"right child");
            }
            if (tabs.length() > 0) tabs = tabs.substring(0, tabs.length()-1); else tabs = "";
            return count;
        }

        public void displayTree(boolean checkBalance) {
            tabs = "";
            int n = inOrderTravers(root, "root");
            logger.info("Tree has "+(n == 0 ? "no" : n)+" element"+(n == 1 ? "" : "s")+
                    ", tree height="+getHeight(root));
            if (checkBalance)
                logger.info("Tree is "+(checkBalance(root) ? "" : "not ")+"balanced");
        }

        /*
            двоичное дерево поиска (Binary Search Tree, BST) сбалансировано (по высоте),
            если
                количество его правых и левых уровней
                (или высоты двух поддеревьев для КАЖДОЙ ЕГО ВЕРШИНЫ)
            различается/различаются не более чем на 1.
         */
        private int getHeight(TreeNode t) {
            return t == null ? 0 : 1+Math.max(getHeight(t.leftChild), getHeight(t.rightChild));
        }

        private boolean checkBalance(TreeNode node) {
            if (node != null) {
                if (Math.abs(getHeight(node.leftChild)-getHeight(node.rightChild)) <= 1) return false;
                checkBalance(node.leftChild);
                checkBalance(node.rightChild);
            }
            return true;
        }

        public boolean delete(T t) {
            TreeNode curr = root;
            TreeNode prev = root;
            boolean isLeftChild = true;
            while (curr.data.compareTo(t) != 0) {
                prev = curr;
                if (t.compareTo(curr.data) < 0) {
                    isLeftChild = true;
                    curr = curr.leftChild;
                } else {
                    isLeftChild = false;
                    curr = curr.rightChild;
                }

                if (curr == null)
                    return false;
            }

            if (curr.leftChild == null && curr.rightChild == null) {
                if (curr == root) {
                    root = null;
                } else if (isLeftChild) {
                    prev.leftChild = null;
                } else {
                    prev.rightChild = null;
                }
            } else if (curr.rightChild == null) {
                if (isLeftChild) {
                    prev.leftChild = curr.leftChild;
                } else {
                    prev.rightChild = curr.leftChild;
                }
            } else if (curr.leftChild == null) {
                if (isLeftChild) {
                    prev.leftChild = curr.rightChild;
                } else {
                    prev.rightChild = curr.rightChild;
                }
            } else {
                TreeNode successor = getSuccessor(curr);
                if (curr == root) {
                    root = successor;
                } else if (isLeftChild) {
                    prev.leftChild = successor;
                } else {
                    prev.rightChild = successor;
                }
                successor.leftChild = curr.leftChild;
            }
            return true;
        }

        private TreeNode getSuccessor(TreeNode deleted) {
            TreeNode successorParent = deleted;
            TreeNode successor = deleted;
            TreeNode flag = deleted.rightChild;

            while (flag != null) {
                successorParent = successor;
                successor = flag;
                flag = flag.leftChild;
            }
            if (successor != deleted.rightChild) {
                successorParent.leftChild = successor.rightChild;
                successor.rightChild = deleted.rightChild;
            }
            return successor;
        }
    }

    static int randomInt(int min, int max) {
        return min + Math.round((float)Math.random()*(max - min));
    }

    static  { logger = new EventLogger(TreeBuilder.class.getName(), null); }

    static final EventLogger logger;

    public static void main(String[] args) {
        /*Tree<Integer> t0 = new Tree<>();
        t0.insert(10);
        t0.insert(9);
        t0.insert(20);
        t0.insert(5);
        t0.insert(15);
        t0.insert(25);
        t0.insert(6);
        t0.insert(12);
        t0.insert(7);
        t0.displayTree(true);*/
        final int NUM_TREES = 200, TREE_SIZE = 100;

        int nBalanced = 0;
        for (int i = 0; i < NUM_TREES; i++) {
            Tree<Integer> t = new Tree<>();
            logger.info("\nTree #"+(i+1)+":");
            for (int j = 0; j < TREE_SIZE; j++) {
                boolean inserted;
                do { inserted = t.insert(randomInt(-100, 100)); }
                while (!inserted);
            }

            t.displayTree(true);
            if (t.checkBalance(t.root)) nBalanced++;
            if (i < NUM_TREES-1) logger.info("");
        }
        logger.info(Math.round((100f*nBalanced)/NUM_TREES)+"% of trees are balanced");
        logger.closeHandlers();
    }
}