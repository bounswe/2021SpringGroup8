# How to Run main.py for testing

- There are 9 separate tests in main.py. They can be invoked separately.
- To run that, you first have to install Appium Desktop. It can be installed by following 
that link [Appium](https://appium.io/downloads.html). Then start the Appium server in default settings.
- After that you have to start an android emulator, which will perform automated tests for the app.
- After starting emulator, you need to find your device name by typing below in your terminal.
````
adb devices
````
- Add your device name in deviceName key of desired_caps. For example, 
```python
desired_caps = dict(
            # ... , 
            deviceName="[YOUR_DEVICE_NAME]"
            # , ...
        )
```
- PyCharm, automatically will suggest installing requirements to run main.py, but they can be installed
manually also, by typing below to install requirements.
````
pip3 install -r requirements.txt
````
- After that you can run any test you want in main.py.