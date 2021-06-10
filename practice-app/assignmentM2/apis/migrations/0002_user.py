# Generated by Django 3.2.4 on 2021-06-10 11:44

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('apis', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=30)),
                ('username', models.CharField(max_length=30)),
                ('email', models.CharField(max_length=250)),
                ('isActive', models.BooleanField(default=True)),
                ('password', models.CharField(max_length=30)),
            ],
        ),
    ]