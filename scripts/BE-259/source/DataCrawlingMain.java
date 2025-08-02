import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class Main {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait shortWait;
    private WebDriverWait longWait;

    public Main(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public Store clickDivByDataIndex(int dataIndex) {
        try {
            waitForPageToLoad();

            if (!ensureElementVisible(dataIndex)) return null;
            String cssSelector = String.format("div._1ht6unv3[data-index='%d']", dataIndex);

            WebElement targetDiv = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector))
            );
            targetDiv.click();
            Store store = createStoreDto();
            driver.navigate().back();
            waitForPageToLoad();

            prepareNextElement(dataIndex + 1);
            return store;
        } catch (Exception e) {
            return null;
        }
    }

    private void prepareNextElement(int nextDataIndex) {
        try {
            scrollDownAndWaitForNewElements(450);
            Thread.sleep(5000);
            String cssSelector = String.format("div._1ht6unv3[data-index='%d']", nextDataIndex);
            try {
                WebElement nextElement = driver.findElement(By.cssSelector(cssSelector));
                if (nextElement != null) {
                    if (nextElement.isDisplayed() && nextElement.isEnabled()) {
                        ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                                nextElement
                        );
                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitForPageToLoad() {
        try {
            wait.until(webDriver ->
                    ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
            );
            try {
                wait.until(webDriver ->
                        ((JavascriptExecutor) webDriver).executeScript("return jQuery.active == 0")
                );
            } catch (Exception e) {}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scrollDownAndWaitForNewElements(int px) {
        try {
            List<WebElement> beforeElements = driver.findElements(
                    By.cssSelector("div._1ht6unv2 div._1ht6unv3")
            );
            Set<String> beforeIndexes = new HashSet<>();
            for (WebElement element : beforeElements) {
                String dataIndex = element.getAttribute("data-index");
                if (dataIndex != null) {
                    beforeIndexes.add(dataIndex);
                }
            }
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, " + px + ");");
            try {
                shortWait.until(webDriver -> {
                    List<WebElement> currentElements = driver.findElements(
                            By.cssSelector("div._1ht6unv2 div._1ht6unv3")
                    );
                    Set<String> currentIndexes = new HashSet<>();
                    for (WebElement element : currentElements) {
                        String dataIndex = element.getAttribute("data-index");
                        if (dataIndex != null) {
                            currentIndexes.add(dataIndex);
                        }
                    }
                    return !currentIndexes.equals(beforeIndexes);
                });
                Thread.sleep(1500);

                List<WebElement> afterElements = driver.findElements(
                        By.cssSelector("div._1ht6unv2 div._1ht6unv3")
                );
                Set<String> afterIndexes = new HashSet<>();
                for (WebElement element : afterElements) {
                    String dataIndex = element.getAttribute("data-index");
                    if (dataIndex != null) afterIndexes.add(dataIndex);
                }
            } catch (Exception e) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void scrollToElement(WebElement element) {
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            shortWait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void smartWait(String url) {
        driver.get(url);
        try {
            waitForPageToLoad();

            longWait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("div._1ht6unv3")),
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class*='_1ht6unv3']"))
            ));

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div._1ht6unv3")));
        } catch (Exception e) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private Store createStoreDto() {
        String name = "";
        String description = "";
        String address = "";
        String priceCategory = "";
        String information = "";
        String direction = "";
        String region = "";
        String category = "";

        try {
            WebElement nameComponent = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1.dl6ids2"))
            );
            name = nameComponent.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            WebElement descriptionComponent = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.mxtve20"))
            );
            description = descriptionComponent.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            WebElement addressButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("svg.zn9ch5c"))
            );
            scrollToElement(addressButton);
            addressButton.click();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.zn9ch56")));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            WebElement addressComponent = null;
            String[] addressSelectors = {
                    "ul.zn9ch56",
                    "svg.zn9ch5c + ul.zn9ch56",
                    "div ul.zn9ch56",
                    ".zn9ch56"
            };

            for (String selector : addressSelectors) {
                try {
                    addressComponent = shortWait.until(
                            ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector))
                    );
                    break;
                } catch (Exception e) {}
            }

            if (addressComponent != null) {
                address = addressComponent.getText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            WebElement descriptionComponent = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.dl6ids3"))
            );
            String[] temp = descriptionComponent.getText().split("\n");
            region = temp[0];
            category = temp[2];

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<WebElement> pElements = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector("ul.zn9ch53 p.zn9ch54")
                    )
            );

            if (pElements.size() >= 3) {
                WebElement directionElement = pElements.get(0);
                String[] directionText = directionElement.getText().trim().split("\n");
                if (directionText.length == 4) {
                    direction = directionText[0].trim() + directionText[1].trim() + " " + directionText[2].trim();
                }

                WebElement priceElement = pElements.get(1);
                String[] priceText = priceElement.getText().trim().split("\n");
                if (priceText.length == 2) {
                    priceCategory = priceText[1];
                }

                WebElement informationElement = pElements.get(2);
                String[] informationText = informationElement.getText().trim().split("\n");
                if (informationText.length == 2) {
                    information = informationText[1];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println( name + " 입력 완료");
        Store store = new Store(name, description, address, priceCategory, information, region, category, direction);
        return store;
    }

    private static void exportStoreList(List<Store> storeList, String position) {
        try (FileWriter writer = new FileWriter("catchtable_store_list_" + position + ".csv")) {
            // CSV 헤더 작성
            String[] columnNames = {
                    "이름", "설명", "주소", "가격대", "정보", "지역", "분류", "위치"
            };
            writer.append(String.join(",", columnNames));
            writer.append("\n");

            // 데이터 작성
            for (Store store : storeList) {
                if (store != null) {
                    String[] temp = {
                            escapeCsvField(store.getName()),
                            escapeCsvField(store.getDescription()),
                            escapeCsvField(store.getAddress()),
                            escapeCsvField(store.getPriceCategory()),
                            escapeCsvField(store.getInformation()),
                            escapeCsvField(store.getRegion()),
                            escapeCsvField(store.getCategory()),
                            escapeCsvField(store.getDirection())
                    };
                    writer.append(String.join(",", temp));
                    writer.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String escapeCsvField(String field) {
        if (field == null) return "";
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }

    private boolean ensureElementVisible(int dataIndex) {
        String cssSelector = String.format("div._1ht6unv3[data-index='%d']", dataIndex);

        // 최대 5번 시도
        for (int attempt = 0; attempt < 5; attempt++) {
            try {
                WebElement element = shortWait.until(
                        ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector))
                );

                if (element.isDisplayed()) return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            scrollDownAndWaitForNewElements(400);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            try {
                WebElement element = shortWait.until(
                        ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector))
                );
                if (element.isDisplayed()) return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        Main clicker = new Main(driver);

        Scanner sc = new Scanner(System.in);
        System.out.print( "URL: " );
        String URL = sc.nextLine();
        //String URL = "https://app.catchtable.co.kr/ct/map/COMMON?showTabs=true&location=CAT011007&locationLabel=%ED%99%8D%EB%8C%80%2F%ED%95%A9%EC%A0%95%2F%EB%A7%88%ED%8F%AC&bottomSheetHeightType=FULL_LIST&serviceType=INTEGRATION&keyword=%ED%99%8D%EB%8C%80%2F%ED%95%A9%EC%A0%95%2F%EB%A7%88%ED%8F%AC&keywordSearch=%ED%99%8D%EB%8C%80%2F%ED%95%A9%EC%A0%95%2F%EB%A7%88%ED%8F%AC&uniqueListId=1754038441296&isShowListLabelExpanded=1&searchedMapLevel=8&zoomLevel=8&centerBoundsLat=37.55196360065566&centerBoundsLng=126.92612387554684&addAdditionalBottomHeightFromSubFilter=0&isScrollTop=0";
        System.out.print( "지역: " );
        String REGION = sc.nextLine();
        //String REGION = "홍대";
        System.out.print( "개수: " );
        int count = sc.nextInt();
        //int count = 10;

        try {
            clicker.smartWait( URL );

            List<Store> storeList = new ArrayList<Store>();
            for( int i=0; i<count; i++ ){
                System.out.println( "현재 저장된 수: " + storeList.size() );
                Store store = clicker.clickDivByDataIndex(i);
                if (store != null) storeList.add(store);
            }
            exportStoreList( storeList, REGION );
        } finally {
            driver.quit();
        }
    }
}
