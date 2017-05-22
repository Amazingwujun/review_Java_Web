package com.cnblogs.lesson_53;

public class Link<T> {
	// 首节点
	private Node<T> root;
	// 尾节点
	private Node<T> last;
	private int size;

	public Link() {
	}

	public Link(Node<T> root) {
		this.root = root;
		size++;
	}

	/**
	 * 添加节点
	 * 
	 * @param node
	 */
	public void add(Node<T> node) {
		if (root == null) {
			root = node;
			size++;
			return;
		}

		Node<T> temp = root;

		while (temp.getNext() != null) {
			temp = temp.getNext();
		}

		temp.setNext(node);
		size++;
	}

	public int length() {
		if (root == null) {
			return 0;
		}

		int i = 1;

		Node<T> temp = root;
		while (temp.getNext() != null) {
			temp = temp.getNext();
			i++;
		}

		return i;
	}

	public Node<T> remove() {
		if (root == null) {
			return null;
		}

		Node<T> temp = null;

		if (size == 1) {
			temp = root;
			root = root.getNext();
			size--;
			return temp;
		}

		// 隐含条件size>1
		Node<T> preNode = root;
		Node<T> curNode = root.getNext();
		while (curNode.getNext() != null) {
			preNode = curNode;
			curNode = curNode.getNext();
		}

		//System.out.println(curNode.getData());
		preNode.setNext(null);
		size--;
		return curNode;
	}

	/*
	 * public Node<T> get() { if (root != null && size == 1) { size--; return
	 * root; }
	 * 
	 * Node<T> temp = root;
	 * 
	 * while(temp.getNext()!=null){ temp = temp.getNext(); }
	 * 
	 * }
	 */

	public static void main(String[] args) {
		Link<Integer> link = new Link<>(new Node<Integer>(1));

		link.add(new Node<Integer>(2));
		link.add(new Node<Integer>(3));
		/*System.out.println(link.size + "" + link.length());
		System.out.println(link.size);*/

		// System.out.println(link.length());
		int len = link.size;
		for (int i = 0; i < len; i++) {
			System.out.println(link.remove().getData());
		}
		
		System.out.println(link.root);
	}

}
