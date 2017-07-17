using SearchService.Model;

namespace SearchService.Services.Interfaces
{
    public interface IScoringStrategy
    {
        double Calculate(string resultName, double? resultLongitude, double? resultLatitude, string searchName, double? searchLongitude, double? searchLatitude);
    }
}
