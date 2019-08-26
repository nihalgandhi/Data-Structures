import java.util.*;

class SNode {
	public SNode left, right;
	public int val;

	public SNode(int val) {
		this.val = val;
		left = null;
		right = null;
	}
}

class STree {
	SNode root;

	public void create() {
		root = null;
	}

	public void insert(int val) {
		root = insert(root, val);
	}
	
	private SNode insert(SNode root, int val) {
		if (root == null) 
			root = new SNode(val);
		else if (val < root.val) 
			root.left = insert(root.left, val);
		else if (val > root.val)
			root.right = insert(root.right, val);

		return splay(root, val);
	}

	private SNode rightRotate(SNode root) {
		SNode newRoot = root.left;
		root.left = newRoot.right;
		newRoot.right = root;
		return newRoot;
	}

	private SNode leftRotate(SNode root) {
		SNode newRoot = root.right;
		root.right = newRoot.left;
		newRoot.left = root;
		return newRoot;
	}
	
	private SNode splay(SNode root, int val) {
		if (root == null || root.val == val)
			return root;

		if (root.val > val) {
			if (root.left == null)
				return root;
			else if (root.left.val > val) {
				root.left.left = splay(root.left.left, val);
				root = rightRotate(root);
			} 
			else if (root.left.val < val) {
				root.left.right = splay(root.left.right, val);
				if (root.left.right != null)
					root.left = leftRotate(root.left);
			}

			return (root.left == null) ? root : rightRotate(root);
		} 
		else {
			if (root.right == null)
				return root;
			else if (root.right.val > val) {
				root.right.left = splay(root.right.left, val);
				if (root.right.left != null)
					root.right = rightRotate(root.right);
			} 
			else if (root.right.val < val) {
				root.right.right = splay(root.right.right, val);
				root = leftRotate(root);
			}

			return (root.right == null) ? root : leftRotate(root);
		}
	}

	public void delete(int val) {
		root = delete(root, val);
	}
	
	private SNode delete(SNode root, int val) {
		if (root == null)
			return root;

		root = splay(root, val);

		if (val == root.val) {
			if (root.left == null)
				return root.right;
			else if (root.right == null)
				return root.left;

			SNode child = root.left;
			while(child.right != null) {
				child = child.right;
			}
			root.val = child.val;
			root.left = delete(root.left, child.val);
		}
		return root;

	}

	public boolean search(int val) {
		root = search(root, val);
		if(root == null)
			return false;
		return root.val == val;
	}

	private SNode search(SNode root, int val) {
		return splay(root, val);
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
		Queue<SNode> q = new LinkedList<>();
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
				SNode node = q.remove();
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
	
	private void inorderPrint(SNode root) {
		if(root == null) return;
		inorderPrint(root.left);
		System.out.print(root.val + " ");
		inorderPrint(root.right);
	}

}

public class SplayTree {
	public static void main(String[] args) {
		STree spt = new STree();
		spt.create();
//		long start = System.nanoTime();
		spt.insert(1);spt.insert(2);spt.insert(3);spt.insert(4);
		spt.insert(5);spt.insert(6);spt.insert(7);spt.insert(8);
		spt.insert(9);spt.insert(10);spt.insert(11);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
		spt.printTree();
//		long start = System.nanoTime();
//		spt.search(20);spt.search(4);spt.search(17);spt.search(1);
//		spt.search(6);spt.search(14);spt.search(11);spt.search(5);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
//		long start = System.nanoTime();
		spt.delete(11);spt.delete(10);spt.delete(9);spt.delete(8);
		spt.delete(7);spt.delete(6);spt.delete(5);spt.delete(4);
//		long end = System.nanoTime();
//		System.out.println("Insert time: " + (end - start));
		spt.printTree();
	}
}