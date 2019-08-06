const express = require('express')
const router = express.Router()
const fs = require('fs')
const model = require('../modules/suggestions_functions')



function cities(json,query,lat,lng){
  let object = []
  let scores = []
  let distances = []
  let counter = 0
  json.forEach((city,index) => {
    if(city.name){
      if((city.name.toLowerCase()).includes(query.toLowerCase())){
        const score = model.name_comparison(city.name,query,lat,lng)
        const dist = model.distance(lat,lng,city.lat,city.long)
        scores.push(score)
        object.push({ name:`${city.name}, ${city.admin1}, ${city.tz}`, latitude:city.lat, longitude:city.long, score:scores[counter]})
        distances.push(dist)
        counter++
      }
      else { //checks the alt-names for every city if there wasn't a match with the name
          if(city.alt_name){
            const alt = city.alt_name.split(',')
             for(let i=0;i<alt.length-1;i++){
              if((alt[i].toLowerCase()).includes(query.toLowerCase())){
                const scorex = model.name_comparison(alt[i],query,lat,lng)
                const distx = model.distance(lat,lng,city.lat,city.long)
                scores.push(scorex)
                object.push({ name:`${city.name}, ${city.admin1}, ${city.tz}`, latitude:city.lat, longitude:city.long, score:scores[counter]})
                distances.push(distx)
                counter++
                break;
            }
        }}
      }
    }
  })
  //if(lat and lng query parameters are passed)
  if(lat && lng){
    model.bubble_Sort(distances,object)
    for(let i=distances.length-1; i >=  0; i--){
      object[distances.length - (i+1)]['score'] += (i+1)/(distances.length * 2)
      scores[distances.length - (i+1)] += (i+1)/(distances.length * 2)
    }
  }
  else{
    model.bubble_Sort(scores,object,'descending')
  }
  return object
}



router.get('/',(req,res,next) => {
  const query = req.query.q
  if(!query)
    res.json({ message: 'You need to enter a valid query' })
  const lat = req.query.latitude
  const lng = req.query.longitude
  const tsv =  fs.readFileSync('./data/cities_canada-usa.tsv',{ encoding: 'utf8' });
  const json =  model.tsvJSON(tsv)
  const response =  cities(json,query,lat,lng)
  res.json({ suggestions:response })
})



module.exports = router
