using System.Collections.Generic;
using Xunit;

namespace AutoCompleteUnitTests
{
    public class MatcherTest
    {
        List<CitiesSuggestions.CityData> DefaultTestData { get; set; }

        public MatcherTest()
        {
            DefaultTestData = new List<CitiesSuggestions.CityData>();
            DefaultTestData.Add(new CitiesSuggestions.CityData() { Id = 1, Name = "Acton Value", Location = new GeoCoordinatePortable.GeoCoordinate(45.65007, -72.56582), Population = 5135, Admin1Code = "10", CountryCode = "CA" });
            DefaultTestData.Add(new CitiesSuggestions.CityData() { Id = 2, Name = "Alma", Location = new GeoCoordinatePortable.GeoCoordinate(48.55009, -71.6491), Population = 17918, Admin1Code = "10", CountryCode = "CA" });
            DefaultTestData.Add(new CitiesSuggestions.CityData() { Id = 3, Name = "Angus", Location = new GeoCoordinatePortable.GeoCoordinate(44.31681, -79.88295), Population = 10269, Admin1Code = "08", CountryCode = "CA" });
            DefaultTestData.Add(new CitiesSuggestions.CityData() { Id = 4, Name = "Anmore", Location = new GeoCoordinatePortable.GeoCoordinate(49.31637, -122.85263), Population = 126456, Admin1Code = "02", CountryCode = "CA" });
        }

        [Theory]
        [InlineData((string)null)]
        [InlineData("")]
        public void WhenEmptyInput_GetEmptyResults(string input)
        {
            var matcher = new CitiesSuggestions.CoreLogic.NameOnlyMatcher(DefaultTestData);
            var result = matcher.GetSuggestions(input);
            Assert.Empty(result.suggestions);
        }

        [Theory]
        [InlineData("A")]
        [InlineData("Al")]
        [InlineData("An")]
        [InlineData("Alma")]
        public void WhenMatchingInput_GetSomeResults(string input)
        {
            var matcher = new CitiesSuggestions.CoreLogic.NameOnlyMatcher(DefaultTestData);
            var result = matcher.GetSuggestions(input);
            Assert.NotEmpty(result.suggestions);
        }

        [Theory]
        [InlineData("B")]
        [InlineData("Alx")]
        [InlineData("Z")]
        [InlineData("gus")]
        public void WhenNoMatchingInput_GetEmptyResults(string input)
        {
            var matcher = new CitiesSuggestions.CoreLogic.NameOnlyMatcher(DefaultTestData);
            var result = matcher.GetSuggestions(input);
            Assert.Empty(result.suggestions);
        }
    }
}
