using System.Data;
using Microsoft.AspNetCore.Mvc;

namespace AutoComplete.Controllers
{
    [Route("suggestions")]
    [ApiController]
    public class SuggestionsController : ControllerBase
    {
        private DataTable citiesData;

        public SuggestionsController()
        {
            citiesData = data.DataHelper.LoadData(@"data\cities_canada-usa.tsv");
        }

        // GET /suggestions
        [HttpGet]
        public ActionResult<SuggestionsResult> Get(string q, double? latitude, double? longitude)
        {
            var ret = new SuggestionsResult();
            ret.AddItem(new SuggestionsResultItem() { name = "**YOUR QUERY** London, WITH POS,++" + q, latitude = latitude.HasValue ? latitude.ToString() : "no lat given", longitude = longitude.HasValue? longitude.ToString() : "no long given", score = 0.9 });
            return ret;
        }

    }
}
