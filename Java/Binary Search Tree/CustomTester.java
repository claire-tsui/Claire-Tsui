/**
 * Name: Claire Tsui
 * Email: ctsui@ucsd.edu
 * PID: A16920940
 * Sources Used: PA8 Write-up, MyBST.java
 * 
 * This file is a custom tester for MyBST.java.
 */

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * This class creates a test fixture and runs multiple tests on 
 * your implementation for MyBST.  
 */
public class CustomTester {

    MyBST<Integer, Integer> tree;
    MyBST<Integer, Integer> emptyTree;

    @Before
    public void setup() {
        MyBST.MyBSTNode<Integer, Integer> root =
                new MyBST.MyBSTNode<>(4, 1, null);
        MyBST.MyBSTNode<Integer, Integer> two =
                new MyBST.MyBSTNode<>(2, 1, root);
        MyBST.MyBSTNode<Integer, Integer> six =
                new MyBST.MyBSTNode<>(6, 1, root);
        MyBST.MyBSTNode<Integer, Integer> one =
                new MyBST.MyBSTNode<>(1, 2, two);
        MyBST.MyBSTNode<Integer, Integer> three =
                new MyBST.MyBSTNode<>(3, 30, two);
        MyBST.MyBSTNode<Integer, Integer> five =
                new MyBST.MyBSTNode<>(5, 50, six);

        this.tree = new MyBST<>();
        this.tree.root = root;
        root.setLeft(two);
        root.setRight(six);
        two.setLeft(one);
        two.setRight(three);
        six.setLeft(five);
        tree.size = 6;

        MyBST.MyBSTNode<Integer, Integer> emptyRoot = null;
        this.emptyTree = new MyBST<>();
        this.emptyTree.root = emptyRoot;
        emptyTree.size = 0;
    }

    /**
	 * Aims to test the successor method with both valid and invalid 
     * argument.
	 */
    @Test
    public void testSuccessor() {
        MyBST.MyBSTNode<Integer, Integer> root = tree.root;
        // no successor
        assertNull(root.getRight().successor());
        // left sub tree, right leaf
        assertEquals(root, root.getLeft().getRight().successor());
        // left sub tree, left leaf
        assertEquals(root.getLeft(), root.getLeft().getLeft().successor());
    }

    /**
	 * Aims to test the insert method with existing key
	 */
    @Test
    public void testInsert() {
        MyBST.MyBSTNode<Integer, Integer> root = tree.root;
        tree.insert(2, 300);
        assertEquals(300, root.getLeft().getValue().intValue());
        assertEquals(2, root.getLeft().getKey().intValue());
        assertEquals(root, root.getLeft().getParent());
        assertEquals(6, tree.size);
    }

    /**
	 * Aims to test the search method with null and non-existing key
	 */
    @Test
    public void testSearch() {
        assertNull(tree.search(null));
        assertEquals(50, tree.search(5).intValue());
    }

    /**
	 * Aims to test the remove method with non-existing key
	 */
    @Test
    public void testRemove() {
        assertNull(tree.remove(10));
    }

    /**
	 * Aims to test the inorder method with empty BST
	 */
    @Test
    public void testInorder() {
        // empty tree
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> expectedRes 
            = new ArrayList<>();
        assertEquals(expectedRes, emptyTree.inorder());
    }
}

