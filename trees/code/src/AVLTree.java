import java.util.*;

class AVLNode {
	public int val;
	public AVLNode left, right;
	public int leftHeight;
	public int rightHeight;
	public AVLNode(int val) {
		this.val = val;
		leftHeight = 0;
		rightHeight = 0;
		left = null;
		right = null;
	}
	
	public int getHeight() {
		return Math.max(leftHeight, rightHeight);
	}
}
class AVL {
	AVLNode root;
	
	public void create() {
		root = null;
	}
	
	public void insert(int val) {
		root = insert(root, val);
	}
	
	private AVLNode insert(AVLNode root, int val) {
		if(root == null)
			root = new AVLNode(val);
		else if(val < root.val) {
			root.leftHeight += 1;
			root.left = insert(root.left, val);
			root.leftHeight = 1 + root.left.getHeight();
		}
		else if(val > root.val) {
			root.rightHeight += 1;
			root.right = insert(root.right, val);
			root.rightHeight = 1 + root.right.getHeight();
		}
		return balanceTree(root);
	}
		
	public boolean search(int val) {
		return search(root, val);
	}
	
	private boolean search(AVLNode node, int val) {
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
	
	private AVLNode delete(AVLNode node, int val) {
		if(node == null)
			return node;
		
		if(val < node.val) {
			node.left = delete(node.left, val);
		}
		else if(val > node.val) {
			node.right = delete(node.right, val);
		}
		else {
			if(node.left == null)
				node = node.right;
			else if(node.right == null)
				node = node.left;
			else {
				AVLNode child = node.left;
				while(child.right != null) {
					child = child.right;
				}
				node.val = child.val;
				node.left = delete(node.left, child.val);
			}
		}
		
		if(node == null)
			return node;
		node.leftHeight = (node.left == null ? 0 : (1 + node.left.getHeight()));
		node.rightHeight = (node.right == null ? 0 : (1 + node.right.getHeight()));
		return balanceTree(node);
	}
	
	private AVLNode balanceTree(AVLNode root) {
		if (root == null)
			return null;
		int balance = root.leftHeight - root.rightHeight;
		if(balance > 1) {
			if(root.left.rightHeight > root.left.leftHeight) {
				root.left = leftRotate(root.left);
			}
			root = rightRotate(root);
		}
		else if(balance < -1) {
			if(root.right.leftHeight > root.right.rightHeight) {
				root.right = rightRotate(root.right);
			}
			root = leftRotate(root); 
		}
		return root;
	}
	
	private AVLNode rightRotate(AVLNode root) {
		AVLNode newRoot = root.left;
		root.left = newRoot.right;
		newRoot.right = root;
		root.leftHeight = (root.left == null ? 0 : (1 + root.left.getHeight()));
		newRoot.rightHeight = (newRoot.right == null ? 0 : (1 + newRoot.right.getHeight()));
		return newRoot;
	}
	
	private AVLNode leftRotate(AVLNode root) {
		AVLNode newRoot = root.right;
		root.right = newRoot.left;
		newRoot.left = root;
		root.rightHeight = (root.right == null ? 0 : (1 + root.right.getHeight()));
		newRoot.leftHeight = (newRoot.left == null ? 0 : (1 + newRoot.left.getHeight()));
		return newRoot;
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
		Queue<AVLNode> q = new LinkedList<>();
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
				AVLNode node = q.remove();
//				System.out.println(node.leftHeight + ", " + node.rightHeight);
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
	
	private void inorderPrint(AVLNode root) {
		if(root == null) return;
		inorderPrint(root.left);
		System.out.print(root.val + " ");
		inorderPrint(root.right);
	}
}
public class AVLTree {
	public static void main(String[] args) {
		AVL avl = new AVL();
		
		avl.create();
//		long start = System.nanoTime();
		avl.insert(1);avl.insert(2);avl.insert(3);avl.insert(4);
		avl.insert(5);avl.insert(6);avl.insert(7);avl.insert(8);
		avl.insert(9);avl.insert(10);avl.insert(11);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
		avl.printTree();
//		long start = System.nanoTime();
//		avl.search(20);avl.search(4);avl.search(17);avl.search(1);
//		avl.search(6);avl.search(14);avl.search(11);avl.search(5);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
//		long start = System.nanoTime();
		avl.delete(11);avl.delete(10);avl.delete(9);avl.delete(8);
		avl.delete(7);avl.delete(6);avl.delete(5);avl.delete(4);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
		avl.printTree();
	}
}
