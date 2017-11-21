import java.util.HashMap;
import java.util.LinkedList;

/**
 * Tree implementation with Node children
 * @author Daniel Griessler
 *
 */
public class Trie {
        /**
         * Node stores name, parent, and a linkedList of children
         * @author Daniel Griessler
         *
         */
        private class Node {
            private String element;             //String name of Node
            private Node parent;                //Link to parent
            private HashMap<String, Node> children;  //HashMap of children
            
            /**
             * Creates new node with given name and parent
             * @param n
             * @param p
             */
            Node(String n, Node p) {
                element = n;
                parent = p;
                children = new HashMap<String, Node>();
            }
            
            public Node addChild(Node n) {
                children.put(n.element, n);
                return n;
            }
            public String getElement() { return element; }
            public void setElement(String s) { element = s; }
            public Node getParent() { return parent; }
            public HashMap<String, Node> getAllChildren() { return children; }
            public Node getChild(String k) { return children.get(k); }
            public Node removeChild(String s) { 
                return children.remove(s); 
            }
            @Override
            public String toString() {
                return element;
            }
        }    
        private Node root;                      //base of the Tree
        
        /**
         * Creates new Tree with given name, parent set to null
         * @param n
         */
        Trie() {
            root = new Node("", null);
        }
        
        private String SENTINAL = "*";
        
        public Node getRoot() { return root; }
        
        public void addWord (String word) {
            Node start = root;
            for (int i = 0; i < word.length(); i++) {
                if (start.getChild(word.substring(i, i + 1)) == null) {
                    start = start.addChild(new Node(word.substring(i, i + 1), start));
                } else {
                    start = start.getChild(word.substring(i, i + 1));
                }
            }
            start.addChild(new Node(SENTINAL, start));
        }
        
        public boolean isInDictionary (String word) {
            Node start = root;
            for (int i = 0; i < word.length(); i++) {
                if (start.getChild(word.substring(i, i + 1)) != null) {
                    start = start.getChild(word.substring(i, i + 1));
                } else {
                    return false;
                }
            }
            if (start.getChild(SENTINAL) != null) {
                return true;
            } else {
                return false;
            }
        }
        
        public void postOrder(Node current, LinkedList<Node> collection) {
            for (Node child : current.getAllChildren().values()) {
                postOrder(child, collection);
            }
            collection.add(current);
        }
        
        public void compress() {
            LinkedList<Node> iteratorDontModify = new LinkedList<Node>();
            if (root.getAllChildren().size() != 0) {
                postOrder(root, iteratorDontModify);
            }
            for (Node current : iteratorDontModify) {
                if (current.getParent() != null && !current.getParent().equals(root) && current.getAllChildren().size() == 1 && current.getChild(SENTINAL) != null) {
                  current.getParent().getParent().removeChild(current.getParent().getElement());
                  current.getParent().setElement(current.getParent().getElement() + current.getElement());
                  current.getParent().getParent().getAllChildren().put(current.getParent().getElement(), current.getParent());
                  current.getParent().addChild(current.getChild(SENTINAL));
                  current.getParent().removeChild(current.getElement());
              }
            }
//            for (Node child : current.getAllChildren().values()) {
//                if (!child.getElement().equals(SENTINAL))
//                    compress(child);
//            }
//            if (current.getParent() != null && !current.getParent().equals(root) && current.getAllChildren().size() == 1 && current.getChild(SENTINAL) != null) {
//                current.getParent().getParent().removeChild(current.getParent().getElement());
//                current.getParent().setElement(current.getParent().getElement() + current.getElement());
//                current.getParent().getParent().getAllChildren().put(current.getParent().getElement(), current.getParent());
//                current.getParent().addChild(current.getChild(SENTINAL));
//                current.getParent().removeChild(current.getElement());
//            }
        }
        
        public void print(Node current) {
            System.out.println(current.toString());
            for (Node child : current.getAllChildren().values()) {
                print(child);
            }
        }
    }
