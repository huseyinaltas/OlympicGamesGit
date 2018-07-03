package com.wiki;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class OlimpicsMadal {
	Map<String, Integer> list;
	String all = "";
	String silvers = "";
	WebDriver driver;
	String mostXpath = "//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr";

	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"/Users/huseyinaltas/Documents/selenium dependencies/drivers/chromedriver");
		driver = new ChromeDriver();
	}

	@BeforeMethod
	public void goToWebsite() {
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics");
	}

	@Test(priority = 1)
	public void sortTest() {

		List<WebElement> ranks = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[1]"));
		ranks.remove(ranks.size() - 1);
		matchingRank(ranks, "Yes"); // Call Method
		System.out.println("NAME IS MATCHING?");
		driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr/th[2]")).click();
		List<WebElement> names = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th[1]/a"));
		Set<String> namesOrder = new TreeSet<>();
		for (int i = 0; i < names.size(); i++) {
			namesOrder.add(names.get(i).getText());
		}
		String name = "";
		int count = 0;
		Iterator iterator = namesOrder.iterator();
		while (iterator.hasNext()) {
			name = (String) iterator.next();
			String actual = name;
			String expected = names.get(count).getText();
			System.out.println(name + "   " + names.get(count).getText());
			count++;
			Assert.assertEquals(actual, expected);
		}
		List<WebElement> ranks2 = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[1]"));
		matchingRank(ranks2, "No");
	}

	@Test(priority = 2)
	public void theMost() throws InterruptedException {
		System.out.println("The Most GOLD: " + mostGold());
		System.out.println("The Most SILVER: " + mostSilver());
		System.out.println("The Most BRONZE: " + mostBronze());
		System.out.println("The Most MADALS: " + mostMadals());
		System.out.println("ALL MADALS: " + all);
	}

	@Test(priority = 3)
	public void silver_18() throws InterruptedException {
		String name = "";
		mostSilver();
		System.out.println("SILVER MADALS COUNTRIES ");
		System.out.println("SILVERS MADALS: " + silvers);
		System.out.println("18 SILVER MADALS COUNTRIES ");
		for (Entry<String, Integer> eachEntry : list.entrySet()) {
			Thread.sleep(1000);
			if (eachEntry.getValue() == 18) {
				name = "COUNTRY: " + eachEntry.getKey() + "    " + "MADAL NUMBER: " + eachEntry.getValue();
				System.out.println(name);
			}}}

	@Test(priority = 4)
	public void getIndexofCountry() throws InterruptedException {
		System.out.println(getIndex("United States"));
		System.out.println(getIndex("China"));
	}

	@Test(priority = 5)
	public void sum() throws InterruptedException {
		System.out.println("TOTAL SUM OF 18 SILVER COUNTRIES: " + sum_18().toString());
	}
	
	public String mostMadal(Map<String, Integer> list) {
		String name = "";
		int max = 0;
		for (Entry<String, Integer> eachEntry : list.entrySet()) {
			if (max < eachEntry.getValue()) {
				max = eachEntry.getValue();
				name = eachEntry.getKey();
			}
		}
		return name;
	}

	public String mostGold() {
		String name = null;
		Integer k = 0;
		List<WebElement> number = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[2]"));
		List<WebElement> country = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th/a"));
		Map<String, Integer> list = convertToMap(country, number);
		int max = 0;
		all = list.toString();
		return mostMadal(list);
	}

	public String mostSilver() {
		String name = null;
		Integer k = 0;
		List<WebElement> number = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[3]"));
		List<WebElement> country = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th/a"));
		list = convertToMap(country, number);
		Set<Integer> sort = new TreeSet<>(list.values());
		int max = 0;
		silvers = list.toString();
		return mostMadal(list);
	}

	public String mostBronze() {
		String name = null;
		Integer k = 0;
		List<WebElement> number = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[4]"));
		List<WebElement> country = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th/a"));
		Map<String, Integer> list = convertToMap(country, number);
		Set<Integer> sort = new TreeSet<>(list.values());
		int max = 0;
		return mostMadal(list);
	}

	public String mostMadals() {
		String name = null;
		Integer k = 0;
		List<WebElement> number = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[5]"));
		List<WebElement> country = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th/a"));
		Map<String, Integer> list = convertToMap(country, number);
		Set<Integer> sort = new TreeSet<>(list.values());
		int max = 0;
		return mostMadal(list);
	}

	public Map<String, Integer> convertToMap(List<WebElement> country, List<WebElement> number) {
		Map<String, Integer> list = new TreeMap<>();
		for (int i = 0; i < number.size() - 1; i++) {
			list.put(country.get(i).getText(), Integer.parseInt(number.get(i).getText()));
		}
		return list;
	}

	public void matchingRank(List<WebElement> ranks, String pass) {
		System.out.println("RANK IS IT CORREC?");
		for (int i = 0; i < ranks.size(); i++) {
			int rank = i + 1;
			String expected = "" + rank;
			String actual = ranks.get(i).getText();
			System.out.println("Actual= " + actual + "	" + "Expected= " + expected);
			if (pass.equals("Yes"))
				Assert.assertEquals(actual, expected);
			else {
				Assert.assertNotEquals(actual, expected);
			}
		}
	}

	public String getIndex(String country) throws InterruptedException {
		String name = ""; int row = 0;
		int column = 0;
		for (int i = 1; i < 2; i++) {
			Thread.sleep(1000);
			for (int j = 1; j < 11; j++) {
				Thread.sleep(1000);
				WebElement element = driver.findElement(By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody//tr)["+ j + "]//th[" + i + "]/a"));
				Thread.sleep(1000);
				if (country.equals(element.getText())) {
					Thread.sleep(1000);
					name = "[Country name: " + country + "]   [Row: " + j + "]  [Column:  " + i + "]";
					break;
				}
			}
		}
		return name;
	}

	public Set<String> sum_18() throws InterruptedException {
		Set<String> list1 = new HashSet<>();
		int first = 0;
		int second = 0;
		String name = null;
		Integer k = 0;
		Thread.sleep(1000);
		List<WebElement> number = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[3]"));
		Thread.sleep(1000);
		List<WebElement> country = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th/a"));
		Map<String, Integer> list = convertToMap(country, number);
		Set<Entry<String, Integer>> eachEntry = list.entrySet();
		for (Entry<String, Integer> entry : eachEntry) {
			first = entry.getValue();
			for (Entry<String, Integer> entry2 : eachEntry) {
				if (!entry.getKey().equals(entry2.getKey()))
					second = entry2.getValue();
				if (18 == first + second) {
					name = "First: " + entry.getKey() + " with " + entry.getValue() + "---- " + "Second: "
							+ entry2.getKey() + " with " + entry2.getValue();
					list1.add(entry.getKey());
					list1.add(entry2.getKey());
				}
			}
		}
		System.out.println(name);
		return list1;
	}
}