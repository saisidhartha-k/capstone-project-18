import { Builder, By, until } from "selenium-webdriver";
import chrome from "selenium-webdriver/chrome";
import { afterAll, beforeAll, describe, expect, it } from "vitest";
const url = "http://localhost:3000";

describe("Selenium", () => {
  let driver;

  beforeAll(async () => {
    const chromeOptions = new chrome.Options();
    chromeOptions.addArguments(
      "--disable-gpu",
      "--no-sandbox",
      "--disable-dev-shm-usage"
    );
    driver = new Builder()
      .forBrowser("chrome")
      .setChromeOptions(chromeOptions)
      .build();

    await driver.manage().window().maximize();
    await driver.manage().setTimeouts({ implicit: 100000 });
    await driver.get(url);
  });

  it("should redirect to the login page", async () => {
    const username = await driver.findElement(By.name("username"));
    const password = await driver.findElement(By.name("password"));

    await username.sendKeys("admin");
    await password.sendKeys("admin");

    const submit = await driver.findElement(By.id("submit"));
    await submit.click();
    const currentUrl = await driver.getCurrentUrl();
    expect(currentUrl).toBe(`${url}/home`);
  });

  it("should be on home page", async () => {
    const currentUrl = await driver.getCurrentUrl();

    expect(currentUrl).toBe(`${url}/home`);
    const softwaresLink = await driver.findElement(By.id("All Notiications"));
    await softwaresLink.click();
  });

  it("should redirect to notifications page", async () => {
    const currentUrl = await driver.getCurrentUrl();
    expect(currentUrl).toBe(`${url}/notification`);

    const softwaresLink = await driver.findElement(By.linkText("Softwares"));
    await softwaresLink.click();
  });

  it("it should be softwares page", async () => {
    const currentUrl = await driver.getCurrentUrl();

    expect(currentUrl).toBe(`${url}/software`);

    const rmaButton = await driver.findElement(By.id("RMAbutton"));
    await rmaButton.click();

    const rmaReasonInput = await driver.findElement(By.id("RMAReason"));
    await rmaReasonInput.sendKeys("Bugs");
    const saveRmaButton = await driver.findElement(By.id("saveRMA"));
    await saveRmaButton.click();
  });

  it("should navigate to Expired Software page", async () => {
    const expiredSoftwareLink = await driver.findElement(
      By.linkText("Expired Software")
    );
    await expiredSoftwareLink.click();
  });

  it("should navigate to About To Expire Software page", async () => {
    const aboutToExpireSoftwareLink = await driver.findElement(
      By.linkText("About To Expire")
    );
    await aboutToExpireSoftwareLink.click();
  });

  it("should navigate to Software Companies page", async () => {
    const softwareCompaniesLink = await driver.findElement(
      By.linkText("Software Companies")
    );
    await softwareCompaniesLink.click();
  });

  it("should navigate to Add Software page", async () => {
    const addSoftwareLink = await driver.findElement(
      By.linkText("Add Softwares")
    );
    await addSoftwareLink.click();
  });

  it("should navigate to Software Purchase History page", async () => {
    const softwarePurchaseHistoryLink = await driver.findElement(
      By.linkText("Software Purchases")
    );
    await softwarePurchaseHistoryLink.click();
  });

  it("should navigate to Devices page", async () => {
    const devicesLink = await driver.findElement(By.linkText("Devices"));
    await devicesLink.click();

    const rmaButton = await driver.findElement(By.id("RMAbutton"));
    await rmaButton.click();

    const rmaReasonInput = await driver.findElement(By.id("RMAReason"));
    await rmaReasonInput.sendKeys("Broken Device");
    const saveRmaButton = await driver.findElement(By.id("saveRMA"));
    await saveRmaButton.click();
  });

  it("should navigate to Expired Devices page", async () => {
    const expiredDevicesLink = await driver.findElement(
      By.linkText("Expired Devices")
    );
    await expiredDevicesLink.click();
  });

  it("should navigate to About To Expire Devices page", async () => {
    const aboutToExpireDevicesLink = await driver.findElement(
      By.linkText("About To Expire")
    );
    await aboutToExpireDevicesLink.click();
  });

  it("should navigate to Device Companies page", async () => {
    const deviceCompaniesLink = await driver.findElement(
      By.linkText("Device Companies")
    );
    await deviceCompaniesLink.click();
  });

  it("should navigate to Add Device page", async () => {
    const addDeviceLink = await driver.findElement(By.linkText("Add Device"));
    await addDeviceLink.click();
  });

  it("should navigate to Device Purchase History page", async () => {
    const devicePurchaseHistoryLink = await driver.findElement(
      By.linkText("Device Purchases")
    );
    await devicePurchaseHistoryLink.click();
  });

  it("should navigate to RMA page", async () => {
    const rmaLink = await driver.findElement(By.linkText("RMA"));
    await rmaLink.click();

    const handleReturnButtonClick = await driver.findElement(
      By.id("handleReturnButtonClick")
    );
    await handleReturnButtonClick.click();
    await handleReturnButtonClick.click();
  });

  it("should navigate to Decommissioned Items page", async () => {
    const decommissionedItemsLink = await driver.findElement(
      By.linkText("decomissioned items")
    );
    await decommissionedItemsLink.click();
  });

  afterAll(async () => {
    await driver.quit();
  });
});
