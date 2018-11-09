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

        public static IScoringStrategy CreateScoringStrategy(string name)
        {
            switch(name)
            {
                case "distance": return new DistanceScore();
                case "population": return new PopulationScore();
                case "exactname": return new ExactNameScore();
                default: return null;
            }
        }
    }
}
