using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace CitySuggestion
{
    public class CityInformation
    {
        private string _name;
        private string _country;
        private string _region;
        private decimal _score;

        public string Name { get => _name; set => _name = value; }
        public string Country { get => _country; set => _country = value; }
        public string Region { get => _region; set => _region = value; }
        public decimal CityScore { get => _score; set => _score = value; }

        public CityInformation(string n, string r, string c)
        {
            _name = n;
            _country = c;
            _region = r;
           //s ComputeCityScore();
        }

        private void ComputeCityScore()
        {
            throw new NotImplementedException();
        }

        public override string ToString()
        {
            return string.Format("{0} is located in {1},{2}. Based on the user's IP," +
                " here's the propability that the city you wanted {3}"
                , Name, Country, Region, CityScore);
        }

    }
}
