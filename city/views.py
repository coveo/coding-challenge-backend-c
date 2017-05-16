from django.shortcuts import render
from django.http import JsonResponse
import sys

from city.models import City


def suggestions(request):
    try:
        query = request.GET.copy()
        cities = City.objects.all()
        n_results = get_number_of_results(query)

        handle_closeto(query, cities)

        top_cities = get_top_cities(cities, query)

        return build_response(top_cities[:n_results], "Query Success")

    except Exception as e:
        return build_response([], str(e))


def get_number_of_results(query):
    try:
        return int(query.pop("n")[0]) if "n" in query else 10
    except:
        raise Exception("Invalid n parameter")


def handle_closeto(query, cities):
    if "closeto" in query:
        close_city_name = query.pop("closeto")[0]
        close_city = City.objects.filter(name__iexact=close_city_name).first()
        query["longitude"] = close_city.longitude
        query["latitude"] = close_city.latitude
        cities.filter(pk=close_city.pk)

def get_top_cities(cities, query):
    return sorted(cities, key=lambda x: x.calculate_score(query), reverse=True)

def build_response(cities, message):

    response = {
        "suggestions": [c.dictionary() for c in cities],
        "message": message
    }

    return JsonResponse(response)
