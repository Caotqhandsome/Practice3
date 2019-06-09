package test;

public class HuffNode {
	private int data ;
	private int index ;
	private HuffNode left ;
	private HuffNode right ;
	
	public HuffNode(int data, int index) {
		this.data = data ;
		this.index = index ;
	}
	
	public int getData() {
		return data ;
	}
	
	public void setData(int data) {
		this.data = data ;
	}
	
	public int getIndex() {
		return index ;
	}
	
	public void setIndex(int index) {
		this.index = index ;
	}
	
	public HuffNode getLeft() {
		return left ;
	}
	
	public HuffNode getRight() {
		return right ;
	}
	
	public void setLeft(HuffNode left) {
		this.left = left ;
	}
	
	public void setRight(HuffNode right) {
		this.right = right ;
	}
}
