using System;
using System.Collections.Generic;
using System.IO;

namespace CitiesSuggestions
{
    public class DataHelper
    {
        public static List<CityData> LoadData()
        {
            var cities = new List<CityData>();
            var assmbly = System.Reflection.Assembly.GetExecutingAssembly();
            var stream = assmbly.GetManifestResourceStream("CitiesSuggestions.data.cities_canada-usa.tsv");
            StreamReader streamreader = new StreamReader(stream, System.Text.Encoding.UTF8);
            char[] delimiter = new char[] { '\t' };
            string[] columnheaders = streamreader.ReadLine().Split(delimiter);

            while (streamreader.Peek() > 0)
            {
                string[] fields = streamreader.ReadLine().Split(delimiter);
                var city = new CityData() { Id = Int32.Parse(fields[Array.IndexOf(columnheaders, "id")]),
                                            Name = fields[Array.IndexOf(columnheaders, "name")],
                                            Population = Int32.Parse(fields[Array.IndexOf(columnheaders, "population")]),
                                            CountryCode = fields[Array.IndexOf(columnheaders, "country")],
                                            Admin1Code = fields[Array.IndexOf(columnheaders, "admin1")],
                                            Location = new GeoCoordinatePortable.GeoCoordinate(
                                                Double.Parse(fields[Array.IndexOf(columnheaders, "lat")]),
                                                Double.Parse(fields[Array.IndexOf(columnheaders, "long")])
                                                )
                };
                cities.Add(city);
            }

            return cities;
        }
    }
}
