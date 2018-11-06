using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace AutoComplete.Controllers
{
    [Route("suggestions")]
    [ApiController]
    public class SuggestionsController : ControllerBase
    {
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
