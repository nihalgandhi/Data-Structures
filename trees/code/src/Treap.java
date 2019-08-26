import java.util.*;

class TNode {
	public int val;
	public int priority;
	public TNode left, right;

	public TNode(int val) {
		this.val = val;
		this.priority = new Random().nextInt(100 - 1) + 1;
		this.left = null;
		this.right = null;
	}
}

class TreeHeap {

	TNode root;

	public void create() {
		root = null;
	}
	
	public void insert(int val) {
		root = insert(root, val);
	}

	private TNode insert(TNode node, int val) {
		if (node == null)
			return new TNode(val);
		if (node.val > val) {
			node.left = insert(node.left, val);
			if (node.priority < node.left.priority) {
				node = rightRotate(node);
			}
		} else {
			node.right = insert(node.right, val);
			if (node.priority < node.right.priority) {
				node = leftRotate(node);
			}
		}
		return node;

	}

	private TNode rightRotate(TNode root) {
		TNode newRoot = root.left;
		root.left = newRoot.right;
		newRoot.right = root;
		return newRoot;
	}

	private TNode leftRotate(TNode root) {
		TNode newRoot = root.right;
		root.right = newRoot.left;
		newRoot.left = root;
		return newRoot;
	}

	public boolean search(int val) {
		return search(root, val);
	}
	
	private boolean search(TNode node, int val) {
		if (node == null)
			return false;
		else if (node.val == val)
			return true;
		else if (val < node.val)
			return search(node.left, val);
		else
			return search(node.right, val);
	}

	public void delete(int val) {
		root = delete(root, val);
	}
	
	private TNode delete(TNode root, int val) {
		if (root == null)
			return root;
		if (val < root.val)
			root.left = delete(root.left, val);
		else if (val > root.val)
			root.right = delete(root.right, val);
		else {
			if (root.right != null || root.left != null) {
				if (root.left == null) {
					root = leftRotate(root);
					root.left = delete(root.left, val);
				} else if (root.right == null || (root.left.priority > root.right.priority)) {
					root = rightRotate(root);
					root.right = delete(root.right, val);
				} else {
					root = leftRotate(root);
					root.left = delete(root.left, val);
				}
			} else {
				return null;
			}
		}
		return root;
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
		Queue<TNode> q = new LinkedList<>();
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
				TNode node = q.remove();
				//System.out.println(node.priority);
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
	
	private void inorderPrint(TNode root) {
		if(root == null) return;
		inorderPrint(root.left);
		System.out.print(root.val + " ");
		inorderPrint(root.right);
	}

}

public class Treap {
	public static void main(String[] args) {
		TreeHeap trp = new TreeHeap();
		trp.create();
//		long start = System.nanoTime();
		trp.insert(1);trp.insert(2);trp.insert(3);trp.insert(4);
		trp.insert(5);trp.insert(6);trp.insert(7);trp.insert(8);
		trp.insert(9);trp.insert(10);trp.insert(11);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
		trp.printTree();
//		long start = System.nanoTime();
//		trp.search(20);trp.search(4);trp.search(17);trp.search(1);
//		trp.search(6);trp.search(14);trp.search(11);trp.search(5);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
//		long start = System.nanoTime();
		trp.delete(11);trp.delete(10);trp.delete(9);trp.delete(8);
		trp.delete(7);trp.delete(6);trp.delete(5);trp.delete(4);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
		trp.printTree();
	}
}