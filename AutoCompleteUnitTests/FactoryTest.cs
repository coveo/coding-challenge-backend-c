using System;
using System.Collections.Generic;
using System.Text;
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
    }
}
