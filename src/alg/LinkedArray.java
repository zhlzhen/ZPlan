package alg;

public class LinkedArray {
	Node<Integer> pre = null;
	
	public int length;
	
	public LinkedArray(int[] array){
		
		Node<Integer> node  = new Node<Integer>(new Integer(array[0]));
		pre = node;
		length = length + 1;
		for (int i = 1; i < array.length; i++) {
			Node<Integer> temp = new Node<Integer>(new Integer(array[i]));
			node.next = temp;
			node = temp;
			length++;
		}
	}
	
	public void print(){
		if(this.pre != null){
			System.out.print(pre.item);
			while (pre.next != null) {
				System.out.print("->" + pre.next.item);
				pre = pre.next;
			}
			
			System.out.println();
		}
	}
}

 class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(E element) {
            this.item = element;
        }
    }