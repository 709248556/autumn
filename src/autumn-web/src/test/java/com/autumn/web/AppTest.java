package com.autumn.web;

import org.junit.Test;

import com.autumn.util.AutumnContextUtils;

/**
 * Unit test for simple App.
 * 
 * @author 老码农
 *
 *         2017-12-06 14:12:24
 */
public class AppTest {

	String testYamlString = "eureka:\r\n" + "  client:\r\n" + "    serviceUrl:\r\n"
			+ "      defaultZone: http://192.168.1.248:8761/eureka/\r\n" + "    healthcheck:\r\n"
			+ "      enabled: true\r\n" + "  instance:\r\n" + "    prefer-ip-address: true\r\n"
			+ "    instance-id: ${spring.cloud.client.ip-address}:${server.port}\r\n"
			+ "    lease-expiration-duration-in-seconds: 30\r\n" + "    lease-renewal-interval-in-seconds: 10";

	String testYamlString2 = "zuul.add-host-header  =true\r\n" + "zuul.host.connect-timeout-millis=10000\r\n"
			+ "zuul.host.socket-timeout-millis=10000   \r\n" + "zuul.host.max-per-route-connections=2000   \r\n"
			+ "zuul.host.max-total-connections=10000  \r\n" + "zuul.host.semaphore.max-semaphores=10000  \r\n" + "\r\n"
			+ "zuul.routes.gift.path=/gift-conf/**\r\n" + "zuul.routes.gift.serviceId=service-activity-gift\r\n"
			+ "zuul.routes.gift.sensitive-headers=true\r\n" + "zuul.routes.gift.custom-sensitive-headers=true";

	@Test
	public void test1() {
		// Yaml aml = new Yaml();
		Object value = AutumnContextUtils.parseYml(testYamlString, false);

		System.out.println(value);

		value = AutumnContextUtils.parseProperties(testYamlString2, false);

		System.out.println(value);
	}

}
