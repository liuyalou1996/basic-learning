package com.universe.thirdparty.geo;

import org.lionsoul.ip2region.xdb.Searcher;

/**
 * @author Nick Liu
 * @date 2022/7/27
 */
public class Ip2RegionExample {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		String path = Ip2RegionExample.class.getResource("/ip2region/ip2region.xdb").getPath();
		Searcher searcher = Searcher.newWithFileOnly(path);
		String region = searcher.search("14.116.172.246");
		// 内容格式为：国家|区域|省份|城市|ISP(网络服务供应商)
		System.out.println(region);
		System.out.println("查询耗时：" + (System.currentTimeMillis() - start));
	}
}
