package com.github.masato29isle.sample;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Selenium サンプルテスト
 */
class SeleniumSampleTest {

    @BeforeAll
    static void setup() {
        // 使用するブラウザに応じて変更する
        Configuration.browser = WebDriverRunner.CHROME;
        //WebDriverRunner.FIREFOX;
        //WebDriverRunner.EDGE;

        // ScreenShot保存フォルダの設定
        Configuration.reportsFolder = "build/reports/tests";

        // 終了時にブラウザを開いたままにするかどうか(デフォルトOFF)
        Configuration.holdBrowserOpen = false;
    }

    @AfterAll
    static void tearDown() {
        closeWebDriver();
    }

    @Test
    void openGooglePageAndSearchTest() {
        // Googleのページを開く
        open("https://www.google.com/");

        // "selenium document"を入力して検索
        $(By.name("q")).val("selenium document").pressEnter();

        // 検索結果の中から"The Selenium Browser Automation Project :: Documentation ..."と記載されたリンクをクリック
        // Googleの検索結果のページ名はh3タグで表示されている -> h3タグの要素を取得し、その中から該当するページ名をフィルタリングする
        elements(By.tagName("h3")).stream()
                .filter(element -> element.text().equals("The Selenium Browser Automation Project :: Documentation ..."))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("要素が見つかりません"))
                .click();

        // 表示ページのScreenshotを取得
        screenshot("selenium-test1");

        // 遷移したページの中に"The Selenium Browser Automation Project"の文言が含まれていること
        $("#the-selenium-browser-automation-project").shouldHave(text("The Selenium Browser Automation Project"));
    }
}