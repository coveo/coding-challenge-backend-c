using Xunit;

namespace AutoCompleteUnitTests
{
    public class FactoryTest
    {
        [Fact]
        public void WhenCreateDefault_GetValidObject()
        {
            var obj = CitiesSuggestions.CoreLogic.Factory.CreateWithDefaultCities();
            Assert.NotNull(obj);
        }

        [Theory]
        [InlineData("")]
        [InlineData(null)]
        [InlineData("foobar")]
        public void WhenInvalidStrategy_ReturnNull(string name)
        {
            var obj = CitiesSuggestions.CoreLogic.Factory.CreateScoringStrategy(name);
            Assert.Null(obj);
        }

        [Theory]
        [InlineData("distance")]
        [InlineData("population")]
        [InlineData("exactname")]
        public void WhenValidStrategy_ReturnValidObject(string name)
        {
            var obj = CitiesSuggestions.CoreLogic.Factory.CreateScoringStrategy(name);
            Assert.NotNull(obj);
        }
    }
}
