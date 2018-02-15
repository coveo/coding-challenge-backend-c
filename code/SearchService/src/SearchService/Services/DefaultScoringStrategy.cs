using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using GeoCoordinatePortable;
using SearchService.Model;
using SearchService.Services.Interfaces;

namespace SearchService.Services
{
    public class DefaultScoringStrategy : IScoringStrategy
    {
        private double _textWeight = 0.5;
        private double _distanceWeight = 0.5;
        private double _worstDistanceValue = 10000000; //10 000 km and more is like a zero score for ditance

        public double Calculate(string resultName, double? resultLongitude, double? resultLatitude, string searchName, double? searchLongitude, double? searchLatitude)
        {
            var textScore = string.IsNullOrEmpty(searchName) || !resultName.ToLower().Contains(searchName.ToLower()) ? 0 : 1; //pretty simple but could be more complex with better search (not just contain method)
            double distanceScore = 0;
            if (resultLongitude != null && resultLatitude != null && searchLongitude != null && searchLatitude != null)
            {
                var distance = GetDistance(resultLongitude.Value, resultLatitude.Value,
                    searchLongitude.Value,
                    searchLatitude.Value);
                distanceScore = 1 - distance / _worstDistanceValue;
            }

            return textScore * _textWeight + _distanceWeight * distanceScore;
        }

        private double GetDistance(double longDecimal, double latDecimal, double longDecimal2, double latDecimal2)
        {
            var sCoord = new GeoCoordinate(latDecimal, longDecimal);
            var eCoord = new GeoCoordinate(latDecimal2, longDecimal2);

            return sCoord.GetDistanceTo(eCoord);
        }
    }
}
