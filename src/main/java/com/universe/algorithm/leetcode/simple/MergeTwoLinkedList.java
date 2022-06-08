package com.universe.algorithm.leetcode.simple;

import com.universe.algorithm.leetcode.util.LinkedListUtils;
import com.universe.algorithm.leetcode.util.LinkedListUtils.ListNode;

/**
 * https://leetcode.cn/problems/merge-two-sorted-lists/
 *
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 *
 * 输入：l1 = [], l2 = []
 * 输出：[]
 *
 * 输入：l1 = [], l2 = [0]
 * 输出：[0]
 *
 * @author 刘亚楼
 * @date 2022/6/5
 */
public class MergeTwoLinkedList {

	public ListNode merge(ListNode first, ListNode second) {
		ListNode head = new ListNode();

		ListNode current = head;
		while (first != null && second != null) {
			if (first.val < second.val) {
				current.next = first;
				first = first.next;
			} else {
				current.next = second;
				second = second.next;
			}
			current = current.next;
		}

		current.next = first == null ? second : first;
		return head.next;
	}

	public static void main(String[] args) {
		ListNode first = LinkedListUtils.fromArray(new int[] { 1, 2, 3 });
		ListNode second = LinkedListUtils.fromArray(new int[] { 4, 5, 6 });

		MergeTwoLinkedList mergeTwoLinkedList = new MergeTwoLinkedList();
		ListNode result = mergeTwoLinkedList.merge(first, second);
		LinkedListUtils.forEach(result);
	}
}
