package org.gnaik.tree;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeTransformProblem {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, new TreeNode(2, new TreeNode(4, null, null), new TreeNode(5, null, null)),
                new TreeNode(3, null, new TreeNode(6, null, null)));
        System.out.println("Input:" + root);
        BinaryTreeTransformProblem binaryTree = new BinaryTreeTransformProblem();
        binaryTree.calculate(root);
        System.out.println(root);
        System.out.println("======================================================");
        root = new TreeNode(1, new TreeNode(2, new TreeNode(4, null, null), new TreeNode(5, null, null)),
                new TreeNode(3, null, new TreeNode(6, null, null)));
        System.out.println("Input :" + root);
        binaryTree.calculateWithUpdate(root);
        System.out.println(root);
    }

    private int calculate(TreeNode treeNode) {
        if (treeNode == null)
            return 0;
        int leftSubTreeSum = calculate(treeNode.left);
        int rightSubTreeSum = calculate(treeNode.right);
        if (treeNode.right != null) {
            treeNode.right.data += treeNode.data + leftSubTreeSum;
        }
        return leftSubTreeSum + rightSubTreeSum + treeNode.data;
    }
//
//                    1
//                   / \
//                  2   3
//                 / \   \
//                4  5   6

//                    1
//                   / \
//                  2   15
//                 / \   \
//                4  11  9

//==================================================================================//


    private int calculateWithUpdate(TreeNode treeNode) {
        if (treeNode == null)
            return 0;
        int leftSubTreeSum = calculateWithUpdate(treeNode.left);
        calculateWithUpdate(treeNode.right);
        int updatedSum = 0;
        if (treeNode.right != null) {
            updatedSum = treeNode.right.data + treeNode.data + leftSubTreeSum;
            treeNode.right.data = updatedSum;
        }
        return leftSubTreeSum + treeNode.data + updatedSum;
    }

//
//                    1
//                   / \
//                  2   3
//                 / \   \
//                4  5   6

//                    1
//                   / \
//                  2   21
//                 / \   \
//                4  11  9
}

class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;

    public TreeNode(int data, TreeNode left, TreeNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    StringBuilder levelOrder(TreeNode root) {
        StringBuilder stringBuilder = new StringBuilder();
        if (root == null)
            return stringBuilder;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(null);
        while (!q.isEmpty()) {

            TreeNode curr = q.poll();

            if (curr == null) {
                if (!q.isEmpty()) {
                    q.add(null);
                    stringBuilder.append("\n");
                }
            } else {
                if (curr.left != null)
                    q.add(curr.left);

                if (curr.right != null)
                    q.add(curr.right);

                stringBuilder.append(curr.data + " ");
            }
        }
        return stringBuilder;
    }

    @Override
    public String toString() {
        return "Level order printing.\n" +levelOrder(this).toString() +"\n";
    }
}
