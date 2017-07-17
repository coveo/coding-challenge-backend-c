using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using CsvHelper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using SearchService.Model;
using SearchService.Services.Interfaces;

// For more information on enabling Web API for empty projects, visit http://go.microsoft.com/fwlink/?LinkID=397860

namespace SearchService.Controllers
{
    [Route("api/[controller]")]
    public class SuggestionsController : Controller
    {

        private readonly ICityService _cityService;

        public SuggestionsController(ICityService cityService)
        {
            _cityService = cityService;
        }
        
        [Route("")]
        [HttpGet]
        public List<CityResult> Get(string q, double? longitude, double? latitude)
        {
            return _cityService.Search(q, longitude, latitude);
        }
    }
}
