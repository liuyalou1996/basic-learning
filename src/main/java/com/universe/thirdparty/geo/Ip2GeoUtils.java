package com.universe.thirdparty.geo;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.File;
import java.net.InetAddress;
import java.util.Optional;

/**
 * IP归属地解析。
 * 海外国家IP归属用的是GeoIP2免费数据库, 更多参考：<a href="https://github.com/maxmind/GeoIP2-java">GeoIP2-java</a>
 * 国内IP归属用的是Ip2Region提供的数据，更多参考：<a href="https://github.com/lionsoul2014/ip2region">Ip2Region</a>
 * @author Nick Liu
 * @date 2022/7/27
 */
@Slf4j
public class Ip2GeoUtils {

	/**
	 * Ip2Region解析无数据默认值
	 */
	private static final String DEFAULT_RESOLVE_VALUE = "0";

	/**
	 * 中国ISO代码
	 */
	private static final String CHINA_ISO_CODE = "CN";

	/**
	 * Geo2Ip数据库名，适用于海外国家IP归属
	 */
	private static final String GEO2IP_DB = "GeoLite2-City.mmdb";
	/**
	 * Ip2Region数据库名，适用于国内IP归属
	 */
	private static final String IP2REGION_DB = "ip2region.xdb";

	/**
	 * .mmdb和.xdb文件目录
	 */
	private static final String IP_DB_BASE_DIRECTORY = System.getProperty("user.home") + File.separator + "Ip2Geo" + File.separator + "db";


	/**
	 * IP解析区分国内和海外，国内相关信息保存为中文，国外相关信息保存为英文
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public IpResolveResult resolveIpFromDifferentCountry(String ip) {
		IpResolveResult ipResolveResult = resolveOverseasIp(ip);
		if (!ipResolveResult.isSuccessful() || isOverseasCountry(ipResolveResult.getCountryIsoCode())) {
			return ipResolveResult;
		}
		return resolveDomesticIp(ip);
	}

	/**
	 * 解析海外IP归属，采用GeoIp2的免费数据库
	 * @param ip
	 * @return
	 */
	public IpResolveResult resolveOverseasIp(String ip){
		File geo2ipDb = new File(IP_DB_BASE_DIRECTORY, GEO2IP_DB);
		try (DatabaseReader reader = new DatabaseReader.Builder(geo2ipDb).build()) {
			InetAddress ipAddress = InetAddress.getByName(ip);
			CityResponse response = reader.city(ipAddress);

			Country country = response.getCountry();
			Subdivision subdivision = response.getMostSpecificSubdivision();
			City city = response.getCity();
			return IpResolveResult.builder()
				.countryIsoCode(country.getIsoCode())
				.countryName(country.getName())
				.subdivisionName(subdivision.getName())
				.cityName(city.getName())
				.successful(true)
				.build();
		} catch (Exception e) {
			log.error("Fail to resolve overseas ip: {}", e.getMessage(), e);
			return IpResolveResult.builder().successful(false).build();
		}
	}

	/**
	 * 解析国内IP归属，采用的是Ip2Region
	 * @param ip
	 * @return
	 */
	public IpResolveResult resolveDomesticIp(String ip) {
		try {
			File ip2RegionDb = new File(IP_DB_BASE_DIRECTORY, IP2REGION_DB);
			Searcher searcher = Searcher.newWithFileOnly(ip2RegionDb.getPath());
			String regionStr = searcher.search(ip);
			// 内容格式为：国家|区域|省份|城市|ISP(网络服务供应商)，没有值则显示为0
			String[] region = regionStr.split("\\|");
			return IpResolveResult.builder()
				.countryIsoCode(CHINA_ISO_CODE)
				.countryName(Optional.of(region[0]).filter(v -> !DEFAULT_RESOLVE_VALUE.equals(v)).orElse(StringUtils.EMPTY))
				.subdivisionName(Optional.of(region[2]).filter(v -> !DEFAULT_RESOLVE_VALUE.equals(v)).orElse(StringUtils.EMPTY))
				.cityName(Optional.of(region[3]).filter(v -> !DEFAULT_RESOLVE_VALUE.equals(v)).orElse(StringUtils.EMPTY))
				.successful(true)
				.build();
		} catch (Exception e) {
			log.error("Fail to resolve domestic ip: {}", e.getMessage(), e);
			return IpResolveResult.builder().successful(false).build();
		}
	}

	private boolean isOverseasCountry(String countryIsoCode) {
		return !CHINA_ISO_CODE.equals(countryIsoCode);
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class IpResolveResult {

		/**
		 * 国家ISO代码
		 */
		private String countryIsoCode;
		/**
		 * 国家名称
		 */
		private String countryName;
		/**
		 * 子区域名称，国外为洲、郡、岛等；国内为省份
		 */
		private String subdivisionName;

		/**
		 * 城市名称
		 */
		private String cityName;

		/**
		 * 是否解析成功
		 */
		private boolean successful;
	}

}
