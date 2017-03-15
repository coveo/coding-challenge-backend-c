using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using System.Net.Http;

namespace CitySuggestion.Controllers
{
    [Route("api/[controller]")]
    public class ValuesController : Controller
    {
        // GET api/values
        [HttpGet]
        public IEnumerable<string> Get()
        {
            var _ = FetchCityInfo("Mont");
            return new string[] { "value1", "value2" };
        }

        public async Task<string> FetchCityInfo(string name)
        {
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri("http://AutoCompleteCity.geobytes.com/");
                var content = new FormUrlEncodedContent(new[]
                {
                new KeyValuePair<string, string>("", "cities")
            });
                var result = await client.PostAsync("AutoCompleteCity?callback=?&sort=size&q=" + name, content);
                string resultContent = await result.Content.ReadAsStringAsync();
                Console.WriteLine(resultContent);
                return resultContent;
            }
        }


        // POST api/values
        [HttpPost]
        public async Task Post([FromBody]string value)
        {
            var cityInfo = await FetchCityInfo(value);
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
