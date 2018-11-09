using System;
using System.Collections.Generic;
using System.Linq;
using GeoCoordinatePortable;

namespace CitiesSuggestions.CoreLogic
{
    public class PopulationScore : IScoringStrategy
    {
        public List<double> ComputeScore(List<CityData> results, string query, GeoCoordinate location)
        {
            if (results.Count == 0) return new List<double>();
            if (results.Count == 1) return new List<double>() { 1.0 };

            var relativePop = results.Select(c => Math.Log(Math.Max(c.Population , 3))); // don't accept lower than 3 persons, or else the Log will return values smaller than 1 and all hell breaks loose
            var maxPop = relativePop.Max();
            if (maxPop < 1) maxPop = 1;
            var scores = relativePop.Select(p => p==maxPop ? 1.0 :  p / maxPop).ToList();
            return scores;
        }
    }
}
