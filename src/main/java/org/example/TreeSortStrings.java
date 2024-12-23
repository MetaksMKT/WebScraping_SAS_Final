package org.example;

import java.util.ArrayList;
import java.util.List;

public class TreeSortStrings {
    static class Node {
        DataPoint data;
        Node left, right;

        Node(DataPoint data) {
            this.data = data;
            left = right = null;
        }
    }

    private Node root;

    // Insert method to insert a DataPoint object into the tree
    public void insert(DataPoint data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node root, DataPoint data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }

        // Compare by price numerically (as Double)
        if (data.price < root.data.price)
            root.left = insertRec(root.left, data);
        else if (data.price > root.data.price)
            root.right = insertRec(root.right, data);

        return root;
    }

    // In-order traversal to collect sorted data
    public void inOrderTraversal(Node node, List<DataPoint> sortedList) {
        if (node != null) {
            inOrderTraversal(node.left, sortedList);
            sortedList.add(node.data);
            inOrderTraversal(node.right, sortedList);
        }
    }

    // TreeSort method to sort a list of DataPoint objects
    public List<DataPoint> treeSort(List<DataPoint> arr) {
        for (DataPoint data : arr) {
            insert(data);
        }
        List<DataPoint> sortedList = new ArrayList<>();
        inOrderTraversal(root, sortedList);
        return sortedList;
    }
}
