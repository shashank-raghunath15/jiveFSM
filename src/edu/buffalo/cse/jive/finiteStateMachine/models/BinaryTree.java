/**
 * 
 */
package edu.buffalo.cse.jive.finiteStateMachine.models;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class BinaryTree<T> implements Tree<T> {

	private Node<T> root;

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	@Override
	public void insert(T data) {
		insert(getRoot(), data);
	}

	private void insert(Node<T> node, T data) {

		if (node == null) {
			node = new Node<T>(data);
		} else {
			if (node.getLeft() == null) {
				insert(node.getLeft(), data);
			} else {
				insert(node.getRight(), data);
			}
		}

	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

		preOrder(root);

	}

	private void preOrder(Node<T> node) {
		// TODO Auto-generated method stub
		if (node == null)
			return;

		System.out.print(node.getData());
		preOrder(node.getLeft());
		preOrder(node.getRight());
	}

	@Override
	public void delete(T data) {
		// TODO Auto-generated method stub

	}

}
