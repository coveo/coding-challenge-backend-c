namespace CitiesSuggestions
{
    public class CityData
    {
        public string Name { get; set; }

        public int Id { get; set; }

        public int Population { get; set; }

        public GeoCoordinatePortable.GeoCoordinate Location { get; set; }

        public string CountryCode { get; set; }

        public string Admin1Code { get; set; }
    }
}
