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
