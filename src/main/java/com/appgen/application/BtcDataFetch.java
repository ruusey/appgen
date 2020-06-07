package com.appgen.application;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import javax.xml.crypto.Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BtcDataFetch extends Thread {
	private static final String URL = "https://www.buybitcoinworldwide.com/how-many-bitcoins-are-there/";

	public void run() {
		long start = System.currentTimeMillis();
		System.setProperty("webdriver.chrome.driver", "D:/downloads/chromedriver.exe");
		Document doc = null;
		String hashRateDoc = null;
		String difficultyDoc = null;
		WebDriver driver = new ChromeDriver();
		driver.get(URL);
		JsonNode hrFinal = null;
		JsonNode diffFinal = null;
		try {
			waitForPageLoaded(driver);
			doc = Jsoup.parse(driver.getPageSource());
			hashRateDoc = Jsoup.connect(
					"https://api.blockchain.info/charts/hash-rate?daysAverageString=7D&timespan=1year&sampled=true&metadata=false&cors=true&format=json")
					.ignoreContentType(true).execute().body();
			difficultyDoc = Jsoup.connect(
					"https://api.blockchain.info/charts/difficulty?timespan=1year&sampled=true&metadata=false&cors=true&format=json")
					.ignoreContentType(true).execute().body();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode hashRateNode = mapper.readValue(hashRateDoc, JsonNode.class);
			JsonNode difficultyNode = mapper.readValue(difficultyDoc, JsonNode.class);
			hrFinal = hashRateNode.get("values").get(0).get("y");
			diffFinal = difficultyNode.get("values").get(0).get("y");

		} catch (Exception e) {

			e.printStackTrace();
		}

		BigDecimal hashRate = new BigDecimal(hrFinal.asText());
		hashRate = hashRate.multiply(new BigDecimal("1000000000000"));
		BigDecimal diff = new BigDecimal(diffFinal.asText());
		BigDecimal two = new BigDecimal("2").pow(32);

		BigDecimal fin = diff.multiply(two);
		BigDecimal res = fin.divide(hashRate, 10, RoundingMode.HALF_UP);
		// Time (sec)= difficulty * 2**32 / hashrate
		Element btcLeftElement = doc.getElementById("bitcoinsleft");
		Element totalBtcElement = doc.getElementById("totalbtc");
		Element btcPerDatElement = doc.getElementById("btcperday");

		Double btcLeft = Double.parseDouble(btcLeftElement.text().replaceAll(",", ""));
		Double totalBtc = Double.parseDouble(totalBtcElement.text().replaceAll(",", ""));
		Double btcPerDay = Double.parseDouble(btcPerDatElement.text().replaceAll(",", ""));

		Double sfRatio10Day = totalBtc / (btcPerDay * (10));
		Double sfRatioYearly = totalBtc / (btcPerDay * (365));

		Double price = Math.exp(-1.84) * (Math.pow(sfRatioYearly, 3.36));

		System.out.println("Fetched BTC data from [" + URL + "] in " + (System.currentTimeMillis() - start) + "ms");
		System.out.println("Bitcoins Left to be Mined: " + btcLeft);
		System.out.println("SF Ratio 10Day: " + sfRatio10Day);
		System.out.println("SF Ratio Yearly: " + sfRatioYearly);
		System.out.println("Model Price: " + price);
		System.out.println("Seconds per block: " + res);
		System.out.println("Total Bitcoins: " + totalBtc);

	}

	public void waitForPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			// Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	public static void main(String[] args) {
		BtcDataFetch fetch = new BtcDataFetch();
		fetch.start();
	}

}
