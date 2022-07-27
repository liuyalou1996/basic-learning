package com.universe.thirdparty.geo;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Postal;
import com.maxmind.geoip2.record.Subdivision;

import java.io.File;
import java.net.InetAddress;

/**
 * @author Nick Liu
 * @date 2022/7/27
 */
public class GeoIp2Example {

	private static final String BASE_PATH = System.getProperty("user.home") + File.separator + "Ip2Geo" + File.separator + "GeoLite2-City_20220722";

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		File database = new File(BASE_PATH + File.separator + "GeoLite2-City.mmdb");
		try (DatabaseReader reader = new DatabaseReader.Builder(database).build()) {
			InetAddress ipAddress = InetAddress.getByName("128.101.101.101");
			CityResponse response = reader.city(ipAddress);

			Country country = response.getCountry();
			System.out.println(country.getIsoCode()); // 国家ISO代码
			System.out.println(country.getName()); // 国家名称
			System.out.println(country.getNames()); // 多语言国家名称

			Subdivision subdivision = response.getMostSpecificSubdivision();
			System.out.println(subdivision.getIsoCode()); // 子区域ISO码
			System.out.println(subdivision.getName()); // 子区域名称
			System.out.println(subdivision.getNames());// 多语言子区域名称

			City city = response.getCity();
			System.out.println(city.getName()); // 城市名称
			System.out.println(city.getNames()); // 多语言城市名称

			Postal postal = response.getPostal();
			System.out.println(postal.getCode()); // 邮编

			Location location = response.getLocation();
			System.out.println(location.getLatitude());  // 44.9733
			System.out.println(location.getLongitude()); // -93.2323
		}

		System.out.println("查询耗时：" + (System.currentTimeMillis() - start));
	}
}
