using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace CitySearch
{
    public class LargeCity
    {
        private string _name;
        private string _country;
        private string _region;
        private int _latitude;
        private int _longitude;
        private decimal _score;

        public string Name { get => _name; set => _name = value; }
        public string Country { get => _country; set => _country = value; }
        public string Region { get => _region; set => _region = value; }
        public int Latitude { get => _latitude; set => _latitude = value; }
        public int Longitude { get => _longitude; set => _longitude = value; }
        public decimal SearchScore { get => _score; set => _score = value; }
    }
}
