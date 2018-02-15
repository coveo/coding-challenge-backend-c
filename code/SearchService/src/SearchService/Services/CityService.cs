using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using CsvHelper;
using GeoCoordinatePortable;
using Microsoft.Extensions.Options;
using SearchService.Model;
using SearchService.Services.Interfaces;

namespace SearchService.Services
{
    public class CityService : ICityService
    {
        private readonly IScoringStrategy _scoringStrategy;
        private readonly WebSettings _settings;

        public CityService(IOptions<WebSettings> settingsOptions, IScoringStrategy scoringStrategy)
        {
            _scoringStrategy = scoringStrategy;
            _settings = settingsOptions.Value;
        }

        private List<City> _cities;
        public List<City> Cities => _cities ?? (_cities = LoadCities(_settings.LocationsFilePath));

        private List<City> LoadCities(string absolutePath)
        {
            List<City> result;
            using (var fileReader = System.IO.File.OpenText(absolutePath))
            using (var csv = new CsvReader(fileReader))
            {
                csv.Configuration.Delimiter = $"\t";
                csv.Configuration.HasHeaderRecord = true;
                csv.Configuration.IsHeaderCaseSensitive = false;

                result = csv.GetRecords<City>().ToList();
            }
            return result;
        }

        public List<CityResult> Search(string cityName, double? longitude = null, double? latitude = null)
        {
            var cities = !string.IsNullOrEmpty(cityName) ? Cities.Where(c => c.Name.ToLower().Contains(cityName.ToLower())).ToList() : Cities;
            var cityResults = cities.Select(c =>
                new CityResult
                {
                    Name = string.Format("{0}, {1}, {2}", c.Name, c.Admin1, c.Country),
                    Latitude = c.Lat.GetValueOrDefault(0),
                    Longitude = c.Long.GetValueOrDefault(0),
                    Score = _scoringStrategy.Calculate(c.Name, c.Long, c.Lat, cityName, longitude, latitude)
                }).ToList();

            return cityResults.OrderByDescending(r => r.Score).ToList();
        }
    }
}
