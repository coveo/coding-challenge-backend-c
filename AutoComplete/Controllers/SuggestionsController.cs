using Microsoft.AspNetCore.Mvc;

namespace AutoComplete.Controllers
{
    [Route("suggestions")]
    [ApiController]
    public class SuggestionsController : ControllerBase
    {
        private CitiesSuggestions.ISuggestions Suggestions;

        public SuggestionsController()
        {
            Suggestions = CitiesSuggestions.CoreLogic.Factory.CreateWithDefaultCities();
        }

        // GET /suggestions
        [HttpGet]
        public ActionResult<CitiesSuggestions.SuggestionsResult> Get(string q, double? latitude, double? longitude)
        {
            if(latitude.HasValue && longitude.HasValue)
            {
                var ret = Suggestions.GetSuggestions(q, new System.Tuple<double, double>(latitude.Value, longitude.Value));
                return ret;
            }
            else
            {
                var ret = Suggestions.GetSuggestions(q);
                return ret;
            }
        }

    }
}
