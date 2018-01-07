
var edge = require('edge');

var helloWorld = edge.func(`    // test
/*
  async (input) => {
    return new {
      anInteger = 1,
      aNumber = 3.1415,
      aBool = true,
      anObject = new { a = "b", c = 12 },
      anArray = new object[] { "a", 1, true },
      aPerson = new Person("Tomasz", "Janczuk"),
      aBuffer = new byte[1024]
    };
  }
*/
    async (input) => { 
        Console.WriteLine("Hello \t {0} from C sharp only, press any key to continue", input);
        Console.ReadKey();
        return ".NET Welcomes " + input.ToString(); 
    }
`);

var hello = edge.func('mysample.cs');  // bind to source file
hello(null, function(error, result){
      console.log('test');
   }
)

helloWorld('JavaScript', function (error, result) {
    if (error) throw error;
    console.log(result);
});


/*
var hello = require('edge').func('My.Sample.dll');

var payload = {
  anInteger: 1,
  aNumber: 3.1415,
  aString: 'foobar',
  aBool: true,
  anObject: { first: 'Tomasz', last: 'Janczuk' },
  anArray: [ 'a', 1, true ],
  aBuffer: new Buffer(1024)
}

hello(payload, function (error, result) { ... });
*/

// var edge = require('edge');

var http = require('http');
var port = process.env.PORT || 8080;

//process.env.EDGE_SQL_CONNECTION_STRING="Data Source=localhost\\sqlexpress;Initial Catalog=db1;user id=***;password=***";

process.env.EDGE_SQL_CONNECTION_STRING="Data Source=BK-SAP;Initial Catalog=BK-LIVE;user id=sa;password=sql@123";

// install npm i edge-sql to support sql language
var getTopUsers = edge.func('sql', `   
    SELECT 
    itemCode 
    FROM 
    OITM 
    where 
    itemcode like @item
`);

function logError(err, res) {
    res.writeHead(200, { 'Content-Type': 'text/plain' });
    res.write("Error: " + err);
    res.end("");
}    

http.createServer(function (req, res) {
    res.writeHead(200, { 'Content-Type': 'text/html' });

    getTopUsers({item: 'RM-EVA%', whs: 'RM'}, function (error, result) {                   // or getTopUsers(null, function (error, result) if no parameters to be sent
        if (error) { logError(error, res); return; }
        if (result) {
            res.write("<ul>");
            result.forEach(function(item) {
                res.write("<li>" + item.itemCode + ": " + "</li>");
            });
            res.end("</ul>");
        }
        else {
        }
    });
}).listen(port);
console.log("Node server listening on port " + port);