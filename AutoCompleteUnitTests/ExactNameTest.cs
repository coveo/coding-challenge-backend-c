using System;
using System.Collections.Generic;
using Xunit;

namespace AutoCompleteUnitTests
{
    public class ExactNameTest
    {
        [Fact]
        void WhenNoElement_GetEmptyResult()
        {
            var s = new CitiesSuggestions.CoreLogic.ExactNameScore();
            var r = s.ComputeScore(new List<CitiesSuggestions.CityData>(), "A", new GeoCoordinatePortable.GeoCoordinate());
            Assert.Empty(r);
        }

        [Fact]
        void WhenOneElement_GetPerfectScore()
        {
            var cities = new List<CitiesSuggestions.CityData>();
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abc" });
            var s = new CitiesSuggestions.CoreLogic.ExactNameScore();
            var r = s.ComputeScore(cities, "A", new GeoCoordinatePortable.GeoCoordinate(4, 4));
            Assert.Equal(1.0, r[0]);
        }

        [Fact]
        void WhenNoQueryString_Throws()
        {
            var cities = new List<CitiesSuggestions.CityData>();
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abc" });
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abcdef" });
            var s = new CitiesSuggestions.CoreLogic.ExactNameScore();
            Assert.Throws<InvalidOperationException>(() => s.ComputeScore(cities, "", new GeoCoordinatePortable.GeoCoordinate()));
        }

        [Fact]
        void WhenPerfectMatch_GetPerfectScore()
        {
            var cities = new List<CitiesSuggestions.CityData>();
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abcdef" });
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abc" });
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abcdefghi" });
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abcxyz" });
            var s = new CitiesSuggestions.CoreLogic.ExactNameScore();
            var r = s.ComputeScore(cities, "Abc", new GeoCoordinatePortable.GeoCoordinate(4, 4));
            Assert.Equal(1.0, r[1]);
        }

        [Fact]
        void WhenVariousLength_ScoreInRange()
        {
            var cities = new List<CitiesSuggestions.CityData>();
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abcdef" });
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abc" });
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abcdefghi" });
            cities.Add(new CitiesSuggestions.CityData() { Name = "Abcxyz" });
            var s = new CitiesSuggestions.CoreLogic.ExactNameScore();
            var r = s.ComputeScore(cities, "Abc", new GeoCoordinatePortable.GeoCoordinate(4, 4));
            foreach(var score in r)
            {
                Assert.True(score >= 0);
                Assert.True(score <= 1.0);
            }
        }
    }
}
