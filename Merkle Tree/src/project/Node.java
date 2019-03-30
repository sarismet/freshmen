package project;


public class Node {


	public String value;
	public Node left = null;
	public Node right = null;
	public boolean check=true;
	public Node(String value) {
		this.value=value;
		
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getLeft() {
		// TODO Auto-generated method stub
		return this.left;
	}
	public Node getRight() {
		// TODO Auto-generated method stub
		return this.right;
	}
	public String getData() {
		// TODO Auto-generated method stub
		return this.value;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
	
	
}
