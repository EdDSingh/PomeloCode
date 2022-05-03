package repository;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import codeChallenge.actionsMethods;
public class aspireRepository 
{		
	public static By header_Item_link = By.xpath("//div[contains(@class,'slide') and contains(@class,'active')]");
	public static By first_product_link = By.xpath("(//div[contains(@class,'infinite-scroll-component')]//div[contains(@class,'product-item hoverable')])[1]/a//div[@class='product-image__cover']");
	public static By first_Product_Select_Size_lbl = By.xpath("((//span[text()='Add To Bag'])[1] | (//div[contains(@class,'infinite-scroll-component')]//div[contains(@class,'product-item hoverable')])[1]/a//div[contains(@class,'add-to-bag')]//span[@class='option-item button'][1])[1]");
	public static By second_Product_Select_Size_lbl = By.xpath("((//span[text()='Add To Bag'])[2] | (//div[contains(@class,'infinite-scroll-component')]//div[contains(@class,'product-item hoverable')])[2]/a//div[contains(@class,'add-to-bag')]//span[@class='option-item button'][1])[1]");
	
	public static By cart_items_lbl = By.xpath("//li[@data-cy='nav_user__cart']//following-sibling::span[contains(@class,'badge')]");
	public static By cart_icon = By.xpath("//li[@data-cy='nav_user__cart']");
	public static By cart_close_icon = By.xpath("//span[@data-cy='close_cart_notice']");
	
	
	public static By get_First_ProductInCart_lbl = By.xpath("(//div[contains(@class,'cart-products')]//div[contains(@class,'product-name')]//a)[1]");
	
	
	public static By products_in_cart_list = By.xpath("//div[contains(@class,'cart-products')]//div[contains(@class,'product-name')]//a");
	public static String products_in_cart = "(//div[contains(@class,'cart-products')]//div[contains(@class,'product-name')]//a)";
	public static String product_in_cart_delete_icon = "(//div[contains(@class,'cart-remove')]//img)";
	
	
	public static By apply_coup_input = By.xpath("//div[@class='cart-discount__main']//input");
	public static By apply_coup_bt = By.xpath("//div[@class='cart-discount__main']//button[@type='button']");
	public static By check_out_now_bt = By.xpath("//button[@data-cy='cart__checkout']");
	
	public static By login_email = By.xpath("//div[contains(@class,'auth__body-wrapper')]//input[@name='email']");
	public static By login_pass = By.xpath("//div[contains(@class,'auth__body-wrapper')]//input[@name='password']");
}
