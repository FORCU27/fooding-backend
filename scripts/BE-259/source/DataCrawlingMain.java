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

            if (!ensureElementVisible(dataIndex)) {
                System.err.println("data-index " + dataIndex + " 요소를 찾을 수 없어 건너뜁니다.");
                return null;
            }

            // CSS 선택자로 특정 data-index를 가진 div 찾기
            String cssSelector = String.format("div._1ht6unv3[data-index='%d']", dataIndex);

            // 요소가 클릭 가능할 때까지 대기
            WebElement targetDiv = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector))
            );
            targetDiv.click();
            Store store = createStoreDto();
            driver.navigate().back();
            waitForPageToLoad();
            driver.navigate().back();
            waitForPageToLoad();

            // 뒤로가기 후 스크롤 및 다음 요소 준비
            prepareNextElement(dataIndex + 1);

            return store;
        } catch (Exception e) {
            System.err.println("data-index " + dataIndex + "인 div 클릭 실패: " + e.getMessage());
            return null;
        }
    }

    /**
     * 다음 요소를 위한 준비 작업 (스크롤 + 대기 + 가시성 확인)
     */
    private void prepareNextElement(int nextDataIndex) {
        try {
            System.out.println("다음 요소 준비 중... (data-index: " + nextDataIndex + ")");

            // 1. 스크롤 실행
            scrollDownAndWaitForNewElements(450);

            Thread.sleep(5000);

            // 3. 다음 요소가 화면에 실제로 보이는지 확인
            String cssSelector = String.format("div._1ht6unv3[data-index='%d']", nextDataIndex);
            try {
                WebElement nextElement = driver.findElement(By.cssSelector(cssSelector));

                // 요소가 DOM에 존재하는지 확인
                if (nextElement != null) {
                    // 요소가 실제로 화면에 보이는지 확인
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
            System.err.println("다음 요소 준비 실패: " + e.getMessage());
        }
    }

    private void waitForPageToLoad() {
        try {
            // 1. document.readyState가 complete가 될 때까지 대기
            wait.until(webDriver ->
                    ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
            );

            // 2. jQuery가 있다면 jQuery 요청 완료 대기
            try {
                wait.until(webDriver ->
                        ((JavascriptExecutor) webDriver).executeScript("return jQuery.active == 0")
                );
            } catch (Exception e) {
                // jQuery가 없는 경우 무시
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scrollDownAndWaitForNewElements(int px) {
        try {
            // 스크롤 전 data-index 값들 수집
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

            // 스크롤 실행
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, " + px + ");");

            try {
                // data-index 값이 변경될 때까지 대기
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
                    // 기존 인덱스와 다른 새로운 인덱스가 있는지 확인
                    return !currentIndexes.equals(beforeIndexes);
                });

                Thread.sleep(1500);

                // 스크롤 후 data-index 확인
                List<WebElement> afterElements = driver.findElements(
                        By.cssSelector("div._1ht6unv2 div._1ht6unv3")
                );
                Set<String> afterIndexes = new HashSet<>();
                for (WebElement element : afterElements) {
                    String dataIndex = element.getAttribute("data-index");
                    if (dataIndex != null) {
                        afterIndexes.add(dataIndex);
                    }
                }
            } catch (Exception e) {
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 요소가 화면에 보이도록 스크롤하는 유틸리티 함수
     * @param element 스크롤할 대상 요소
     */
    private void scrollToElement(WebElement element) {
        try {
            // JavaScript를 사용해 요소까지 스크롤
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);

            // 요소가 실제로 보일 때까지 대기
            shortWait.until(ExpectedConditions.visibilityOf(element));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void smartWait(String url) {
        driver.get(url);

        try {
            // 1단계: 페이지 로딩 완료 대기
            waitForPageToLoad();

            // 2단계: 핵심 요소들이 로드될 때까지 대기
            longWait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("div._1ht6unv3")),
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class*='_1ht6unv3']"))
            ));

            // 3단계: 요소들이 실제로 상호작용 가능할 때까지 대기
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div._1ht6unv3")));

        } catch (Exception e) {
            try {
                Thread.sleep(3000);  // 5초 -> 3초로 단축
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
            // 이름 요소 대기
            WebElement nameComponent = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1.dl6ids2"))
            );
            name = nameComponent.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 설명 요소 대기
            WebElement descriptionComponent = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.mxtve20"))
            );
            description = descriptionComponent.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 주소 버튼 클릭
            WebElement addressButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("svg.zn9ch5c"))
            );
            scrollToElement(addressButton);
            addressButton.click();

            // 주소 정보가 나타날 때까지 대기 (조건부 대기)
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.zn9ch56")));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 주소 정보 추출
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
                } catch (Exception e) {
                    // 다음 선택자 시도
                }
            }

            if (addressComponent != null) {
                address = addressComponent.getText();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 지역/분류 요소 대기
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
            // 가격/정보 요소들이 모두 로드될 때까지 대기
            List<WebElement> pElements = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector("ul.zn9ch53 p.zn9ch54")
                    )
            );

            if (pElements.size() >= 3) {
                // 방향 정보
                WebElement directionElement = pElements.get(0);
                String[] directionText = directionElement.getText().trim().split("\n");
                if (directionText.length == 4) {
                    direction = directionText[0].trim() + directionText[1].trim() + " " + directionText[2].trim();
                }

                // 가격 정보
                WebElement priceElement = pElements.get(1);
                String[] priceText = priceElement.getText().trim().split("\n");
                if (priceText.length == 2) {
                    priceCategory = priceText[1];
                }

                // 운영 정보
                WebElement informationElement = pElements.get(2);
                String[] informationText = informationElement.getText().trim().split("\n");
                if (informationText.length == 2) {
                    information = informationText[1];
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 메뉴 버튼 클릭
            WebElement menuButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='메뉴']"))
            );
            scrollToElement(menuButton);
            menuButton.click();

            WebElement addressButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("svg.zn9ch5c"))
            );
            scrollToElement(addressButton);
            addressButton.click();

            // 메뉴 정보가 나타날 때까지 대기 (조건부 대기)
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div._1pz6w3u6")));

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<StoreMenu> menuList = new ArrayList<StoreMenu>();
        try {
            // 메뉴 요소들이 모두 로드될 때까지 대기
            List<WebElement> menuElements = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector("div._1pz6w3u6 a._16e3mg80 div._16e3mg81")
                    )
            );

            if (menuElements.size() > 0) {
                // 메뉴 목록
                for( WebElement menuElement : menuElements ){
                    StoreMenu menu = new StoreMenu();
                    menu.setStoreName( name );
                    // 메뉴 이름 추출
                    WebElement menuNameElement = menuElement.findElement( By.tagName("h4") );
                    String menuName = menuNameElement.getText();
                    menu.setMenuName( menuName );
                    // 가격 추출
                    WebElement priceElement = menuElement.findElement( By.cssSelector("p._16e3mg84") );
                    String price = priceElement.getText();
                    menu.setPrice( price );

                    menuList.add( menu );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 결과 출력
        System.out.println( name + " 입력 완료");
        Store store = new Store(name, description, address, priceCategory, information, region, category, direction, menuList);
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
                    // 가게 정보 추가
                    String[] temp = {
                            escapeCsvField(store.getName()),
                            escapeCsvField(store.getDescription()),
                            escapeCsvField(store.getAddress().split("\n")[0]),
                            escapeCsvField(store.getPriceCategory()),
                            escapeCsvField(store.getInformation()),
                            escapeCsvField(store.getRegion()),
                            escapeCsvField(store.getCategory()),
                            escapeCsvField(store.getDirection())
                    };
                    writer.append(String.join(",", temp));
                }
                writer.append(",");
                for( StoreMenu menu : store.getMenuList() ){
                    // 메뉴 정보 추가
                    String[] temp2 = {
                            escapeCsvField(menu.getMenuName()),
                            escapeCsvField(menu.getPrice())
                    };
                    writer.append(String.join(",", temp2));
                }
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * CSV 필드에서 특수문자 처리
     */
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
                // 요소가 존재하는지 확인 (짧은 대기시간)
                WebElement element = shortWait.until(
                        ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector))
                );

                // 요소가 화면에 보이는지 확인
                if (element.isDisplayed()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 요소가 없으면 스크롤하여 더 많은 요소 로드
            scrollDownAndWaitForNewElements(400);

            // 스크롤 직후 바로 다시 요소 찾기 시도
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // 스크롤 + 대기 후 바로 다시 요소 찾기 시도
            try {
                WebElement element = shortWait.until(
                        ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector))
                );
                if (element.isDisplayed()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 사용 예제 메인 함수
     */
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        Main clicker = new Main(driver);

//        Scanner sc = new Scanner(System.in);
//        System.out.print( "URL: " );
//        String URL = sc.nextLine();
//        System.out.print( "지역: " );
//        String REGION = sc.nextLine();
//        System.out.print( "개수: " );
//        int count = sc.nextInt();

        String URL = "https://app.catchtable.co.kr/ct/map/COMMON?showTabs=true&location=CAT011007&locationLabel=%ED%99%8D%EB%8C%80%2F%ED%95%A9%EC%A0%95%2F%EB%A7%88%ED%8F%AC&bottomSheetHeightType=FULL_LIST&serviceType=INTEGRATION&keyword=%ED%99%8D%EB%8C%80%2F%ED%95%A9%EC%A0%95%2F%EB%A7%88%ED%8F%AC&keywordSearch=%ED%99%8D%EB%8C%80%2F%ED%95%A9%EC%A0%95%2F%EB%A7%88%ED%8F%AC&uniqueListId=1754038441296&isShowListLabelExpanded=1&searchedMapLevel=8&zoomLevel=8&centerBoundsLat=37.55196360065566&centerBoundsLng=126.92612387554684&addAdditionalBottomHeightFromSubFilter=0&isScrollTop=0";
        String REGION = "홍대";
        int count = 10;

        try {
            // 웹페이지 로드
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
