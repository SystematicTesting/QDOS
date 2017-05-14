/*
 * Copyright (c) Jan 11, 2017 Systematic Testing Ltd. (www.systematictesting.com) to Present..
 * All rights reserved. 
 */
package com.systematictesting.automation.core.framework;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.systematictesting.automation.core.constants.FrameworkParams;
import com.systematictesting.automation.core.constants.SystemParams;
import com.systematictesting.automation.core.utils.CommandLineParamsUtils;

public class Browser {

	public static WebDriver wbdv = null;
	public static EventFiringWebDriver driver = null;
	private static final Browser browser = new Browser();

	private Browser() {
	}

	public static Browser getInstance() {
		return browser;
	}

	public EventFiringWebDriver getDriver() {
		if (wbdv == null) {
			if (CommandLineParamsUtils.getInstance().getBrowserName().equals(FrameworkParams.BROWSER_FIREFOX)) {
				FirefoxProfile profile = new FirefoxProfile();
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				if (StringUtils.isNotBlank(CommandLineParamsUtils.getInstance().getProxyUrl())) {
					setProxyServerInBrowserCapabilities(capabilities);
				}
				capabilities.setCapability(FirefoxDriver.PROFILE, profile);
				wbdv = new FirefoxDriver(capabilities);
				driver = new EventFiringWebDriver(wbdv);
				driver.manage().window();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}
			if (CommandLineParamsUtils.getInstance().getBrowserName().equals(FrameworkParams.BROWSER_CHROME)) {
				if (StringUtils.isNotBlank(CommandLineParamsUtils.getInstance().getProxyUrl())) {
					DesiredCapabilities capabilities = DesiredCapabilities.chrome();
					setProxyServerInBrowserCapabilities(capabilities);
					wbdv = new ChromeDriver(capabilities);
				} else {
					wbdv = new ChromeDriver();
				}
				
				driver = new EventFiringWebDriver(wbdv);
				driver.manage().window();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}
//			if (CommandLineParamsUtils.getInstance().getBrowserName().equals(FrameworkParams.BROWSER_IE)) {
//				if (StringUtils.isNotBlank(CommandLineParamsUtils.getInstance().getProxyUrl())){
//					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
//					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//					wbdv = new InternetExplorerDriver(capabilities);
//				} else {
//					wbdv = new InternetExplorerDriver();
//				}
//				
//				driver = new EventFiringWebDriver(wbdv);
//				driver.manage().window();
//				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//			}
			if (CommandLineParamsUtils.getInstance().getBrowserName().equals(FrameworkParams.BROWSER_HEADLESS)) {
				DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
				if (StringUtils.isNotBlank(CommandLineParamsUtils.getInstance().getProxyUrl())) {
					setProxyServerInBrowserCapabilities(capabilities);
				}
				capabilities.setCapability("phantomjs.binary.path", System.getProperty(SystemParams.PATH_PHANTOMJS_DRIVER));
				capabilities.setJavascriptEnabled(true);
				wbdv = new PhantomJSDriver(capabilities);
				driver = new EventFiringWebDriver(wbdv);
				driver.manage().window();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}
		}

		return driver;
	}

	private void setProxyServerInBrowserCapabilities(DesiredCapabilities capabilities) {
		Proxy proxy = new Proxy();
		proxy.setProxyType(ProxyType.MANUAL);
		proxy.setHttpProxy(CommandLineParamsUtils.getInstance().getProxyUrl());
		proxy.setSslProxy(CommandLineParamsUtils.getInstance().getProxyUrl());
		capabilities.setCapability(CapabilityType.PROXY, proxy);
	}

	public WebDriver getWebDriver() {
		return wbdv;
	}

	public void resetWebDriver() {
		if (wbdv != null && driver != null) {
			this.close();
		}
		driver = null;
		wbdv = null;
	}

	public void close() {
		if (CommandLineParamsUtils.getInstance().getBrowserName().equals(FrameworkParams.BROWSER_FIREFOX)) {
			driver.quit();
		}
		if (CommandLineParamsUtils.getInstance().getBrowserName().equals(FrameworkParams.BROWSER_CHROME)) {
			driver.close();
			wbdv.quit();
		}
		if (CommandLineParamsUtils.getInstance().getBrowserName().equals(FrameworkParams.BROWSER_HEADLESS)){
			driver.close();
			wbdv.quit();
		}
//		if (CommandLineParamsUtils.getInstance().getBrowserName().equals(FrameworkParams.BROWSER_IE)) {
//			wbdv.quit();
//			driver.quit();
//		}
	}

}
