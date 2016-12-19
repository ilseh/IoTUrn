var appRouter = function(app) {
const fs = require('fs');
const pg = require('pg');
const connectionString = process.env.DATABASE_URL || 'postgres://devdata@localhost:5432/devdata';

console.log (connectionString);

app.get("/", function(req, res) {
    res.send("Hello");
});

app.get("/devdata", function(req, res) {
  const results = [];
  // Get a Postgres client from the connection pool
  pg.connect(connectionString, (err, client, done) => {
    // Handle connection errors
    if(err) {
      done();
      console.log(err);
      return res.status(500).json({success: false, data: err});
    }
    // SQL Query > Select Data
    const query = client.query('SELECT * FROM received ORDER BY id ASC;');
    // Stream results back one row at a time
    query.on('row', (row) => {
      results.push(row);
    });
    // After all data is returned, close connection and return results
    query.on('end', () => {
      done();
    return res.json(results);
    });
  });
});


app.post("/devdata", function(req, res) {
  const results = [];
  // Grab data from http request
  console.log (req.body)
  console.log ('---')
  console.log (req.body.DevEUI_uplink.DevAddr)
  const data = {text: req.body.text, complete: false};
  // Get a Postgres client from the connection pool
  pg.connect(connectionString, (err, client, done) => {
    // Handle connection errors
    if(err) {
      done();
      console.log(err);
      return res.status(500).json({success: false, data: err});
    }
    // SQL Query > Insert Data
    client.query('INSERT INTO received(time,deveui,payload_hex,message_text) values($1,$2,$3,$4)',
    [req.body.DevEUI_uplink.Time,req.body.DevEUI_uplink.DevEUI,req.body.DevEUI_uplink.payload_hex,req.body]);
    // SQL Query > Select Data
    const query = client.query('SELECT * FROM received ORDER BY id ASC');
    // Stream results back one row at a time
    query.on('row', (row) => {
      results.push(row);
    });
    // After all data is returned, close connection and return results
    query.on('end', () => {
      done();
      return res.json(results);
    });
  });
});
}
 
module.exports = appRouter;
