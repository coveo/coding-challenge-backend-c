using System.Collections.Generic;

namespace CitiesSuggestions
{
    public interface IScoringStrategy
    {
        List<double> ComputeScore(List<CityData> results, string query, GeoCoordinatePortable.GeoCoordinate location);
    }
}
