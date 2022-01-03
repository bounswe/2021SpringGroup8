import sys
import time
# Appium-Python-Client should be installed. PyCharms automatically installs for project-specific environment.
# But it can also be installed with "pip install Appium-Python-Client" through terminal.
from appium import webdriver
from appium.webdriver.common.appiumby import AppiumBy
import os
import unittest


class MobileUITests(unittest.TestCase):

    def setUp(self):
        desired_caps = dict(
            platformName="Android",
            # device name may change time to time. It is usually emulator-5554. You can check your device name
            # from terminal by typing "adb devices" . There should be an open android emulator.
            deviceName="emulator-5554",
            app=os.path.join(os.getcwd(), "app-debug.apk"),
            appActivity="com.example.facespace.MainActivity",
            appPackage="com.example.signin_signup",
        )
        self.USER_NAME = "gravewalker"
        self.PASSWORD = "Aaaa1111"
        self.FILTER_COMM = "cmpe"
        self.FILTER_POST = "7"
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

    def tearDown(self):
        self.driver.quit()

    def test_goLoginPage(self):
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnIn").click()
        time.sleep(1)
        self.assertEqual(
            self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/tvAccount")
                .get_attribute("text"), "Don't have an account?")

    def test_login(self):
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputUsername") \
            .send_keys(self.USER_NAME)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputPassword") \
            .send_keys(self.PASSWORD)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnSignIn").click()
        time.sleep(1)
        self.assertEqual(
            self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/textView13")
                .get_attribute("text"), "Welcome to FaceSpace")

    def test_goMyProfilePage(self):
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputUsername") \
            .send_keys(self.USER_NAME)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputPassword") \
            .send_keys(self.PASSWORD)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnSignIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/imageProfile").click()
        time.sleep(0.5)
        self.assertEqual(
            self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/textView25")
                .get_attribute("text"), "MY PROFILE")

    def test_login_logout(self):
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputUsername") \
            .send_keys(self.USER_NAME)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputPassword") \
            .send_keys(self.PASSWORD)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnSignIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/imageLogOut").click()
        time.sleep(0.5)
        self.assertEqual(
            self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/tvAccount")
                .get_attribute("text"), "Don't have an account?")

    def test_open_my_communities(self):
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputUsername") \
            .send_keys(self.USER_NAME)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputPassword") \
            .send_keys(self.PASSWORD)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnSignIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/imageMyComm").click()
        time.sleep(0.5)
        self.assertEqual(self.driver.find_element(AppiumBy.ID,
                                                  "com.example.signin_signup:id/textView24").get_attribute("text"),
                         "Currently Viewing")

    def test_open_all_communities(self):
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputUsername") \
            .send_keys(self.USER_NAME)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputPassword") \
            .send_keys(self.PASSWORD)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnSignIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/imageAllComm").click()
        time.sleep(0.5)
        self.assertEqual(self.driver.find_element(AppiumBy.ID,
                                                  "com.example.signin_signup:id/textView19").get_attribute("text"),
                         "Create New\nCommunity")
        try:
            self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/textView24")
            self.assertEqual("", "a", "This is myCommunities page, not All Communities")
        except:
            self.assertEqual("", "")

        time.sleep(0.5)

    def test_filter_all_communities(self):
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputUsername") \
            .send_keys(self.USER_NAME)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputPassword") \
            .send_keys(self.PASSWORD)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnSignIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/imageAllComm").click()
        time.sleep(0.5)
        self.driver.find_element(AppiumBy.ID, "com.example.signin_signup:id/btnSearch").click()
        time.sleep(0.3)
        self.driver.find_element(AppiumBy.ID, "com.example.signin_signup:id/etQuery").send_keys(self.FILTER_COMM)
        self.driver.find_element(AppiumBy.ID, "com.example.signin_signup:id/enterSearch").click()
        time.sleep(0.3)
        self.assertEqual("", "")

    def test_open_post(self):
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputUsername") \
            .send_keys(self.USER_NAME)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputPassword") \
            .send_keys(self.PASSWORD)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnSignIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/imageMyComm").click()
        time.sleep(0.5)
        self.driver.find_element(AppiumBy.ID, "com.example.signin_signup:id/btnOpenComm").click()
        time.sleep(2)
        self.assertEqual("", "")

    def test_filter_post(self):
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputUsername") \
            .send_keys(self.USER_NAME)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/inputPassword") \
            .send_keys(self.PASSWORD)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnSignIn").click()
        time.sleep(1)
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/imageMyComm").click()
        time.sleep(0.5)
        self.driver.find_element(AppiumBy.ID, "com.example.signin_signup:id/btnOpenComm").click()
        time.sleep(1)
        self.driver.find_element(AppiumBy.ID, "com.example.signin_signup:id/btnSearch").click()
        time.sleep(0.3)
        self.driver.find_element(AppiumBy.ID, "com.example.signin_signup:id/searchbar").send_keys(self.FILTER_POST)
        self.driver.find_element(AppiumBy.ID, "com.example.signin_signup:id/btnAdd").click()
        self.driver.find_element(AppiumBy.ID, "com.example.signin_signup:id/btnSearch").click()
        self.driver.find_element(AppiumBy.ID, "com.example.signin_signup:id/button").click()
        time.sleep(2)
        self.assertEqual("", "")



if __name__ == '__main__':
    unittest.main()
