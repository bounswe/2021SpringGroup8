from django import forms

from .models import Event


class EventCreateForm(forms.Form):
    title = forms.CharField(widget=forms.TextInput(attrs={
        'placeholder': 'Your event title'}))
    description = forms.CharField(widget=forms.Textarea(attrs={
        'placeholder': 'Your event description'}))
    city_name = forms.CharField(widget=forms.TextInput(attrs={
        'placeholder': 'Your event city name',
        'rows': 5,
        'cols': 20
    }))
