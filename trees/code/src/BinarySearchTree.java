import java.util.*;

class TreeNode {
	public int val;
	public TreeNode left, right;
	public TreeNode(int val) {
		this.val = val;
		left = null;
		right = null;
	}
}
class BST {
	TreeNode root;
	
	public void create() {
		root = null;
	}
	
	public void insert(int val) {
		root = insert(root, val);
	}
	
	private TreeNode insert(TreeNode root, int val) {
		if(root == null)
			root = new TreeNode(val);
		else if(val < root.val)
			root.left = insert(root.left, val);
		else if(val > root.val)
			root.right = insert(root.right, val);
		return root;
	}
	
	public boolean search(int val) {
		return search(root, val);
	}
	
	private boolean search(TreeNode node, int val) {
		if(node == null)
			return false;
		else if(node.val == val)
			return true;
		else if(val < node.val) 
			return search(node.left, val);
		else
			return search(node.right, val);
	}
	
	public void delete(int val) {
		root = delete(root, val);
	}
	
	private TreeNode delete(TreeNode node, int val) {
		if(node == null)
			return null;
		
		if(val < node.val)
			node.left = delete(node.left, val);
		else if(val > node.val)
			node.right = delete(node.right, val);
		else {
			if(node.left == null)
				node = node.right;
			else if(node.right == null)
				node = node.left;
			else {
				TreeNode child = node.left;
				while(child.right != null) {
					child = child.right;
				}
				node.val = child.val;
				node.left = delete(node.left, child.val);
			}
		}
		return node;
	}
	
	public void printTree() {
		System.out.println("Inorder:");
		if(root == null)
			System.out.print("Tree is Empty");
		else
			inorderPrint(root);
		System.out.println();
		System.out.println("Level Order:");
		levelOrderPrint();
		
	}
	
	private void levelOrderPrint() {
		List<List<String>> tree = new ArrayList<>();
		List<String> temp = new ArrayList<>();
		Queue<TreeNode> q = new LinkedList<>();
		if(root == null) {
			System.out.println("Tree is Empty");
			return;
		}
		q.add(root);
		temp.add(String.valueOf(root.val));
		tree.add(new ArrayList<>(temp));
		while(true) {
			temp.clear();
			int size = q.size();
			for(int i = 0; i < size; i++) {
				TreeNode node = q.remove();
				if(node.left != null) {
					q.add(node.left);
					temp.add(String.valueOf(node.left.val));
				}
				else {
					temp.add("null");
				}
				
				if(node.right != null) {
					q.add(node.right);
					temp.add(String.valueOf(node.right.val));
				}
				else {
					temp.add("null");
				}
			}
			if(!q.isEmpty())
				tree.add(new ArrayList<>(temp));
			else
				break;
		}
		System.out.println(tree);
	}
	
	private void inorderPrint(TreeNode root) {
		if(root == null) return;
		inorderPrint(root.left);
		System.out.print(root.val + " ");
		inorderPrint(root.right);
	}
}
public class BinarySearchTree {
	public static void main(String[] args) {
		BST bst = new BST();
		
		bst.create();
//		long start = System.nanoTime();
		bst.insert(1);bst.insert(2);bst.insert(3);bst.insert(4);
		bst.insert(5);bst.insert(6);bst.insert(7);bst.insert(8);
		bst.insert(9);bst.insert(10);bst.insert(11);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
		bst.printTree();
//		long start = System.nanoTime();
//		bst.search(11);bst.search(10);bst.search(9);bst.search(8);
//		bst.search(7);bst.search(6);bst.search(5);bst.search(4);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
//		long start = System.nanoTime();
		bst.delete(11);bst.delete(10);bst.delete(9);bst.delete(8);
		bst.delete(7);bst.delete(6);bst.delete(5);bst.delete(4);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
		bst.printTree();
	}
}
