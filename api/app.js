/* eslint-disable no-console */
const express = require('express');


const app = express();
const port = 8000;
const host = 'localhost';

app.listen(port, host, () => {
    console.log(`Listening on ${host}:${port}`);
});
