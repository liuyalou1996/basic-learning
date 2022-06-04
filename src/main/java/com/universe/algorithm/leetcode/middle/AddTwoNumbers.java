package com.universe.algorithm.leetcode.middle;

import com.universe.algorithm.leetcode.util.LinkedListUtils;
import com.universe.algorithm.leetcode.util.LinkedListUtils.ListNode;

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

	public ListNode calculate(ListNode first, ListNode second) {
		// 这里的头部节点用作临时节点
		ListNode headListNode = new ListNode();
		ListNode currentListNode = headListNode;

		int sum = 0, carry = 0, newListNodeVal = 0;
		while (first != null || second != null) {
			int firstVal = first == null ? 0 : first.val;
			int secondVal = second == null ? 0 : second.val;
			sum = firstVal + secondVal + carry;

			// carry为进位
			carry = sum / 10;
			newListNodeVal = sum % 10;
			currentListNode.next = new ListNode(newListNodeVal);

			currentListNode = currentListNode.next;
			if (first != null) {
				first = first.next;
			}
			if (second != null) {
				second = second.next;
			}
		}

		if (carry == 1) {
			currentListNode.next = new ListNode(1);
		}

		return headListNode.next;
	}

	public static void main(String[] args) {
		ListNode first = LinkedListUtils.fromArray(new int[] { 2, 4, 3 });
		ListNode second = LinkedListUtils.fromArray(new int[] { 5, 6, 4 });

		AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
		ListNode newListNode = addTwoNumbers.calculate(first, second);
		LinkedListUtils.forEach(newListNode);
	}
}
