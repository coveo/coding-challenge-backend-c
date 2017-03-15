using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CitySuggestion
{
    public static class CityInformationHelper
    {
        public const int NewCityPosition = 3;
        public const int CityCountryPosition = 1;
        public const int CityRegionPosition = 2;

        public static IEnumerable<string> ConvertStringToCityInfoStrings(string cityInfo)
        {
            var partial = string.Join("", cityInfo.Split(new string[] { "?([" }, StringSplitOptions.None));
            var cities = string.Join("", partial.Split(new string[] { "\"" }, StringSplitOptions.None));
            return ParseCityInfo(cities).Where(c => c != null).Select(c => c.ToString() + "\n");
        }

        private static List<CityInformation> ParseCityInfo(string cityInfo)
        {
            var info = cityInfo.Split(',');
            var results = new List<CityInformation>();
            for (var cityNameIndex = 0; cityNameIndex <= info.Length - NewCityPosition; cityNameIndex += NewCityPosition)
            {
                results.Add(new CityInformation(info[cityNameIndex], info[cityNameIndex + CityCountryPosition], info[cityNameIndex + CityRegionPosition]));
            }
            return results;
        }
    }
}
