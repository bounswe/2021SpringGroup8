import server
from threading import Thread
from time import sleep
import unittest
import sys
import string
import random
import requests
import json

def RandomLowerString(n):
    return "".join([random.choice(string.ascii_lowercase) for i in range(n)])

def RandomUpperString(n):
    return "".join([random.choice(string.ascii_uppercase) for i in range(n)])

def RandomNumberString(n):
    return "".join([random.choice([str(j) for j in range(10)]) for i in range(n)])



class BackendTest(unittest.TestCase):
    username = None
    password = None
    email = None
    name = None
    surname = None
    usertoken = None

    def test0_testsignup(self):
        self.__class__.username = RandomLowerString(10) + RandomUpperString(10) + RandomNumberString(10)
        self.__class__.password = RandomLowerString(5) + RandomUpperString(5) + RandomNumberString(5)
        self.__class__.email = RandomLowerString(10) + RandomNumberString(10) + "@unittest.com"
        self.__class__.name = RandomLowerString(10) 
        self.__class__.surname = RandomLowerString(10) 
        
        dict = {
            "username": self.__class__.username,
            "password": self.__class__.password,
            "email": self.__class__.email,
            "name": self.__class__.name,
            "surname": self.__class__.surname,
        }

        postreq = requests.post(f"http://127.0.0.1:8080/signup", data = dict)
        res = json.loads(postreq.text)
        self.assertEqual(res["@success"], 'True', "Can't signup")
        self.__class__.usertoken = res["@usertoken"]

    def test1_login(self): 
        dict = {
            "username": self.__class__.username,
            "password": self.__class__.password,
        }
        postreq = requests.post(f"http://127.0.0.1:8080/login", data = dict)
        res = json.loads(postreq.text)
        self.assertEqual(res["@success"], 'True', "Can't login")

    communityid = None

    def test2_createcommunity(self):
        dict = {
            "communityTitle": "uinttest_community_" + RandomLowerString(10),
            "description": "Created with backend unit test!",
            "@usertoken": self.__class__.usertoken,
        }
        postreq = requests.post(f"http://127.0.0.1:8080/createcommunity", data = dict)
        res = json.loads(postreq.text)
        self.assertEqual(res["@success"], 'True', "Can't create a community")
        self.__class__.communityid = res["@return"]["id"]

    def test3_sub2community(self):
        dict = {
            "@usertoken": self.__class__.usertoken,
            "communityId": self.__class__.communityid,
        }
        postreq = requests.post(f"http://127.0.0.1:8080/subscribetocommunity", data = dict)
        res = json.loads(postreq.text)
        self.assertEqual(res["@success"], 'False', "Error sub!")
        self.assertEqual(res["@error"], 'Already subscribed!', "Error sub!")
    
    
    def test4_createdatatype(self):
        dict = {
                "@usertoken": self.__class__.usertoken,
                "communityId": self.__class__.communityid,
                "datatypename": "unit test data type 0",
                "datatypefields": """{"unit test field 0":"bool", "unit test field 1":"str"}"""
            }
        postreq = requests.post(f"http://127.0.0.1:8080/createdatatype", data = dict)
        res = json.loads(postreq.text)
        self.assertEqual(res["@success"], 'True', "Can't create data type!")
    
    def test5_sendpost(self):
        dict = {
                "@usertoken": self.__class__.usertoken,
                "communityId": self.__class__.communityid,
                "title": "unit test post 0",
                "datatypename": "unit test data type 0",
                "datatypevalues": """{"unit test field 0":true, "unit test field 1":"unit test string 0"}"""
            }
        postreq = requests.post(f"http://127.0.0.1:8080/submitpost", data = dict)
        res = json.loads(postreq.text)
        
        self.assertEqual(res["@success"], 'True', "Can't send post!")
    
    def test9_deletecommunity(self):
        dict = {
            "@usertoken": self.__class__.usertoken,
            "communityId": self.__class__.communityid,
        }
        postreq = requests.post(f"http://127.0.0.1:8080/deletecommunity", data = dict)
        res = json.loads(postreq.text)
        self.assertEqual(res["@success"], 'True', "Can't login")

    def testz_print(self):
        print("username:", self.__class__.username)
        print("password:",self.__class__.password)
        print("email:",self.__class__.email)
        print("name:",self.__class__.name)
        print("surname:",self.__class__.surname)
        print("usertoken:",self.__class__.usertoken)
        print("communityid:",self.__class__.communityid)

        self.assertEqual("True", 'True', "testz_print!")


def StartServer():
    server.run()
    sys.exit(0)

def setUp():
    serverthread = Thread(target = StartServer) 
    serverthread.daemon = True
    serverthread.start()
                
if __name__ == "__main__":
    setUp()
    sleep(1)
    unittest.main(BackendTest())
    server.RUNNING_SERVER.shutdown()
    sys.exit(0)