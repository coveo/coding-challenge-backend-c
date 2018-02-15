using System.Collections.Generic;
using SearchService.Model;

namespace SearchService.Services.Interfaces
{
    public interface ICityService
    {
        List<City> Cities { get; }
        List<CityResult> Search(string cityName, double? longitude = null, double? latitude = null);
    }
}
