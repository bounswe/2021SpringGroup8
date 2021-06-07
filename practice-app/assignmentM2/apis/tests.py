import json
from django.http.response import HttpResponse
from django.test import TestCase, Client, testcases
from django.urls import reverse

# initialize the APIClient app
client = Client()


class MealRecipeTests(TestCase):

    def setUp(self):
        pass

    def test_default_connection(self):
        response: HttpResponse = client.get(reverse('getmealrecipe'))
        content = response.content.decode()
        self.assertGreaterEqual(len(content), 10)
