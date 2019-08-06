const express = require('express')
const {createServer} = require('http')
const compression = require('compression')
const morgan = require('morgan')
const path = require('path')

const app = express()
const dev = app.get('env') !== 'production'

if(!dev){
  app.disable('x-powered-by')
  app.use(compression())
  app.use(morgan('common'))
}else {
  app.use(morgan('dev'))
}

app.use(express.json())

app.get('/',(req,res) => {
  res.send({ message: 'Enter /suggestions?q=<<CITYNAMEHERE>> to get started' })
})

const SuggestionRouter = require('./routes/suggestions')
app.use('/suggestions',SuggestionRouter)

app.get('*', function(req, res){
  res.statusCode = 404
  res.json({ message: 'Error,route not found',statusCode: res.statusCode });
});


const server = createServer(app)
const normalizePort = port => parseInt(port, 10)
const PORT = normalizePort(process.env.PORT || 3000)

server.listen(PORT,(err) => {
  if(err) throw err
  console.log('SERVER STARTED')
})
