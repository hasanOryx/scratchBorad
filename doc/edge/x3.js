var createHello = require('edge').func('x3.cs');

var hello = createHello(null, true); 
//hello(null, true); // prints out "Hello from .NET"
hello.getSalary(null, true);