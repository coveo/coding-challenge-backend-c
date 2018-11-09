using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CitiesSuggestions.CoreLogic
{
    public class NameOnlyMatcher : ISuggestions
    {
        ICollection<CityData> Dataset { get; set; }

        public NameOnlyMatcher(ICollection<CityData> dataset)
        {
            Dataset = dataset;
        }

        public SuggestionsResult GetSuggestions(string partialMatch)
        {            
            return GetSuggestionsInternal(partialMatch, new GeoCoordinatePortable.GeoCoordinate(), new List<string>() { "population", "exactname" });
        }

        public SuggestionsResult GetSuggestions(string partialMatch, Tuple<double, double> position)
        {
            return GetSuggestionsInternal(partialMatch, new GeoCoordinatePortable.GeoCoordinate( position.Item1, position.Item2), new List<string>() { "population", "exactname", "distance" });
        }

        private SuggestionsResult GetSuggestionsInternal(string partialMatch, GeoCoordinatePortable.GeoCoordinate location, List<string> scoringStrategies)
        {
            if (string.IsNullOrEmpty(partialMatch)) // no input ==> no output!
                return new SuggestionsResult();

            var matchingCities = Dataset.Where(c => c.Name.StartsWith(partialMatch, StringComparison.CurrentCultureIgnoreCase)).ToList();
            var scores = new List<double>( matchingCities.Select(mc => 1.0) );

            foreach (var strategyName in scoringStrategies)
            {
                var strat = Factory.CreateScoringStrategy(strategyName);
                var newScores = strat.ComputeScore(matchingCities, partialMatch, location);
                scores = scores.Zip(newScores, (oldScore, newScore) => oldScore * newScore).ToList();
            }

            var resultItems = matchingCities.Zip(scores, (c, score) => new SuggestionsResultItem() { name = c.Name, latitude = c.Location.Latitude.ToString(), longitude = c.Location.Longitude.ToString(), score = score })
                                            .OrderByDescending(mc => mc.score);
            var result = new SuggestionsResult();
            foreach (var i in resultItems)
            {
                result.AddItem(i);
            }
            return result;
        }
    }
}
