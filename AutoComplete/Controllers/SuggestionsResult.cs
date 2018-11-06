using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AutoComplete.Controllers
{
    public class SuggestionsResultItem
    {
        public string name { get; set; }
        public string latitude { get; set; }
        public string longitude { get; set; }
        public double score { get; set; }
    }

    public class SuggestionsResult
    {
        public List<SuggestionsResultItem> suggestions =  new List<SuggestionsResultItem>();

        public void AddItem(SuggestionsResultItem item)
        {
            suggestions.Add(item);
        }
    }
}
