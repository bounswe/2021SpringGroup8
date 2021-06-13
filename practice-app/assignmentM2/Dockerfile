# For more information, please refer to https://aka.ms/vscode-docker-python
FROM python:3.8-slim-buster

EXPOSE 8000

# Keeps Python from generating .pyc files in the container
ENV PYTHONDONTWRITEBYTECODE=1

# Turns off buffering for easier container logging
ENV PYTHONUNBUFFERED=1

# Install pip requirements
COPY requirements.txt .
RUN python -m pip install -r requirements.txt
ENV SECRET_KEY django-insecure-hjpwmczb)+#nm0l_$jka+#m6y!v%wdbph!dk-jmip393^@s%ru
ENV CURRENCY_API_KEY 55d560f06e174022b414
ENV DISTANCE_API_KEY 2a41d6ab37f44a2cb85b254b57123393&q=

WORKDIR /app
COPY . /app

RUN python manage.py makemigrations
RUN python manage.py migrate
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000" ]
