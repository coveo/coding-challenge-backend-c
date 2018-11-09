using System;
using System.Collections.Generic;
using System.Linq;
using GeoCoordinatePortable;

namespace CitiesSuggestions.CoreLogic
{
    public class ExactNameScore : IScoringStrategy
    {
        public List<double> ComputeScore(List<CityData> results, string query, GeoCoordinate location)
        {
            if (results.Count == 0) return new List<double>();
            if (results.Count == 1) return new List<double>() { 1.0 };

            if (string.IsNullOrEmpty(query))
                throw new InvalidOperationException("The query parameter is null or empty; cannot compute best string match");

            int queryLength = query.Length;
            var fractionMatcing = results.Select(c => queryLength / c.Name.Length);
            var maxFrac = fractionMatcing.Max();
            var scores = fractionMatcing.Select(p => p == maxFrac ? 1.0 : p / maxFrac).ToList();
            return scores;
        }
    }
}
