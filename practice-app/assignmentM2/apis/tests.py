import json
from django.http.response import HttpResponse
from django.test import TestCase, Client, testcases
from django.urls import reverse


# initialize the APIClient app
client = Client()


class RandomMealRecipeTests(TestCase):

    def setUp(self):
        pass

    def test_default_connection(self):
        response: HttpResponse = client.get(reverse('getrandommealrecipe'))
        content = response.content.decode()
        self.assertGreaterEqual(len(content), 10)


class MealRecipeByName(TestCase):

    def setUp(self):
        pass

    def test_default_connection(self):
        response: HttpResponse = client.get(reverse('getmealrecipebyname'), {'s': 'cake'})
        content = response.content.decode()
        print(type(content))
        self.assertGreaterEqual(len(content), 10)

    def test_null_result(self):
        response: HttpResponse = client.get(reverse('getmealrecipebyname'), {'s': '.'})
        content = response.content.decode()
        print(type(content))
        self.assertEqual(content, "null")


# initialize the APIClient app


class CurrencyTests(TestCase):

    def setUp(self):
       pass

    def test_default_connection(self):
        response : HttpResponse = client.get(reverse('getcurrencies'), {'from': 'try', 'to': 'try'})
        content = response.content.decode()
        self.assertEqual(content, "1")
    
    
    def test_currency_exchange(self):
        response1 : HttpResponse = client.get(reverse('getcurrencies'), {'from': 'try', 'to': 'usd'})
        response2 : HttpResponse = client.get(reverse('getcurrencies'), {'from': 'usd', 'to': 'try'})
        content1 = response1.content.decode()
        content2 = response2.content.decode()

        res = float(content1)*float(content2)
        self.assertEqual(round(res,3), 1)


class FlagTests(TestCase):
    
    def setUp(self):
       pass

    def test_default_connection(self):
        response : HttpResponse = client.get(reverse('getflag'), {'country': 'tr'})
        content = response.content
        self.assertGreaterEqual(len(content), 10)
    

