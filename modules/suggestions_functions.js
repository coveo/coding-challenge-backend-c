//Converts TSV file to JSON for easier data manipulation
function tsvJSON(tsv) {
  const lines = tsv.split('\n');
  const headers = lines.slice(0, 1)[0].split('\t');
  return lines.slice(1, lines.length).map(line => {
    const data = line.split('\t');
    return headers.reduce((obj, nextKey, index) => {
      obj[nextKey] = data[index];
      return obj;
    }, {});
  });
}


function name_comparison(city_name,query,lat,lng){
  const namearr = city_name.split('')
  const queryarr = query.split('')
  const distance = getEditDistance(city_name,query)
  const score = lat && lng ? ((namearr.length - distance)/namearr.length)/2 : ((namearr.length - distance)/namearr.length)     //if(there are lat and lng params.then cap the score to 0.5(since the other half will be calculated from the distance given from the lat and lng))
  return score

}

// Algorithm that calculates the difference between 2 strings(Levenshtein distance)
function getEditDistance(a, b){
  if(a.length == 0) return b.length;
  if(b.length == 0) return a.length;
  let matrix = [];
  for(let i = 0; i <= b.length; i++){
    matrix[i] = [i];
  }
  for(let j = 0; j <= a.length; j++){
    matrix[0][j] = j;
  }
  for(let i = 1; i <= b.length; i++){
    for(let j = 1; j <= a.length; j++){
      if(b.charAt(i-1) == a.charAt(j-1)){
        matrix[i][j] = matrix[i-1][j-1];
      } else {
        matrix[i][j] = Math.min(matrix[i-1][j-1] + 1, // substitution
                                Math.min(matrix[i][j-1] + 1, // insertion
                                         matrix[i-1][j] + 1)); // deletion
      }
    }
  }
  return (matrix[b.length][a.length]); // returns an integer,0 if strings are identical n if they are not nE(0,+infinite)
};


//Parallel sorting of two arrays based in one condition

function swap(arr,i){
  const temp = arr[i]
  arr[i] = arr[i+1]
  arr[i+1] = temp
}


function bubble_Sort(a,b,method)
{
    let swapp;
    let n = a.length-1;
    let x=a;
    do {
        swapp = false;
        for (let i=0; i < n; i++)
        {   if(method === 'descending'){
                  if (x[i] <= x[i+1]){
                     swap(x,i)
                     swap(b,i)
                     swapp = true
                  }
        }else{
            if (x[i] >= x[i+1]){
              swap(x,i)
              swap(b,i)
              swapp = true;
            }
          }
        }
        n--;
    } while (swapp);
 return x;
}


//Algorithm that calculates the avg distance to travel from LAT1-LNG1 to LAT2-LNG2
//Math-Formula: https://www.movable-type.co.uk/scripts/latlong.html
function distance(lat1, lon1, lat2, lon2, unit) {
	if ((lat1 == lat2) && (lon1 == lon2)) {
		return 0;
	}
	else {
		const radlat1 = Math.PI * lat1/180;
		const radlat2 = Math.PI * lat2/180;
		const theta = lon1-lon2;
		const radtheta = Math.PI * theta/180;
		let dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
		if (dist > 1) {
			dist = 1;
		}
		dist = Math.acos(dist);
		dist = dist * 180/Math.PI;
		dist = dist * 60 * 1.1515;
		if (unit=="K") { dist = dist * 1.609344 }
		if (unit=="N") { dist = dist * 0.8684 }
		return dist;
	}
}


module.exports = {
  tsvJSON,
  name_comparison,
  getEditDistance,
  swap,
  bubble_Sort,
  distance
}
