package com.universe.algorithm.leetcode.middle;

class Node {

	int value;
	Node next;

	public Node() {
	}

	public Node(int value) {
		this.value = value;
	}

	public Node(int value, Node next) {
		this.value = value;
		this.next = next;
	}
}

/**
 * https://leetcode.cn/problems/add-two-numbers/
 *
 * 给你两个非空 的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，
 * 并且每个节点只能存储一位数字。
 *
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 *
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 *
 * https://leetcode.cn/problems/add-two-numbers/solution/hua-jie-suan-fa-2-liang-shu-xiang-jia-by-guanpengc/
 * @author 刘亚楼
 * @date 2022/6/3
 */
public class AddTwoNumbers {

	public Node calculate(Node first, Node second) {
		// 这里的头部节点用作临时节点
		Node headNode = new Node();
		Node currentNode = headNode;

		int sum = 0, carry = 0, newNodeVal = 0;
		while (first != null || second != null) {
			int firstVal = first == null ? 0 : first.value;
			int secondVal = second == null ? 0 : second.value;
			sum = firstVal + secondVal + carry;

			// carry为进位
			carry = sum / 10;
			newNodeVal = sum % 10;
			currentNode.next = new Node(newNodeVal);

			currentNode = currentNode.next;
			if (first != null) {
				first = first.next;
			}
			if (second != null) {
				second = second.next;
			}
		}

		if (carry == 1) {
			currentNode.next = new Node(1);
		}

		return headNode.next;
	}

	public static Node transferFromArray(int[] array) {
		Node head = new Node();

		Node current = head;
		for (int element : array) {
			current.next = new Node(element);
			current = current.next;
		}

		return head.next;
	}

	public static void forEach(Node headNode) {
		Node current = headNode;
		while (current != null) {
			if (current.next != null) {
				System.out.print(current.value + " -> ");
			} else {
				System.out.print(current.value);
			}
			current = current.next;
		}
	}

	public static void main(String[] args) {
		Node first = transferFromArray(new int[] { 2, 4, 3 });
		Node second = transferFromArray(new int[] { 5, 6, 4 });

		AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
		Node newNode = addTwoNumbers.calculate(first, second);
		forEach(newNode);
	}
}
