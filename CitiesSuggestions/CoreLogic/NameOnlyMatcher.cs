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
            if(string.IsNullOrEmpty(partialMatch)) // no input ==> no output!
                return new SuggestionsResult();

            var matchingCities = Dataset.Where(c => c.Name.StartsWith(partialMatch, StringComparison.CurrentCultureIgnoreCase)).Select(c => new SuggestionsResultItem() { name = c.Name, latitude = c.Location.Latitude.ToString(), longitude = c.Location.Longitude.ToString(), score = 1.0 }); ;
            var result = new SuggestionsResult();
            foreach(var c in matchingCities)
            {
                result.AddItem(c);
            }
            return result;
        }

        public SuggestionsResult GetSuggestions(string partialMatch, Tuple<double, double> position)
        {
            throw new NotImplementedException();
        }
    }
}
