package ch.zhaw.ads;

import java.util.ArrayDeque;
import java.util.Stack;

public class TreeTraversal<T extends Comparable<T>> implements Traversal<T> {
    private TreeNode<T> root;

    public TreeTraversal(TreeNode<T> root) {
        this.root = root;
    }

    public void inorder(Visitor<T> vis) {
        Stack<TreeNode<T>> s = new Stack<>();

        var current = root;
        while (!s.isEmpty() || current != null) {
            if (current != null) {
                s.push(current);
                current = current.left;
            } else {
                current = s.pop();
                vis.visit(current.getValue());
                current = current.right;
            }
        }
    }

    public void postorder(Visitor<T> vis) {
        Stack<TreeNode<T>> s = new Stack<>();
        s.push(root);

        while (!s.isEmpty()) {
            TreeNode<T> next = s.peek();

            if (next.right == root || next.left == root || (next.left == null && next.right == null)) {
                s.pop();
                vis.visit(next.getValue());
                root = next;
            } else {
                if (next.right != null) s.push(next.right);
                if (next.left != null) s.push(next.left);
            }
        }
    }

    public void preorder(Visitor<T> vis) {
        Stack<TreeNode<T>> s = new Stack<>();

        vis.visit(root.getValue());
        s.push(root);

        while (!s.isEmpty()) {
            TreeNode<T> next = s.peek();

            if (next.right == root || next.left == root || (next.left == null && next.right == null)) {
                s.pop();
                root = next;
            } else {
                if (next.left != null) {
                    vis.visit(next.left.getValue());
                    s.push(next.left);
                }
                if (next.right != null) {
                    vis.visit(next.right.getValue());
                    s.push(next.right);
                }
            }
        }
    }

    @Override
    public void levelorder(Visitor<T> vistor) {
        var q = new ArrayDeque<TreeNode<T>>();
        var node = root;
        if (node != null) q.add(node);
        while (!q.isEmpty()) {
            node = q.poll();
            vistor.visit(node.getValue());
            if (node.left != null) q.add(node.left);
            if (node.right != null) q.add(node.right);
        }
    }

    @Override
    public void interval(T min, T max, Visitor<T> vistor) {

    }
}
