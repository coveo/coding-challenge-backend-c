using System;
using Xunit;

namespace AutoCompleteUnitTests
{
    public class DataHelperTest
    {
        [Fact]
        public void SomeDataWasLoaded()
        {
            var data = CitiesSuggestions.DataHelper.LoadData();
            Assert.True(data.Count > 0);
        }
    }
}
