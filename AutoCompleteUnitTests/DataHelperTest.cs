using System;
using Xunit;

namespace AutoCompleteUnitTests
{
    public class DataHelperTest
    {
        [Fact]
        public void SomeDataWasLoaded()
        {
            var data = AutoComplete.data.DataHelper.LoadData(@"cities_canada-usa.tsv");
            var numberOfColumns = data.Columns.Count;
            var numberOfRows = data.Rows.Count;
            Assert.True(numberOfColumns > 0);
            Assert.True(numberOfRows > 0);
        }
    }
}
