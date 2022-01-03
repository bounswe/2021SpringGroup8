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
            deviceName="emulator-5556",
            app=os.path.join(os.getcwd(), "app-debug.apk"),
            appActivity="com.example.facespace.MainActivity",
            appPackage="com.example.signin_signup",
        )
        self.USER_NAME = "gravewalker"
        self.PASSWORD = "Aaaa1111"
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

    def tearDown(self):
        self.driver.quit()

    def test_goLoginPage(self):
        self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnIn").click()
        time.sleep(1)
        # driver.implicitly_wait(5)
        # driver.find_element_by_xpath("android.widget.Button[1]").click()
        # driver.find_element_by_accessibility_id("com.example.signin_signup:id/btnIn").click()
        self.assertEqual(
            self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnForgot")
                .get_attribute("text"), "Forgot Password?")

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
            self.driver.find_element(by=AppiumBy.ID, value="com.example.signin_signup:id/btnForgot")
                .get_attribute("text"), "Forgot Password?")

    


if __name__ == '__main__':
    unittest.main()
