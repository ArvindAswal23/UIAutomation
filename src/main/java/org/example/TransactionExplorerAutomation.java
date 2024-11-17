package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;

public class TransactionExplorerAutomation {
    public static void main(String[] args) {
        WebDriverManager.firefoxdriver().setup();
        // Step 1: Set up WebDriver and open the target URL
        WebDriver driver = new FirefoxDriver();

        try {
            // Step 2: Navigate to the provided URL
            driver.get("https://blockstream.info/block/000000000000000000076c036ff5119e5a5a74df77abf64203473364509f7732");

            // Test Case 1: Validate the transaction list heading
            WebElement transactionListHeading = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div[2]/div[3]/h3"));
            String expectedHeading = "25 of 2875 Transactions";
            String actualHeading = transactionListHeading.getText();
            // IF condition to check the actual and expected behaviour
            if (actualHeading.equals(expectedHeading)) {
                System.out.println("Test Case 1 Passed: Transaction list heading is correct.");
            } else {
                System.out.println("Test Case 1 Failed: Expected heading '" + expectedHeading + "' but found '" + actualHeading + "'.");
            }

            // Test Case 2: Print the transaction hash of the transactions which has exactly 1 input and 2 outputs
            Thread.sleep(3000); // Wait for the page to load all the transactions
            // Counter to keep track of the passed transactions
            int passedTransactionsCount = 0;
            // Step 1: Locate all transaction boxes
            List<WebElement> transactions = driver.findElements(By.xpath("//div[@class='transaction-box']"));

            // Step 2: Iterate over each transaction
            for (WebElement transaction : transactions) {
                try {
                    // Step 3: Find input elements for the particular transaction
                    List<WebElement> inputs = transaction.findElements(By.xpath(".//div[@class='ins-and-outs']//div[@class='vins']//div[@class='vin']"));

                    // Step 4: Find output elements
                    List<WebElement> outputs = transaction.findElements(By.xpath(".//div[@class='ins-and-outs']//div[@class='vouts']//div[@class='vout']"));

                    // Step 5: Check if the transaction has exactly 1 input and 2 outputs
                    if (inputs.size() == 1 && outputs.size() == 2) {
                        // Extract transaction hash
                        WebElement txHashElement = transaction.findElement(By.xpath(".//div[@class='header']//div[@class='txn']//a"));
                        String txHash = txHashElement.getText();
                        // Step 6: Print the transaction hash for the 1 input and 2 outputs
                        System.out.println("Test Case2 Passed--> Transaction Hash with 1 input and 2 outputs: " + txHash);
                        passedTransactionsCount++;
                    }
                } catch (Exception e) {
                    // Step 7: Handle exceptions gracefully, ensuring the script doesn't crash abruptly
                    System.out.println("Error extracting transaction hash: " + e.getMessage());
                }
            }
            //print the total number of passed transactions
            System.out.println("Total Passed Transactions: " + passedTransactionsCount);

    } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions gracefully, ensuring the script doesn't crash abruptly
        } finally {
            // Close the browser after test completion
            driver.quit();
        }
    }
}