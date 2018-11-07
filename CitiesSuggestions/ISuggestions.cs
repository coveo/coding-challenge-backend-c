using System;
using System.Collections.Generic;
using System.Text;

namespace CitiesSuggestions
{
    public interface ISuggestions
    {
        SuggestionsResult GetSuggestions(string partialMatch);

        SuggestionsResult GetSuggestions(string partialMatch, Tuple<double, double> position);
    }
}
