var mysql = require('mysql');

var express = require('express');
app = express();
port = process.env.PORT || 3018;

var bodyParser = require('body-parser');
app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true })); // support encod


var con = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'oL1920bD$',
  insecureAuth : true,
  database : 'MagicKey'

}); 

con.connect(function(err) {
  if (err) throw err;
  console.log('Connected to DB');
  /*
  con.query("SELECT * FROM test", function (err, rows, fields) {
    if (err) throw err;
     for (var i in rows) {
        console.log(rows[i].name);
    }
  });
  */
});

app.get("/", (req, res) =>{
	console.log("Root route")
	res.send("respond from route")
});
app.get("/newKey", (req, res) =>{
	var genRandId =  makeid(10);
	var who = (req.query.who).substr(0,60);
	var isOk = false;
	var key = 'NA';
	var stat = 'NOK';
	var rowsExists = '0';
	//chek that existst in DB
	con.query("SELECT Count(1) EXISTSID FROM MAINCONV WHERE CONVID = '" + genRandId + "'", function (err, rows, fields) {
    if (err) throw err;
		//console.log(rows[0].EXISTSID);
		rowsExists = rows[0].EXISTSID;
	});
	
	if(rowsExists == '0'){
		con.query("INSERT INTO MAINCONV (CONVID, CREATEDBY) VALUES ('"  + genRandId + "', '" + who + "')", 
		function (err, rows, fields) {
		if (err) throw err;
				
		});
		stat = 'OK';
		key = genRandId;
	}
	res.send([{newKey:key, who, status:stat}]);
	console.log('GET newKey');
});


app.get("/addMesageGet", (req, res) =>{
	var who = (req.query.who).substr(0,60);
	var conKey = req.query.keyconv;
	var msg = req.query.msg;
	var stat = 'OK';
	con.query("SELECT Count(1) EXISTSID FROM MAINCONV WHERE CONVID = '" + conKey + "'", function (err, rows, fields) {
		if (err) throw err;
		if(rows[0].EXISTSID == '1'){
			console.log(rows[0].EXISTSID + ': rowsExists 1 - yes , 0 - No ');
			
			con.query("INSERT INTO CONVERSATION (CONVID, CREATEDBY, CONTEXT) " +
					  " VALUES ('"  + conKey + "', '" + who + "','" + msg + "')", 
					  function (err, rows, fields) {
								if (err) throw err;
				});
		}
	});
	console.log('GET addMesageGet');
	res.send([{status:stat}]);
});

app.post('/addMesage', function(req, res) {
	

	var stat = 'OK';
	var conKey = req.body.keyconv;
	var who = req.body.who;
	var msg = req.body.msg;

	con.query("SELECT Count(1) EXISTSID FROM MAINCONV WHERE CONVID = '" + conKey + "'", function (err, rows, fields) {
		if (err) throw err;
		if(rows[0].EXISTSID == '1'){
			console.log(rows[0].EXISTSID + ': rowsExists 1 - yes , 0 - No ');
			
			con.query("INSERT INTO CONVERSATION (CONVID, CREATEDBY, CONTEXT) " +
					  " VALUES ('"  + conKey + "', '" + who + "','" + msg + "')", 
					  function (err, rows, fields) {
								if (err) throw err;
				});
		}
	});
	console.log('POST addMesage');
	res.send(stat);
});
app.listen(port);
console.log('REST API server started on: ' + port);

app.get("/getConv", (req, res) =>{
	var stat ='OK';
	var key = req.query.keyConv;
	var conv = '';
	var sql = "SELECT * " +
			  "FROM CONVERSATION " +
			  "WHERE CONVID = '" + key + "' " +
			  "ORDER BY CREATETS DESC " +
			  "LIMIT 30"; 
	con.query(sql, function (err, rows, fields) {
			if (err) throw err;
				//console.log(rows[0].EXISTSID);
				conv = rows;
				res.send(conv);
	});

	console.log('GET getConV');
});



function makeid(length) {
   var result           = '';
   var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
   var charactersLength = characters.length;
   for ( var i = 0; i < length; i++ ) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
   }
   return result;
}



