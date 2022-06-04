package com.universe.algorithm.leetcode.simple;

import com.universe.algorithm.leetcode.util.LinkedListUtils;
import com.universe.algorithm.leetcode.util.LinkedListUtils.ListNode;

/**
 * https://leetcode.cn/problems/reverse-linked-list/
 * 反转链表
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 * @author 刘亚楼
 * @date 2022/6/4
 */
public class ReverseLinkedList {

	public ListNode reverseList(ListNode head) {
		ListNode newHead = null, temp = null;

		ListNode current = head;
		while (current != null) {
			temp = current;
			current = current.next;

			temp.next = newHead;
			newHead = temp;
		}

		return newHead;
	}

	public static void main(String[] args) {
		int[] input = { 1, 2, 3, 4, 5, 6 };
		ListNode head = LinkedListUtils.fromArray(input);

		ReverseLinkedList reverseLinkedList = new ReverseLinkedList();
		ListNode reversed = reverseLinkedList.reverseList(head);
		LinkedListUtils.forEach(reversed);
	}
}
