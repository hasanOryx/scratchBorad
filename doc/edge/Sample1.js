var http = require('http'); var edge = require('edge'); var port = process.env.PORT || 8080;

//setup the EDGE_SQL_CONNECTION_STRING environment
process.env.EDGE_SQL_CONNECTION_STRING="Data Source=BK-SAP;Initial Catalog=BK-LIVE;user id=sa;password=sql@123";

// Set up the assembly to call from Node.js 
var querySample = edge.func({ assemblyFile: 'EdgeSampleLibrary.dll', typeName: 'EdgeSampleLibrary.Sample1', methodName: 'Invoke' });

function logError(err, res) { res.writeHead(200, { 'Content-Type': 'text/plain' }); res.write("Got error: " + err); res.end(""); }

http.createServer(function (req, res) { res.writeHead(200, { 'Content-Type': 'text/html' });

    // This is the data we will pass to .NET
    var data = { pageNumber: 2, pageSize: 3 };

    // Invoke the .NET function
    querySample(data, function (error, result) {
        if (error) { logError(error, res); return; }
        if (result) {
            res.write("<ul>");
            result.forEach(function(user) {
                res.write("<li>" + user.FirstName + " " + user.LastName + ": " + user.Email + "</li>");
            });
            res.end("</ul>");
        }
        else {
            res.end("No results");
        }
    });


}).listen(port);

console.log("Node server listening on port " + port);