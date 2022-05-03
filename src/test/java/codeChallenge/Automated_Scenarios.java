package codeChallenge;

import static org.testng.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Automated_Scenarios extends actionsMethods
{
	public static String url_tor;
	
	@Test(priority = 0)
	public static void Scenario_01() throws Exception
	{
		String URL=getPropertyValue("env-url");
		url_tor=URL.substring(0, 29);
		try {
			int no_of_products_to_add_in_cart = Integer.parseInt(getPropertyValue("no_of_products_to_add_in_cart"));
			System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(" - - - - - START : 01 Add Products To Cart & Open Cart. - - - - ");
			System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			rep_CreateTest("Pomelo Fashion - Add Products To My Shopping Bag");
			
			//Navigate To Pomelo Fashion Site & Wait Till It Open.
				openURL(URL);
				waitForElementVisibility(header_Item_link);
				rep_AddLogWithScreenShot("Pass", "URL Opened : "+URL, web_TakeScreenShot());
			
			//Go To Random Header Product Category Page & Wait Till It Open.
				click(header_Item_link);
				waitForElementVisibility(first_product_link);
				rep_AddLogInfo("Opened Product Category Page, To Add Products To Cart");
				
				
			//Add Products Into Cart
				String beforeAdding_cart_items_count = driver.findElement(cart_items_lbl).getAttribute("innerText");
				int x=0,tempvar=1,flag=0;
				while(x==0)
					{
						//Dynamic Xpath
						String pro_xpath = "(//div[contains(@class,'infinite-scroll-component')]//div[contains(@class,'product-item hoverable')])["+tempvar+"]/a//div[contains(@class,'add-to-bag')]//span[@class='option-item button'][1]";
						By pro_size = By.xpath(pro_xpath);
						if(isElementAvailable(pro_size)==true)
						{
							moveToElement(pro_size);
							click(pro_size);
							
							waitForElementVisibility(cart_close_icon);
							click(cart_close_icon);
							flag+=1;
							tempvar+=1;
							if(flag==no_of_products_to_add_in_cart)
							{
								x=1;
								break;
							}
						}
						else
						{
							tempvar+=1;
						}
					}
				
				wait(8);
				String AfterAdding_cart_items_count = driver.findElement(cart_items_lbl).getAttribute("innerText");
			
			//Validate Products Added To Cart By Checking Before & After Cart count
				boolean validation=false;
				if(Integer.parseInt(beforeAdding_cart_items_count)<Integer.parseInt(AfterAdding_cart_items_count))
				{
					validation=true;
					rep_AddLog("Pass","Cart Updated");
				}	
				else
				{
					validation=false;
					rep_AddLogWithScreenShot("Fail","No Product added in cart",web_TakeScreenShot());
				}
				Assert.assertEquals(validation, true, "In Cart Items Not Added");
			
				
			//Go To Cart
				if(isElementVisible(cart_close_icon)==true)
				{
					click(cart_close_icon);
					wait(2);
				}
				click(cart_icon);
				wait(5);
				
			rep_AddLogWithScreenShot("Pass", "Products Added Into Cart", web_TakeScreenShot());
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			rep_AddLogWithScreenShot("Fail", "Failed To Add Products Into Cart, Log: "+e.getMessage(), web_TakeScreenShot());
			assertTrue(false, "Unxpected Error on :"+e.getMessage());
		}
		finally {
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(" - - - - - END : 01 Add Products To Cart & Open Cart. - - - - ");
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		}

	}
	@Test(priority = 1,dependsOnMethods = "Scenario_01")
	public static void Scenario_02() throws Exception
	{		
		try {
			rep_CreateTest("Pomelo Fashion - Update Product Quantity");
			System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(" - - - - - START : 02 Update Product Quantity. - - - - ");
			System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			
			// Get First Product href & class to create dynamic xpath, to update/validate quantity
				String prohref = driver.findElement(get_First_ProductInCart_lbl).getAttribute("href");
				String proclass = driver.findElement(get_First_ProductInCart_lbl).getAttribute("class");
				String proname = driver.findElement(get_First_ProductInCart_lbl).getAttribute("innerText");
				rep_AddLogInfo("Updating Quantity For :"+proname);
				prohref=prohref.substring(url_tor.length(), prohref.length());
				//Dynamic Xpath
				By select_quantity_dd = By.xpath("//a[@href='"+prohref+"'][@class='"+proclass+"']/ancestor::div[contains(@class,'product-information')]//div[contains(@class,'size-quantity')]//div[contains(@class,'info__quantity')]//div[@role='button']/select");
			
			//Select Quantity & Validate 
				Select quantity_dd = new Select(driver.findElement(select_quantity_dd));
				String quan_before = quantity_dd.getFirstSelectedOption().getAttribute("text");
				moveToElement(select_quantity_dd);
				rep_AddLogWithScreenShot("Pass", "Quantity Before Update", web_TakeScreenShot());
				System.out.println("Before Quantity Update, Selected Value Is : "+quan_before);
				quantity_dd.selectByIndex(1);
				wait(5);
				Select quantity_dda = new Select(driver.findElement(select_quantity_dd));
				String quan_after = quantity_dda.getFirstSelectedOption().getAttribute("text");
				System.out.println("After Quantity Updated, Selected Value Is : "+quan_after);
				moveToElement(select_quantity_dd);
				if(quan_before.equals(quan_after))
				{
					System.out.println("Quantity Updated, Fail");
					rep_AddLogWithScreenShot("Fail", "Quantity Not Updated", web_TakeScreenShot());
					Assert.assertEquals(false, true, "Quantity Not Updated");
				}
				else
				{
					System.out.println("Quantity Updated, Pass");
					rep_AddLogWithScreenShot("Pass", "Updated Quantity Successfully", web_TakeScreenShot());
				}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			rep_AddLogWithScreenShot("Fail", "While Updating the Quantity, Log: "+e.getMessage(), web_TakeScreenShot());
			assertTrue(false, "Unxpected Error on :"+e.getMessage());
		}
		finally {
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(" - - - - - END : 02 Update Product Quantity. - - - - ");
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		}
	}
	@Test(priority = 2,dependsOnMethods = "Scenario_01")
	public static void Scenario_03() throws Exception
	{		
		try {
			rep_CreateTest("Pomelo Fashion - Update Product Size");
			System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(" - - - - - START : 03 Update Product Size. - - - - ");
			System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			
			// Get First Product href & class to update & validate quantity
				String prohref = driver.findElement(get_First_ProductInCart_lbl).getAttribute("href");
				String proclass = driver.findElement(get_First_ProductInCart_lbl).getAttribute("class");
				String proname = driver.findElement(get_First_ProductInCart_lbl).getAttribute("innerText");
				rep_AddLogInfo("Updating Size For :"+proname);
				prohref=prohref.substring(url_tor.length(), prohref.length());
				//Dynamic Xpath
				By select_size_dd = By.xpath("//a[@href='"+prohref+"'][@class='"+proclass+"']/ancestor::div[contains(@class,'product-information')]//div[contains(@class,'size-quantity')]//div[contains(@class,'info__size')]//div[@role='button']/select");
			
			//Select Quantity & Validate 
				Select size_dd = new Select(driver.findElement(select_size_dd));
				String size_before = size_dd.getFirstSelectedOption().getAttribute("text");
				moveToElement(select_size_dd);
				rep_AddLogWithScreenShot("Pass", "Size Before Update", web_TakeScreenShot());
				System.out.println("Before Size Update, Selected Value Is : "+size_before);
				size_dd.selectByIndex(1);
				wait(5);
				Select size_dda = new Select(driver.findElement(select_size_dd));
				String size_after = size_dda.getFirstSelectedOption().getAttribute("text");
				System.out.println("After Size Updated, Selected Value Is : "+size_after);
				moveToElement(select_size_dd);
				if(size_before.equals(size_after))
				{
					System.out.println("Quantity Updated, Fail");
					rep_AddLogWithScreenShot("Fail", "Size Not Updated", web_TakeScreenShot());
					Assert.assertEquals(false, true, "Size Not Updated");
					
				}
				else
				{
					System.out.println("Size Updated, Pass");
					rep_AddLogWithScreenShot("Pass", "Updated Size Successfully", web_TakeScreenShot());
				} 
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			rep_AddLogWithScreenShot("Fail", "While Updating the Size, Log: "+e.getMessage(), web_TakeScreenShot());
			assertTrue(false, "Unxpected Error on :"+e.getMessage());
		}
		finally {
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(" - - - - - END : 03 Update Product Size. - - - - ");
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		}
	}
	@Test(priority = 3,dependsOnMethods = "Scenario_01")
	public static void Scenario_04() throws Exception
	{		
			int product_to_remove_from_cart_index=Integer.parseInt(getPropertyValue("product_to_remove_from_cart_index"));
		try {
			rep_CreateTest("Pomelo Fashion - Delete Product From Cart");
			System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(" - - - - - START : 04 Delete Product From Cart. - - - - ");
			System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			
			//Before Deleting - Get List Of Products In Cart
				System.out.println(" - - List Of Products Available In Cart, Before Delete. - -");
				int no_of_products_in_cart = driver.findElements(products_in_cart_list).size();
				for(int i=1;i<=no_of_products_in_cart;i++)
				{
					System.out.println("I_No: "+i+" Product Name: "+driver.findElement(By.xpath(products_in_cart+"["+i+"]")).getAttribute("innerText"));
				}
				
			//Removing Product From Cart
				By pro_remove_cart = By.xpath(product_in_cart_delete_icon+"["+product_to_remove_from_cart_index+"]");
				String deleting_product = driver.findElement(By.xpath(products_in_cart+"["+product_to_remove_from_cart_index+"]")).getAttribute("innerText");
				System.out.println("Deleting Product : "+deleting_product);
				rep_AddLogInfo("Deleting Product : "+deleting_product);
				moveToElement(pro_remove_cart);
				rep_AddLogWithScreenShot("Pass", "Deleting Product", web_TakeScreenShot());
				click(pro_remove_cart);
				wait(5);
				
			//After Deleting - Get List Of Products In Cart
				System.out.println(" - - List Of Products Available In Cart, After Delete. - -");
				no_of_products_in_cart = driver.findElements(products_in_cart_list).size();
				System.out.println("No OF Products Present In Cart"+no_of_products_in_cart);
				int val_flag=0;
				
			//Validating - Respective Product Deleted
				for(int i=1;i<=no_of_products_in_cart;i++)
				{
					String pro_in_cart = driver.findElement(By.xpath(products_in_cart+"["+i+"]")).getAttribute("innerText");
					System.out.println("I_No: "+i+" Product Name: "+pro_in_cart);
					if(deleting_product.equals(pro_in_cart))
					{
						val_flag++;
					}
				}
				if(val_flag==0)
				{
					System.out.println("Pass : Product Deleted Successfully");
					rep_AddLogWithScreenShot("Pass", "Product Deleted From Cart", web_TakeScreenShot());
					
				}
				else
				{
					System.out.println("Fail : Product Not Deleted Successfully");
					rep_AddLogWithScreenShot("Fail", "Product Not Deleted From Cart", web_TakeScreenShot());
					Assert.assertEquals(false, true, "Product Not Deleted From Cart");
				}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			rep_AddLogWithScreenShot("Fail", "While Deleting the Product In Cart, Log: "+e.getMessage(), web_TakeScreenShot());
			assertTrue(false, "Unxpected Error on :"+e.getMessage());
		}
		finally {
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(" - - - - - END : 04 Delete Product From Cart. - - - - ");
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		}
	}
	@Test(priority = 4,dependsOnMethods = "Scenario_01")
	public static void Scenario_05() throws Exception
	{		
		String coupon=getPropertyValue("coupon");
		try {
				rep_CreateTest("Pomelo Fashion - Apply Coupon In Cart");
				System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
				System.out.println(" - - - - - START : 05 Apply Coupon In Cart. - - - - ");
				System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
				
				rep_AddLogInfo("Applying \'"+coupon+"\' Coupon.");
				
				sendKeys(apply_coup_input, coupon);
				
				//Validating Coupon Added Into Field
				if(driver.findElement(apply_coup_input).getAttribute("value").equals(coupon))
				{
					System.out.println("Pass : Coupon Validated");
					rep_AddLogWithScreenShot("Pass", "Coupon Validated", web_TakeScreenShot());
				}
				else
				{
					System.out.println("Fail : Coupon not able to validate");
					rep_AddLogWithScreenShot("Fail", "Coupon not able to validate", web_TakeScreenShot());
					Assert.assertEquals(false, true, "Coupon not able to validate");
				}
				click(apply_coup_bt);
				wait(2);
				rep_AddLogWithScreenShot("Pass", "Coupon Status", web_TakeScreenShot());				
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			rep_AddLogWithScreenShot("Fail", "While Deleting the Product In Cart, Log: "+e.getMessage(), web_TakeScreenShot());
			assertTrue(false, "Unxpected Error on :"+e.getMessage());
		}
		finally {
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(" - - - - - END : 05 Apply Coupon In Cart. - - - - ");
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		}
	}	
	
	@Test(priority = 5,dependsOnMethods = "Scenario_01")
	public static void Scenario_06() throws Exception
	{		
		try {
				rep_CreateTest("Pomelo Fashion - Validate Proceed To Checkout Button");
				System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
				System.out.println(" - - - - - START : 06 Validate Proceed To Checkout Button. - - - - ");
				System.out.println(" - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
				rep_AddLogInfo("Clicking On Checkout Button.");
				//Clicking On Checkout Button, Validate Login Screen should open
				click(check_out_now_bt);
				wait(5);
				if(isElementVisible(login_email)==true && isElementVisible(login_pass)==true)
				{
					System.out.println("Pass : Validated Post Checkout, Landed On Login Page");
					rep_AddLogWithScreenShot("Pass", "Validated Post Checkout, Landed On Login Page", web_TakeScreenShot());
				}
				else
				{
					System.out.println("Fail : Not Landed On Login Page");
					rep_AddLogWithScreenShot("Fail", "Not Landed On Login Page", web_TakeScreenShot());
					Assert.assertEquals(false, true, "Not Landed On Login Page");
				}		
				wait(5);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			rep_AddLogWithScreenShot("Fail", "While Validating Proceed To Checkout Button, Log: "+e.getMessage(), web_TakeScreenShot());
			assertTrue(false, "Unxpected Error on :"+e.getMessage());
		}
		finally {
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(" - - - - - END : 06 Validate Proceed To Checkout Button. - - - - ");
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		}
	}	
}