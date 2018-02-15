using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Threading.Tasks;

namespace SearchService.Model
{
    [DataContract()]
    public class City
    {
        [DataMember(Name = "id")]
        public int Id { get; set; }
        [DataMember(Name = "name")]
        public string Name { get; set; }
        [DataMember(Name = "ascii")]
        public string Ascii { get; set; }
        [DataMember(Name = "alt_name")]
        public string Alt_Name { get; set; }
        [DataMember(Name = "lat")]
        public double? Lat { get; set; }
        [DataMember(Name = "long")]
        public double? Long { get; set; }
        [DataMember(Name = "feat_class")]
        public string Feat_class { get; set; }
        [DataMember(Name = "feat_code")]
        public string Feat_code { get; set; }
        [DataMember(Name = "country")]
        public string Country { get; set; }
        [DataMember(Name = "cc2")]
        public string Cc2 { get; set; }
        [DataMember(Name = "admin1")]
        public string Admin1 { get; set; }
        [DataMember(Name = "admin2")]
        public string Admin2 { get; set; }
        [DataMember(Name = "admin3")]
        public string Admin3 { get; set; }
        [DataMember(Name = "admin4")]
        public string Admin4 { get; set; }
        [DataMember(Name = "population")]
        public string Population { get; set; }
        [DataMember(Name = "elevation")]
        public string Elevation { get; set; }
        [DataMember(Name = "dem")]
        public string Dem { get; set; }
        [DataMember(Name = "tz")]
        public string Tz { get; set; }
        [DataMember(Name = "modified_At")]
        public string Modified_At { get; set; }
    }
}
