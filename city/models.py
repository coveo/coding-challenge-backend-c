from django.db import models
import csv
import sys
import math
import json
from difflib import SequenceMatcher

# Create your models here.
class ScoreCalculator:
    def __init__(self, city, query):

        self.score_map = {
            "population": self.score_population,
            "latitude": self.score_latitude,
            "longitude": self.score_longitude,
            "q": self.score_name
        }

        self.query = query
        self.city = city

    def calculate_score(self):
        total_score = 0
        for k in self.query.keys():
            total_score += self.score_map[k]()

        return total_score / len(self.query)

    def score_population(self):
        desired_pop = int(self.query["population"])
        city_pop = self.city.population
        return self.normalized_diff(desired_pop, city_pop, City.MAX_POP, City.MIN_POP)

    def score_latitude(self):
        desired_lat = float(self.query["latitude"])
        city_lat = self.city.latitude
        return self.normalized_diff(desired_lat, city_lat, City.MAX_LAT, City.MIN_LAT)

    def score_longitude(self):
        desired_lon = float(self.query["longitude"])
        city_lon = self.city.longitude
        return self.normalized_diff(desired_lon, city_lon, City.MAX_LON, City.MIN_LON)

    def score_name(self):
        desired_name = self.query["q"]
        city_name = self.city.name
        return  SequenceMatcher(None, desired_name, city_name).ratio()

    def normalized_diff(self, true, desired, maximum, minimum):
        adjusted_true = self.normalize(true, maximum, minimum)
        adjusted_desired = self.normalize(desired, maximum, minimum)
        diff = abs(adjusted_true - adjusted_desired)
        return 1 / (1 + diff)

    def normalize(self, value, maximum, minimum):
        return float(value - minimum) / float(maximum - minimum)

class City(models.Model):
    MAX_POP = 0
    MIN_POP = 0
    MAX_LAT = 0
    MIN_LAT = 0
    MAX_LON = 0
    MIN_LON = 0

    name = models.TextField()
    latitude = models.FloatField()
    longitude = models.FloatField()
    population = models.IntegerField()

    def calculate_score(self, query):
        self.score = ScoreCalculator(self, query.copy()).calculate_score()
        return self.score

    def dictionary(self):
        return {
            "name": self.name,
            "latitude": self.latitude,
            "longitude": self.longitude,
            "population": self.population,
            "score": "{0:.1f}".format(self.score)
        }


def load_data():
    csv.field_size_limit(sys.maxsize)
    with open("./data/cities_canada-usa.tsv", "r") as f:
        reader = csv.reader(f, delimiter="\t")
        next(f)

        cities = []
        for row in reader:
            cities.append(build_city(row))

        City.objects.bulk_create(cities)

    set_max_min()


def build_city(row):
    _, _, name, _, lat, lon, _, _, _, _, _, _, _, _, pop, _, _, _, _ = row

    city = City(
        name=name,
        latitude=float(lat),
        longitude=float(lon),
        population=int(pop),
    )
    return city


def set_max_min():
    cities = City.objects.all()
    City.MAX_LON = cities.latest("longitude").longitude
    City.MIN_LON = cities.earliest("longitude").longitude
    City.MAX_LAT = cities.latest("latitude").latitude
    City.MIN_LAT = cities.earliest("latitude").latitude
    City.MAX_POP = cities.latest("population").population
    City.MIN_POP = cities.earliest("population").population
