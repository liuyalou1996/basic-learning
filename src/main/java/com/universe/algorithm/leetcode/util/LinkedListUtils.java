package com.universe.algorithm.leetcode.util;

/**
 * @author 刘亚楼
 * @date 2022/6/5
 */
public class LinkedListUtils {

	public static ListNode fromArray(int[] array) {
		ListNode head = new ListNode();

		ListNode current = head;
		for (int element : array) {
			current.next = new ListNode(element);
			current = current.next;
		}

		return head.next;
	}

	public static void forEach(ListNode head) {
		ListNode current = head;
		while (current != null) {
			if (current.next != null) {
				System.out.print(current.val + " -> ");
			} else {
				System.out.print(current.val);
			}
			current = current.next;
		}
	}

	public static class ListNode {

		public int val;
		public ListNode next;

		public ListNode() {
		}

		public ListNode(int val) {
			this.val = val;
		}

		public ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}
}
