using System;
using System.Collections.Generic;
using System.Linq;
using GeoCoordinatePortable;

namespace CitiesSuggestions.CoreLogic
{
    public class DistanceScore : IScoringStrategy
    {
        public List<double> ComputeScore(List<CityData> results, string query, GeoCoordinate location)
        {
            if (results.Count == 0) return new List<double>();
            if (results.Count == 1) return new List<double>() { 1.0 };

            var distances = results.Select(c => Math.Log(Math.Max(location.GetDistanceTo(c.Location), 3))); // don't accept lower than 3 meters, or else the Log will return values smaller than 1 and all hell breaks loose
            var minDistance = distances.Min();
            var scores = distances.Select(d => minDistance / d).ToList();
            return scores;
        }
    }
}
