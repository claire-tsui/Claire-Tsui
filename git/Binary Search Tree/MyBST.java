/**
 * Name: Claire Tsui
 * Email: ctsui@ucsd.edu
 * PID: A16920940
 * Sources Used: PA8 Write-up
 * 
 * This file extends Comparable and complete the functionality 
 * of each method in MyBST class.
 */

import java.util.ArrayList;

/**
 * This class sets up behavior of each method's instruction.
 * 
 * Instance variables:
 * root - root of the BST
 * size - size of the BST 
 */
public class MyBST<K extends Comparable<K>, V> {

    MyBSTNode<K, V> root = null;
    int size = 0;

    /** 
     * Return the size of the BST
     * 
     * @return size of the BST
     */
    public int size() {
        return size;
    }

    /** 
     * Insert value into the BST at key
     * 
     * @param key location where the value is inserted
     * @param value value inserted
     * @return value that's removed due to the insertion,
     * if no value is removed, return null
     */
    public V insert(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        MyBSTNode<K, V> curr = root;
        while (curr.getRight() != null && curr.getLeft() != null) {
            if (curr.getKey().compareTo(key) == 0) {
                break;
            }
            if (curr.getKey().compareTo(key) < 0) {
                curr = curr.getRight();
            }
            else {
                curr = curr.getLeft();
            }
        }
        if (curr.getKey().compareTo(key) == 0) {
            V orig = curr.getValue();
            curr.setValue(value);
            return orig;
        }
        else if (curr.getKey().compareTo(key) < 0) {
            MyBSTNode<K, V> right = new MyBSTNode<>(key, value, curr);
            curr.setRight(right);
            size++;
            return null;
        }
        else {
            MyBSTNode<K, V> left = new MyBSTNode<>(key, value, curr);
            curr.setLeft(left);
            size++;
            return null;
        }
    }

    /** 
     * Search the corresponding key in the BST
     * 
     * @param key argument key 
     * @return value corresponding to the key
     */
    public V search(K key) {
        if (key == null) {
            return null;
        }
        else {
            MyBSTNode<K, V> curr = root;
            V value = curr.getValue();
            while (curr != null) {
                if (curr.getKey().compareTo(key) == 0) {
                    value = curr.getValue();
                    return value;
                }
                else if (curr.getKey().compareTo(key) < 0) {
                    curr = curr.getRight();
                }
                else if (curr.getKey().compareTo(key) > 0){
                    curr = curr.getLeft();
                }
            }
            return null;
        }
    }

    /** 
     * Remove the element corresponding to the key
     * 
     * @param key location of where the value is removed
     * @return value that's removed
     */
    public V remove(K key) {
        if (this.search(key) == null || key == null) {
            return null;
        }
        else {
            MyBSTNode<K, V> curr = root;
            V removed = this.search(key);
            while (curr != null) {
                if (curr.getKey().compareTo(key) == 0) {
                    break;
                }
                else if (curr.getKey().compareTo(key) < 0) {
                    curr = curr.getRight();
                }
                else if (curr.getKey().compareTo(key) > 0){
                    curr = curr.getLeft();
                }
            }
            if (curr != null) {
                // node is leaf
                if (curr.getLeft() == null && curr.getRight() == null) {
                    if (curr.getParent() == null) {
                        root = null;
                    }
                    else if (curr.getParent().getLeft() == curr) {
                        curr.getParent().setLeft(null);
                    }
                    else {
                        curr.getParent().setRight(null);
                    }
                }
                // node with only right child
                else if (curr.getLeft() == null) {
                    if (curr.getParent() == null) {
                        root = curr.getRight();
                    }
                    else if (curr.getParent().getLeft() == curr) {
                        curr.getParent().setLeft(curr.getRight());
                    }
                    else {
                        curr.getParent().setRight(curr.getRight());
                    }
                }   
                // node with only left child
                else if (curr.getRight() == null) {
                    if (curr.getParent() == null) {
                        root = curr.getLeft();
                    }
                    else if (curr.getParent().getLeft() == curr) {
                        curr.getParent().setLeft(curr.getLeft());
                    }
                    else {
                        curr.getParent().setRight(curr.getLeft());
                    }
                }
                // node with two children
                else {
                    MyBSTNode<K, V> set = curr.successor();
                    if (curr.getParent() == null) {
                        root = set;
                    }
                    else if (curr.getParent().getLeft() == curr) {
                        curr.getParent().setLeft(set);
                    }
                    else {
                        curr.getParent().setRight(set);
                    }
                }
                size--;
            }
            return removed;
        }
    }

    /** 
     * The traversal of the BST in order
     * 
     * @return arraylist with elements in the BST in order
     */
    public ArrayList<MyBSTNode<K, V>> inorder() {
        if (root == null) {
            return new ArrayList<>(0);
        }
        else {
            MyBSTNode<K, V> min = root;
            while (min.getLeft() != null) {
                min = min.getLeft();
            }
            ArrayList<MyBSTNode<K, V>> BSTlist = new ArrayList<>();
            BSTlist.add(min);
            while (min.successor() != null) {
                min = min.successor();
                BSTlist.add(min);
            }
            return BSTlist;
        }
    }

    static class MyBSTNode<K, V> {
        private static final String TEMPLATE = "Key: %s, Value: %s";
        private static final String NULL_STR = "null";

        private K key;
        private V value;
        private MyBSTNode<K, V> parent;
        private MyBSTNode<K, V> left = null;
        private MyBSTNode<K, V> right = null;

        /**
         * Creates a MyBSTNode storing specified data
         *
         * @param key    the key the MyBSTNode will store
         * @param value  the data the MyBSTNode will store
         * @param parent the parent of this node
         */
        public MyBSTNode(K key, V value, MyBSTNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Return the key stored in the the MyBSTNode
         *
         * @return the key stored in the MyBSTNode
         */
        public K getKey() {
            return key;
        }

        /**
         * Set the key stored in the MyBSTNode
         *
         * @param newKey the key to be stored
         */
        public void setKey(K newKey) {
            this.key = newKey;
        }

        /**
         * Return data stored in the MyBSTNode
         *
         * @return the data stored in the MyBSTNode
         */
        public V getValue() {
            return value;
        }

        /**
         * Set the data stored in the MyBSTNode
         *
         * @param newValue the data to be stored
         */
        public void setValue(V newValue) {
            this.value = newValue;
        }

        /**
         * Return the parent
         *
         * @return the parent
         */
        public MyBSTNode<K, V> getParent() {
            return parent;
        }

        /**
         * Set the parent
         *
         * @param newParent the parent
         */
        public void setParent(MyBSTNode<K, V> newParent) {
            this.parent = newParent;
        }

        /**
         * Return the left child
         *
         * @return left child
         */
        public MyBSTNode<K, V> getLeft() {
            return left;
        }

        /**
         * Set the left child
         *
         * @param newLeft the new left child
         */
        public void setLeft(MyBSTNode<K, V> newLeft) {
            this.left = newLeft;
        }

        /**
         * Return the right child
         *
         * @return right child
         */
        public MyBSTNode<K, V> getRight() {
            return right;
        }

        /**
         * Set the right child
         *
         * @param newRight the new right child
         */
        public void setRight(MyBSTNode<K, V> newRight) {
            this.right = newRight;
        }

        /**
         * Return the successor of the node
         *
         * @return successor of the node
         */
        public MyBSTNode<K, V> successor() {
            MyBSTNode<K, V> curr = this;
            if (curr.getRight() != null) {
                curr = curr.getRight();
                while (curr.getLeft() != null) {
                    curr = curr.getLeft();
                }
                return curr;
            }
            else { 
                if (curr.getParent().getLeft() == curr) {
                    return curr.getParent();
                }
                else {
                    return curr.getParent().getParent();
                }
            }
        }

        /**
         * This method compares if two node objects are equal.
         *
         * @param obj The target object that the currect object compares to.
         * @return Boolean value indicates if two node objects are equal
         */
        public boolean equals(Object obj) {
            if (!(obj instanceof MyBSTNode))
                return false;

            MyBSTNode<K, V> comp = (MyBSTNode<K, V>) obj;

            return ((this.getKey() == null ? comp.getKey() == null :
                    this.getKey().equals(comp.getKey()))
                    && (this.getValue() == null ? comp.getValue() == null :
                    this.getValue().equals(comp.getValue())));
        }

        /**
         * This method gives a string representation of node object.
         *
         * @return "Key:Value" that represents the node object
         */
        public String toString() {
            return String.format(
                    TEMPLATE,
                    this.getKey() == null ? NULL_STR : this.getKey(),
                    this.getValue() == null ? NULL_STR : this.getValue());
        }
    }

}
