using CitiesSuggestions;
using System;

namespace CitiesSuggestions.CoreLogic
{
    public static class Factory
    {
        public static ISuggestions CreateWithDefaultCities()
        {
            var data = DataHelper.LoadData();
            var concrete = new NameOnlyMatcher(data);
            return concrete;
        }
    }
}
