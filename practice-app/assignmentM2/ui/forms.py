from django import forms

class FlagForm(forms.Form):
    country_name = forms.CharField(label="country name", max_length=50)

class CurrencyForm(forms.Form):
    cur_from = forms.CharField(label="from", max_length=50)
    cur_to = forms.CharField(label="to", max_length=50)

class DistanceForm(forms.Form):
    country_from = forms.CharField(label="your country", max_length=50)
    city_from = forms.CharField(label="your city", max_length=50)
    county_from = forms.CharField(label="your county", max_length=50)
    country_to = forms.CharField(label="event country", max_length=50)
    city_to = forms.CharField(label="event city", max_length=50)
    county_to = forms.CharField(label="event county", max_length=50)

class MealForm(forms.Form):
    meal_name = forms.CharField(label="meal name", max_length=50)

class QuoteForm(forms.Form):
    first_name = forms.CharField(label="first name", max_length=50)
    last_name = forms.CharField(label="last name", max_length=50)
