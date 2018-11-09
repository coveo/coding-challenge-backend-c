using System.Collections.Generic;
using Xunit;

namespace AutoCompleteUnitTests
{
    public class PopulationScoreTest
    {
        [Fact]
        void WhenNoElement_GetEmptyResult()
        {
            var s = new CitiesSuggestions.CoreLogic.PopulationScore();
            var r = s.ComputeScore(new List<CitiesSuggestions.CityData>(), "A", new GeoCoordinatePortable.GeoCoordinate());
            Assert.Empty(r);
        }

        [Fact]
        void WhenOneElement_GetPerfectScore()
        {
            var cities = new List<CitiesSuggestions.CityData>();
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abc", Population = 1234, Location = new GeoCoordinatePortable.GeoCoordinate(2, 2) });
            var s = new CitiesSuggestions.CoreLogic.PopulationScore();
            var r = s.ComputeScore(cities, "A", new GeoCoordinatePortable.GeoCoordinate(4, 4));
            Assert.Equal(1.0, r[0]);
        }

        [Fact]
        void WhenTwoElements_OneHasPerfectScore()
        {
            var cities = new List<CitiesSuggestions.CityData>();
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abc", Population = 1234, Location = new GeoCoordinatePortable.GeoCoordinate(2, 2) });
            cities.Add(new CitiesSuggestions.CityData() { Name = "DEF", Population = 2345, Location = new GeoCoordinatePortable.GeoCoordinate(3, 3) });
            var s = new CitiesSuggestions.CoreLogic.PopulationScore();
            var r = s.ComputeScore(cities, "A", new GeoCoordinatePortable.GeoCoordinate(3.1, 3.1));
            Assert.Equal(1.0, r[1]);
            Assert.True(r[1] > r[0]);
        }

        [Fact]
        void WhenSamePopulation_GetSameScore()
        {
            var cities = new List<CitiesSuggestions.CityData>();
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abc", Population = 1234, Location = new GeoCoordinatePortable.GeoCoordinate(4, 2) });
            cities.Add(new CitiesSuggestions.CityData() { Name = "DEF", Population = 1234, Location = new GeoCoordinatePortable.GeoCoordinate(4, 2) });
            cities.Add(new CitiesSuggestions.CityData() { Name = "Hij", Population = 2345, Location = new GeoCoordinatePortable.GeoCoordinate(3, 3) });
            var s = new CitiesSuggestions.CoreLogic.PopulationScore();
            var r = s.ComputeScore(cities, "A", new GeoCoordinatePortable.GeoCoordinate(3.1, 3.1));
            Assert.True(r[0] == r[1]);
        }

        [Fact]
        void WhenVaryingInputs_ScoreInRange()
        {
            var cities = new List<CitiesSuggestions.CityData>();
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abc", Population = 1, Location = new GeoCoordinatePortable.GeoCoordinate(1, 2) });
            cities.Add(new CitiesSuggestions.CityData() { Name = "DEF", Population = 10, Location = new GeoCoordinatePortable.GeoCoordinate(2, 2) });
            cities.Add(new CitiesSuggestions.CityData() { Name = "Hij", Population = 100, Location = new GeoCoordinatePortable.GeoCoordinate(3, 3) });
            cities.Add(new CitiesSuggestions.CityData() { Name = "k", Population = 1234, Location = new GeoCoordinatePortable.GeoCoordinate(4, 3) });
            cities.Add(new CitiesSuggestions.CityData() { Name = "l", Population = 10234, Location = new GeoCoordinatePortable.GeoCoordinate(10, 3) });
            cities.Add(new CitiesSuggestions.CityData() { Name = "mn", Population = 100234, Location = new GeoCoordinatePortable.GeoCoordinate(9, 3) });
            cities.Add(new CitiesSuggestions.CityData() { Name = "op", Population = 99888777, Location = new GeoCoordinatePortable.GeoCoordinate(8, 3) });
            cities.Add(new CitiesSuggestions.CityData() { Name = "zz", Population = 0, Location = new GeoCoordinatePortable.GeoCoordinate(8, 3) });
            var s = new CitiesSuggestions.CoreLogic.PopulationScore();
            var r = s.ComputeScore(cities, "A", new GeoCoordinatePortable.GeoCoordinate(3.1416, 3.1415926535));

            foreach (var score in r)
            {
                Assert.True(score >= 0);
                Assert.True(score <= 1.0);
            }
        }
    }
}
