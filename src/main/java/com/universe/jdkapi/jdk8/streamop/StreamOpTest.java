package com.universe.jdkapi.jdk8.streamop;

import com.universe.jdkapi.jdk8.streamop.entity.Hobby;
import com.universe.jdkapi.jdk8.streamop.entity.Student;
import com.universe.thirdparty.fastjson.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamOpTest {

	private static List<Student> students = new ArrayList<>();

	private static List<Student> redundantStudent = new ArrayList<>();

	static {
		for (int count = 0; count < 5; count++) {
			students.add(new Student("student" + count, count, Arrays.asList(new Hobby("hobby" + count))));
			redundantStudent.add(new Student("student" + (count % 2), count % 2));
		}
	}

	/**
	 * Stream类提供了foreach方法用于迭代
	 */
	public static void printByUsingForeach() {
		// Stream提供了forEach方法来迭代流中的每个数据
		Random random = new Random();
		random.ints().limit(10).sorted().forEach(System.out::println);
	}

	/**
	 * 元素映射
	 */
	public static void elementMapping() {
		List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 7);

		List<Integer> newNumberList = numberList.stream().map(number -> number * number).distinct().collect(Collectors.toList());
		System.out.println(newNumberList);

		IntSummaryStatistics statistics = numberList.stream().mapToInt(ele -> ele).summaryStatistics();
		System.out.println("平均数为：" + statistics.getAverage());
		System.out.println("最大数为：" + statistics.getMax());
		System.out.println("最小数为：" + statistics.getMin());
		System.out.println("列表中元素之和为：" + statistics.getSum());
		System.out.println("列表中元素数量为：" + statistics.getCount());
	}

	/**
	 * 元素过滤
	 */
	public static void filterElement() {
		// filter方法可通过设置的条件进行过滤
		List<String> names = Arrays.asList("lyl", "", "lq", "zxq", "wm");
		List<String> filteredList =
						names.stream().filter(StringUtils::isNotEmpty).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		System.out.println(filteredList);
	}

	/**
	 * 使用collectors
	 */
	public static void useCollectors() {
		List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");

		Set<String> set = list.stream().collect(Collectors.toSet());
		System.out.println("转换为set集合：" + set);

		String mergedStr = list.stream().collect(Collectors.joining(","));
		System.out.println("元素归并后：" + mergedStr);

		Map<String, Integer> studentMap = students.stream().collect(Collectors.toMap(Student::getName, Student::getAge));
		System.out.println("转换为map:" + studentMap);

		// 重复key会报错，当重复时取最新值
		Map<String, Integer> redundantStudentMap =
						students.stream().collect(Collectors.toMap(Student::getName, Student::getAge, (oldValue, newValue) -> oldValue));
		System.out.println("转换重复key的map：" + redundantStudentMap);
	}

	/**
	 * 通过实例某个字段进行分组
	 */
	public static void useGroupingBy() {
		Map<String, List<Student>> map = students.stream()
			.collect(Collectors.groupingBy(Student::getName, LinkedHashMap::new, Collectors.toList()));
		System.out.println(JsonUtils.toPrettyJsonString(map));
	}

	/**
	 * 从一个流转换成另一个流
	 */
	public static void useFlatMap() {
		List<Hobby> allHobbies = students.stream().flatMap(stu -> stu.getHobbies().stream()).collect(Collectors.toList());
		System.out.println("提取后的hobbies:" + allHobbies);
	}

	/**
	 * 并行流操作，当数量少时操作性能好，数量多时操作性能差
	 */
	public static void parallelStream() {
		List<Integer> numberList = new ArrayList<>();
		for (int count = 0; count < 10000000; count++) {
			numberList.add(count);
		}

		long start = System.currentTimeMillis();
		long countBySequetial = numberList.stream().count();
		long end = System.currentTimeMillis();
		System.out.println("串行流统计花费的时间为：" + (end - start) + "ms，元素个数为：" + countBySequetial);

		start = System.currentTimeMillis();
		long countByParallel = numberList.stream().parallel().count();
		end = System.currentTimeMillis();
		System.out.println("并行流统计花费的时间为：" + (end - start) + "ms，元素个数为：" + countByParallel);
	}

	public static void main(String[] args) {
		useGroupingBy();
	}
}
